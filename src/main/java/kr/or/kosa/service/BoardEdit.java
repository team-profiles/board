package kr.or.kosa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dto.Board;

public class BoardEdit implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String idx = request.getParameter("idx");
		BoardService service = null;
		Board board = null;
		String url = "/WEB-INF/board/board_edit.jsp";
		ActionForward forward = new ActionForward();
		try {
			if (idx == null || idx.trim().equals("")) {
				forward.setPath("/WEB-INF/board/board_list.jsp");
				return forward;
			}
			service = BoardService.getInBoardService();
			board = service.board_EditContent(idx);
			if (board == null) {
				forward.setPath("/WEB-INF/board/board_list.jsp");
				return forward;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("board", board);
		
		forward.setRedirect(false); //forward 방식
		forward.setPath("/WEB-INF/board/board_edit.jsp");
		
		return forward;
	}

}
