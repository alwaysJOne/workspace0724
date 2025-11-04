package com.kh.spring.service;

import com.kh.spring.model.mapper.BoardMapper;
import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.Category;
import com.kh.spring.model.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public List<Category> getCategories() {
        return boardMapper.selectCategories();
    }

    @Override
    public Map<String, Object> getBoardList(int currentPage) {
        int listCount = boardMapper.selectBoardListCount();

        PageInfo pi = new PageInfo(currentPage, listCount, 5, 5);

        int offset = (currentPage - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());

        ArrayList<Board> list = (ArrayList)boardMapper.selectBoardList(rowBounds);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("pi", pi);

        return map;
    }

    //spring에서 기본적으로 mcv패턴을 사용하고
    //service계층에서는 하나의 기능을 정의하며, dao에서는 개별 sql단위로 처리되므로
    //업무단위인 서비스계층에 트랜잭션을 걸어준다.
    @Override
    @Transactional
    public int insertBoard(Board board, MultipartFile file) {
        int result = boardMapper.insertBoard(board);

        if(result > 0 && file != null && !file.isEmpty()) {
            Attachment at = new Attachment();
            at.setRefBoardNo(board.getBoardNo());
            at.setOriginName(file.getOriginalFilename());
            at.setFilePath("resources/uploadFiles/");
            at.setChangeName(saveFile(file, "C:/workspace/05_Spring/spring/src/main/webapp/resources/uploadFiles/"));
            at.setFileLevel(1);

            result = boardMapper.insertAttachment(at);
        }

        return result;
    }

    @Override
    public Map<String,Object> getBoardByIdWithCount(int boardNo) {
        int result = boardMapper.increaseCount(boardNo);

        Map<String,Object> map = new HashMap<>();
        if(result > 0){
          //board, attachment
            Board board = boardMapper.selectBoardByNo(boardNo);
            Attachment at = boardMapper.selectAttachmentByBoardNo(boardNo);

            map.put("board",board);
            map.put("attachment",at);
        }

        return map;
    }

    @Override
    public Map<String,Object> getBoardById(int boardNo) {
        Map<String,Object> map = new HashMap<>();

        Board board = boardMapper.selectBoardByNo(boardNo);
        Attachment at = boardMapper.selectAttachmentByBoardNo(boardNo);

        map.put("board",board);
        map.put("attachment",at);
        return map;
    }

    @Override
    @Transactional
    public int updateBoard(Board board, MultipartFile file, Integer originFileNo) {
        int result = boardMapper.updateBoard(board);

        if(result > 0 && file != null && !file.isEmpty()) {// 업로드할 file이 있다면
            if(originFileNo != null){ //기존파일이 존재한다면
                boardMapper.deleteAttachment(originFileNo);
            }

            //새로운 파일 등록
            Attachment at = new Attachment();
            at.setRefBoardNo(board.getBoardNo());
            at.setOriginName(file.getOriginalFilename());
            at.setFilePath("resources/uploadFiles/");
            at.setChangeName(saveFile(file, "C:/workspace/05_Spring/spring/src/main/webapp/resources/uploadFiles/"));
            at.setFileLevel(1);

            result = boardMapper.insertAttachment(at);
        }

        return result;
    }

    private String saveFile(MultipartFile file, String path) {
        String originName = file.getOriginalFilename();
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int randomNumber = (int)(Math.random() * 90000) + 10000;
        String ext = originName.substring(originName.lastIndexOf("."));

        String changeName = currentTime + randomNumber + ext;

        try {
            file.transferTo(new File(path + changeName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return changeName;
    }
}
