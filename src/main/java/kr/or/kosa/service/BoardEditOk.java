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
				forward.setPath("/WEB-INF/board/board_list.jsp");
				return forward;
			}
			System.out.println(request);
			BoardService service = BoardService.getInBoardService();
			int result = service.board_Edit(request);
			
			String msg = "";
			String url = "";
			if (result > 0) {
				msg = "edit success";
				url = "/boardList.do";
			} else {
				msg = "edit fail";
				url = "/boardContent.do?idx=" + idx;
			}
			request.setAttribute("board_msg", msg);
			request.setAttribute("board_url", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward.setRedirect(false); //forward 방식
		forward.setPath("/WEB-INF/board/redirect.jsp");
		
		return forward;
	}
	
}
