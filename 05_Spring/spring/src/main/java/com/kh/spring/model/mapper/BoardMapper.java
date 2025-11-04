package com.kh.spring.model.mapper;

import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Category> selectCategories();

    //게시판
    List<Board> selectBoardList(RowBounds rowBounds);
    int selectBoardListCount();
    int insertBoard(Board board);
    int increaseCount(int boardNo);
    Board selectBoardByNo(int boardNo);
    Attachment selectAttachmentByBoardNo(int boardNo);
    int updateBoard(Board board);

    //첨부파일
    int insertAttachment(Attachment attachment);
    int deleteAttachment(int fileNo);
}
