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
import kr.or.kosa.dto.Reply;
import kr.or.kosa.service.BoardReplyOk;
import kr.or.kosa.service.BoardService;

/**
 * Servlet implementation class FetchRegisterController
 */
@WebServlet("/boardReplyOk.do")
public class FetchController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FetchController() {
        super();
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	boolean check = false;

    	BoardService service = BoardService.getInBoardService();
    	String idx = request.getParameter("idx");

    	int result = service.replyWrite(0, idx, idx, idx, idx);
    	if(result) {
    		check = true;
    	}
    	if(result) {
    		try {
				List<Reply> replylist = service.replyList(idx);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	
    	String jsonResponse = "{\"check\": " + check + "}";
        response.setContentType("application/json");
        
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}