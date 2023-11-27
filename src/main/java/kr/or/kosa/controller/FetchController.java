package kr.or.kosa.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;

/**
 * Servlet implementation class FetchRegisterController
 */
@WebServlet("/fetch")
public class FetchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FetchController() {
        super();
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
       	Action action = null;
    	RequestDispatcher dis = null;



    	String command = request.getParameter("cmd"); 
    	System.out.println(command);

    	if(command.equals("fetchOk")) {


    	}else {
    		
    	}
//		request.setAttribute("memolist", memolist);
		// view 페이지 지정
//		dis = request.getRequestDispatcher("/memolist.jsp");
    	
		dis.forward(request, response);

    	
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}