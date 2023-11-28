package kr.or.kosa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;

public class BoardEditOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String idx = request.getParameter("idx");
		
		ActionForward forward = new ActionForward();

		try {
			if (idx == null || idx.strip().equals("")) {
				response.sendRedirect("board_list.jsp");
				
				return forward;
			}
			BoardService service = BoardService.getInBoardService();
			int result = service.board_Edit(request);
			String msg = "";
			String url = "";
			if (result > 0) {
				msg = "edit success";
				url = "board_list.jsp";
			} else {
				msg = "edit fail";
				url = "board_edit.jsp?idx=" + idx;
			}
			request.setAttribute("board_msg", msg);
			request.setAttribute("board_url", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward.setRedirect(false); //forward 방식
		forward.setPath("/WEB-INF/board/board_editok.jsp");
		
		return forward;
	}
	
}
