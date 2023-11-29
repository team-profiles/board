package kr.or.kosa.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dto.Board;

public class BoardWriteOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String writer = request.getParameter("writer");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String email = request.getParameter("email");
		String homepage = request.getParameter("homepage");
		String filename = request.getParameter("filename");
		String pwd = request.getParameter("pwd");

		Board board = new Board();
		BoardService service = BoardService.getInBoardService();

		board.setWriter(writer);
		board.setSubject(subject);
		board.setContent(content);
		board.setEmail(email);
		board.setHomepage(homepage);
		board.setFilename(filename);
		board.setPwd(pwd);

		try {
			int result = service.writeOk(board);
			String msg = "";
			if (result > 0) {
				msg = "write insert success";
			} else {
				msg = "write insert fail";

			}
			String url = "/boardList.do";
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
