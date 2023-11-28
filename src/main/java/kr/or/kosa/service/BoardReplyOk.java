package kr.or.kosa.service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;

public class BoardReplyOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String ps = request.getParameter("ps");
		String cp = request.getParameter("cp");
		// 데이터 받기
		String writer = request.getParameter("reply_writer");
		String content = request.getParameter("reply_content");
		String pwd = request.getParameter("reply_pwd");
		String idx_fk = request.getParameter("idx");
		System.out.println(idx_fk + "idx");
		String userid = "empty";
		System.out.println(userid);
		// service 객체 생성
		BoardService service = BoardService.getInBoardService();
		int result;
		try {
			result = service.replyWrite(Integer.parseInt(idx_fk), writer, userid, content, pwd);
			String msg = "";
			String url = "";

			if (result > 0) {
				msg = "댓글 입력 성공";
				url = "/boardContent.do?idx=" + idx_fk + "&cp=" + cp + "&ps=" + ps;
			} else {
				msg = "댓글 입력 실패";
				url = "/boardContent.do?idx=" + idx_fk + "&cp=" + cp + "&ps=" + ps;
			}

			request.setAttribute("board_msg", msg);
			request.setAttribute("board_url", url);

		} catch (NumberFormatException e) {
			System.out.println("넘버포맷");
		} catch (NamingException e) {
			System.out.println("네이밍");
			e.printStackTrace();
		}
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false); //forward 방식
		forward.setPath("/WEB-INF/board/redirect.jsp");

		return forward;
	}

}
