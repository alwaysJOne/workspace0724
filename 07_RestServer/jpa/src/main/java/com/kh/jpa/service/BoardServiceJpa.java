package com.kh.jpa.service;

import com.kh.jpa.dto.BoardDto;
import com.kh.jpa.entity.Board;
import com.kh.jpa.entity.Member;
import com.kh.jpa.entity.Tag;
import com.kh.jpa.enums.CommonEnums;
import com.kh.jpa.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardServiceJpa implements BoardService{

    private final MemberJPARepository memberJPARepository;
    private final BoardJPARepository boardJPARepository;
    private final TagJPARepository tagJPARepository;
    private final String FILE_PATH = "C:\\devtool\\upload";


    @Override
    public Long createBoard(BoardDto.Create createDto) throws IOException {
        Member member = memberJPARepository.findById(createDto.getUser_id())
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
                Tag tag = tagJPARepository.findByTagName(tagName)
                        .orElseGet(() -> tagJPARepository.save(Tag.builder() //없다면 예외발생이 아닌 생성
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

        boardJPARepository.save(board);
        return board.getBoardId();
    }

    @Override
    public BoardDto.Response getBoardDetail(Long boardId) {
        Board board = boardJPARepository.findById(boardId)
                        .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다"));

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
        Page<Board> page = boardJPARepository.findByStatus(CommonEnums.Status.Y, pageable);

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
    public BoardDto.Response updateBoard(Long boardId, BoardDto.Update updateBoardDto) throws IOException {
        Board board = boardJPARepository.findById(boardId)
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

        if(updateBoardDto.getTags() != null && !updateBoardDto.getTags().isEmpty()) {
            board.getBoardTags().clear();

            for(String tagName : updateBoardDto.getTags()) {
                //tag를 이름으로 조회해서 없으면 새로 만들자
                Tag tag = tagJPARepository.findByTagName(tagName)
                        .orElseGet(() -> tagJPARepository.save(Tag.builder() //없다면 예외발생이 아닌 생성
                                .tagName(tagName)
                                .build()));

                board.addTag(tag);
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
    public void deleteBoard(Long boardId) {
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        if(board.getChangeName() != null){
            new File(FILE_PATH + board.getChangeName()).delete();
        }

        boardJPARepository.delete(board);
    }
}
