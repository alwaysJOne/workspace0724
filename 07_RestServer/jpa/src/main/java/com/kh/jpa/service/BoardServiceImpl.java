package com.kh.jpa.service;

import com.kh.jpa.dto.BoardDto;
import com.kh.jpa.entity.Board;
import com.kh.jpa.entity.BoardTag;
import com.kh.jpa.entity.Member;
import com.kh.jpa.entity.Tag;
import com.kh.jpa.enums.CommonEnums;
import com.kh.jpa.repository.BoardRepository;
import com.kh.jpa.repository.MemberRepository;
import com.kh.jpa.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true) //readOnly=true 데이터를 조회만하고, DML은 하지 않는 트랜잭션
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final BoardRepository boardRepository;
    private final String FILE_PATH = "C:\\devtool\\upload";

    @Override
    @Transactional
    public Long createBoard(BoardDto.Create createDto) throws IOException {
        // 게시글 작성
        // 작성자 찾기 -> 객체관점의 코드를 작성할 것이기 때문에 key를 직접 외래키로 insert하지 않고
        // 작성자를 찾아서 참조관계를 만들어 준다.
        // spring, jpa

        Member member = memberRepository.findById(createDto.getUser_id())
                        .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다"));

        String changeName = null;
        String originName = null;

        if(createDto.getFile() != null && !createDto.getFile().isEmpty()) {
            originName = createDto.getFile().getOriginalFilename();
            changeName = UUID.randomUUID().toString() + "_" + originName;

            File uploadDir = new File(FILE_PATH);
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            createDto.getFile()
                    .transferTo(new File(FILE_PATH + changeName));
        }

        Board board = createDto.toEntity();
        board.changeMember(member);
        board.changeFile(originName, changeName);

        if(createDto.getTags() != null && !createDto.getTags().isEmpty()) {
            // tag가 전달됨 ["srping", "jpa"]
            for(String tagName : createDto.getTags()) {
                //tag를 이름으로 조회해서 없으면 새로 만들자
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseGet(() -> tagRepository.save(Tag.builder() //없다면 예외발생이 아닌 생성
                                                                .tagName(tagName)
                                                                .build()));
                board.addTag(tag);
//                BoardTag boardTag = BoardTag.builder()
//                        .tag(tag)
//                        .build();
//
//                boardTag.changeBoard(board);
            }
        }

        boardRepository.save(board);
        return board.getBoardId();
    }

    @Override
    public BoardDto.Response getBoardDetail(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        List<String> tagNames = board.getBoardTags()
                .stream()
                .map(boardTag -> boardTag.getTag().getTagName())
                .toList();


        return BoardDto.Response.of(
                board.getBoardId(),
                board.getBoardTitle(),
                board.getBoardContent(),
                board.getOriginName(),
                board.getChangeName(),
                board.getCount(),
                board.getMember().getUserId(),
                board.getMember().getUserName(),
                board.getCreateDate(),
                tagNames
        );
    }

    @Override
    public Page<BoardDto.Response> getBoardList(Pageable pageable) {

        Page<Board> page = boardRepository.findByStatus(CommonEnums.Status.Y, pageable);

        return page.map(board -> BoardDto.Response.ofSimple(
                board.getBoardId(),
                board.getBoardTitle(),
                board.getOriginName(),
                board.getCount(),
                board.getMember().getUserId(),
                board.getCreateDate()
        ));
    }

    @Override
    @Transactional
    public BoardDto.Response updateBoard(Long boardId, BoardDto.Update updateBoardDto) throws IOException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        String originName = board.getOriginName();
        String changeName = board.getChangeName();

        if(updateBoardDto.getFile() != null && !updateBoardDto.getFile().isEmpty()) {
            originName = updateBoardDto.getFile().getOriginalFilename();
            changeName = UUID.randomUUID().toString() + "_" + originName;

            File uploadDir = new File(FILE_PATH);
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            updateBoardDto.getFile()
                    .transferTo(new File(FILE_PATH + changeName));
        }

        board.update(updateBoardDto.getBoard_title(),
                updateBoardDto.getBoard_content(),
                originName, changeName
        );

//        board.changeTitle(updateBoardDto.getBoard_title());
//        board.changeFile(originName, changeName);
//        board.changeContent(updateBoardDto.getBoard_content());

        if(updateBoardDto.getTags() != null && !updateBoardDto.getTags().isEmpty()) {
            //기존BoardTag -> 연결을 끊어야할까? X
            //연결된 BoardTag의 영속성을 제거한다.
            board.getBoardTags().clear();

            for(String tagName : updateBoardDto.getTags()) {
                //tag를 이름으로 조회해서 없으면 새로 만들자
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseGet(() -> tagRepository.save(Tag.builder() //없다면 예외발생이 아닌 생성
                                .tagName(tagName)
                                .build()));

                    board.addTag(tag);
                //                BoardTag boardTag = BoardTag.builder()
                //                        .tag(tag)
                //                        .build();
                //
                //                boardTag.changeBoard(board);
            }
        }

        List<String> tagNames = board.getBoardTags()
                .stream()
                .map(boardTag -> boardTag.getTag().getTagName())
                .toList();

        return BoardDto.Response.of(
                board.getBoardId(),
                board.getBoardTitle(),
                board.getBoardContent(),
                board.getOriginName(),
                board.getChangeName(),
                board.getCount(),
                board.getMember().getUserId(),
                board.getMember().getUserName(),
                board.getCreateDate(),
                tagNames
        );
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        if(board.getChangeName() != null){
            new File(FILE_PATH + board.getChangeName()).delete();
        }

        boardRepository.delete(board);
    }


}
