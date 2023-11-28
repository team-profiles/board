package kr.or.kosa.service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;

public class BoardDeleteOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String idx = request.getParameter("idx");
		String pwd = request.getParameter("pwd");
		String msg = "";
		String url = "";
		BoardService service = BoardService.getInBoardService();
		try {
			int result = service.board_Delete(idx, pwd);
			if (result > 0) {
				msg = "delete success";
				url = "/boardList.do";
			} else {
				msg = "delete fail";
				url = "/boardList.do";
				// response.sendRedirect("board_list.jsp");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		request.setAttribute("board_url", url);
		request.setAttribute("board_msg", msg);
		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false); //forward 방식
		forward.setPath("/WEB-INF/board/redirect.jsp");
		
		return forward;
	}

}
