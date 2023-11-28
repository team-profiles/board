package kr.or.kosa.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dto.Board;
import kr.or.kosa.dto.Reply;
import kr.or.kosa.service.BoardContent;
import kr.or.kosa.service.BoardDeleteOk;
import kr.or.kosa.service.BoardEdit;
import kr.or.kosa.service.BoardEditOk;
import kr.or.kosa.service.BoardList;
import kr.or.kosa.service.BoardReplyOk;
import kr.or.kosa.service.BoardRewrite;
import kr.or.kosa.service.BoardRewriteOk;
import kr.or.kosa.service.BoardService;
import kr.or.kosa.service.BoardWriteOk;

@WebServlet("*.do")
public class FrontRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontRegisterController() {
		super();

	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String urlcommand = requestUri.substring(contextPath.length());
		System.out.println("requestUri : " + requestUri);
		System.out.println("contextPath : " + contextPath);
		System.out.println("urlcommand : " + urlcommand);

		// 3. 요청 서비스 판단 (command 통해서) 문자열 비교
		// 3.1 판단에 의해서 서비스 동작 (DB작업 , 암호화 , ....)

    	Action action = null;
    	ActionForward forward = null;

		if (urlcommand.equals("/boardList.do")) {
			action = new BoardList();
    		forward = action.execute(request, response);

		} else if (urlcommand.equals("/boardwrite.do")) {
			forward = new ActionForward();
    		forward.setRedirect(false);
    		forward.setPath("/WEB-INF/board/board_write.jsp");
    		
		} else if (urlcommand.equals("/boardwriteok.do")) {
			action = new BoardWriteOk();
    		forward = action.execute(request, response);
    		
		} else if (urlcommand.equals("/boardContent.do")) {
			action = new BoardContent();
    		forward = action.execute(request, response);
    		
		} else if (urlcommand.equals("/boardEdit.do")) {
			action = new BoardEdit();
    		forward = action.execute(request, response);
		}

		else if (urlcommand.equals("/boardEditOk.do")) {
			action = new BoardEditOk();
    		forward = action.execute(request, response);
		}

		else if (urlcommand.equals("/boardDelete.do")) {
			forward = new ActionForward();
    		forward.setRedirect(false);
    		forward.setPath("/WEB-INF/board/board_delete.jsp");
		}

		else if (urlcommand.equals("/boardDeleteOk.do")) {
			action = new BoardDeleteOk();
    		forward = action.execute(request, response);

		}

		else if (urlcommand.equals("/boardRewrite.do")) {
			action = new BoardRewrite();
    		forward = action.execute(request, response);
		}

		else if (urlcommand.equals("/boardRewriteOk.do")) {
			action = new BoardRewriteOk();
    		forward = action.execute(request, response);

		} else if (urlcommand.equals("/boardReplyOk.do")) {
			action = new BoardReplyOk();
    		forward = action.execute(request, response);
		}

     	if(forward != null) {
    		if(forward.isRedirect()) { // true // location.href="" 새로운 페이지 처리
    			response.sendRedirect(forward.getPath());
    		}else {
	    	    	RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
	      	    	dis.forward(request, response);
    			}
    		}
    	

		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

}
