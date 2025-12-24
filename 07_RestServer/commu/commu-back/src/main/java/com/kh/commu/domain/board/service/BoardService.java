package com.kh.commu.domain.board.service;

import com.kh.commu.domain.board.dto.BoardDto;
import com.kh.commu.domain.board.entity.Board;
import com.kh.commu.domain.board.entity.BoardTag;
import com.kh.commu.domain.board.entity.Tag;
import com.kh.commu.domain.board.repository.BoardRepository;
import com.kh.commu.domain.board.repository.BoardTagRepository;
import com.kh.commu.domain.board.repository.TagRepository;
import com.kh.commu.domain.member.entity.Member;
import com.kh.commu.domain.member.repository.MemberRepository;
import com.kh.commu.global.common.CommonEnums;
import com.kh.commu.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;

    @Transactional
    public Long createBoard(String boardTitle, String boardContent, String userId, 
                           MultipartFile file, List<String> tags) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다: " + userId));

        String originName = null;
        String changeName = null;

        if (file != null && !file.isEmpty()) {
            originName = file.getOriginalFilename();
            changeName = saveFile(file);
        }

        Board board = Board.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .originName(originName)
                .changeName(changeName)
                .member(member)
                .build();

        boardRepository.save(board);

        if (tags != null && !tags.isEmpty()) {
            for (String tagName : tags) {
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseGet(() -> tagRepository.save(Tag.builder().tagName(tagName).build()));

                BoardTag boardTag = BoardTag.builder()
                        .board(board)
                        .tag(tag)
                        .build();

                board.addBoardTag(boardTag);
            }
        }

        return board.getBoardId();
    }

    public PageResponse<BoardDto.Response> getAllBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findByStatus(CommonEnums.Status.Y, pageable);
        Page<BoardDto.Response> responses = boards.map(BoardDto.Response::from);
        return new PageResponse<>(responses);
    }

    @Transactional
    public BoardDto.Response getBoardById(Long boardId) {
        Board board = boardRepository.findByIdWithMemberAndTags(boardId, CommonEnums.Status.Y)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다: " + boardId));

        board.incrementCount();
        return BoardDto.Response.from(board);
    }

    @Transactional
    public BoardDto.Response updateBoard(Long boardId, String boardTitle, String boardContent,
                                        MultipartFile file, List<String> tags) {
        Board board = boardRepository.findByIdWithMemberAndTags(boardId, CommonEnums.Status.Y)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다: " + boardId));

        String originName = board.getOriginName();
        String changeName = board.getChangeName();

        if (file != null && !file.isEmpty()) {
            if (changeName != null) {
                deleteFile(changeName);
            }
            originName = file.getOriginalFilename();
            changeName = saveFile(file);
        }

        board.updateBoard(boardTitle, boardContent, originName, changeName);

        if (tags != null) {
            board.clearBoardTags();
            boardTagRepository.deleteByBoard_BoardId(boardId);

            for (String tagName : tags) {
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseGet(() -> tagRepository.save(Tag.builder().tagName(tagName).build()));

                BoardTag boardTag = BoardTag.builder()
                        .board(board)
                        .tag(tag)
                        .build();

                board.addBoardTag(boardTag);
            }
        }

        return BoardDto.Response.from(board);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다: " + boardId));

        if (board.getChangeName() != null) {
            deleteFile(board.getChangeName());
        }

        board.delete();
    }

    private String saveFile(MultipartFile file) {
        String uploadDir = "uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String savedFilename = UUID.randomUUID().toString() + extension;
        File destFile = new File(uploadDir + savedFilename);

        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다", e);
        }

        return savedFilename;
    }

    private void deleteFile(String filename) {
        File file = new File("uploads/" + filename);
        if (file.exists()) {
            file.delete();
        }
    }
}

