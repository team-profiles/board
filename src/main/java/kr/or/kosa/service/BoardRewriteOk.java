package kr.or.kosa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dto.Board;

public class BoardRewriteOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		int idx = Integer.parseInt(request.getParameter("idx"));
		String writer = request.getParameter("writer");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String email = request.getParameter("email");
		String homepage = request.getParameter("homepage");
		String filename = request.getParameter("filename");
		String pwd = request.getParameter("pwd");

		String cpage = request.getParameter("cp"); // current page
		String pagesize = request.getParameter("ps"); // pagesize

		Board board = new Board();
		BoardService service = BoardService.getInBoardService();

		board.setIdx(idx);
		board.setWriter(writer);
		board.setSubject(subject);
		board.setContent(content);
		board.setEmail(email);
		board.setHomepage(homepage);
		board.setFilename(filename);
		board.setPwd(pwd);

		try {
			int result = service.rewriteok(board);
			String msg = "";
			String url = "";
			if (result > 0) {
				msg = "rewrite insert success";
				url = "/boardList.do";
			} else {
				msg = "rewrite insert fail";
				url = "/boardContent.do?idx=" + idx + "&cp=" + cpage + "&ps=" + pagesize;

			}

			request.setAttribute("board_msg", msg);
			request.setAttribute("board_url", url);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false); //forward 방식
		forward.setPath("/WEB-INF/board/redirect.jsp");

		return forward;
	}

}
