package kr.or.kosa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;

public class BoardRewrite implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		String idx = request.getParameter("idx");
		String cpage = request.getParameter("cp");
		String pagesize = request.getParameter("ps");
		String subject = request.getParameter("subject"); // 답글의 제목으로 사용

		ActionForward forward = new ActionForward();

		if (idx == null || subject == null || idx.trim().equals("") || subject.trim().equals("")) {
			forward.setPath("/WEB-INF/board/board_list.jsp");
			return forward;
		}
		if (cpage == null || pagesize == null) {
			cpage = "1";
			pagesize = "5";
		}

		request.setAttribute("idx", idx);
		request.setAttribute("subject", subject);
		request.setAttribute("cpage", cpage);
		request.setAttribute("pagesize", pagesize);
		
		forward.setRedirect(false); //forward 방식
		forward.setPath("/WEB-INF/board/board_rewrite.jsp");
		
		return forward;
	}

}
