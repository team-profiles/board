package kr.or.kosa.service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;

public class BoardReplyDeleteOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String idx_fk=request.getParameter("idx");//댓글의 원본 게시글 번호
		String no = request.getParameter("no");//댓글의 순번(PK)
		String pwd = request.getParameter("delPwd");//댓글의 암호
		System.out.println(idx_fk + "/" + no + "/" + pwd + "/");
		
		BoardService service = BoardService.getInBoardService();
		String msg="";
	    String url="";
		try {
			int result =service.replyDelete(no, pwd);
			
		    if(result > 0){
		    	msg ="댓글 삭제 성공";
		    	url ="/boardContent.do?idx="+idx_fk;
		    }else{
		    	msg="댓글 삭제 실패";
		    	url="/boardContent.do?idx="+idx_fk;
		    }
		} catch (NamingException e) {
			e.printStackTrace();
		}
	    request.setAttribute("board_msg",msg);
	    request.setAttribute("board_url", url);
		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false); //forward 방식
		forward.setPath("/WEB-INF/board/redirect.jsp");
		
		return forward;
	}

}
