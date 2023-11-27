package kr.or.kosa.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;


@WebServlet("*.do")
public class FrontRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FrontRegisterController() {
        super();
       
    }
    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	String requestUri = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String urlcommand = requestUri.substring(contextPath.length()); 
       	System.out.println("requestUri : " + requestUri);
    	System.out.println("contextPath : " + contextPath);
    	System.out.println("urlcommand : " + urlcommand);
    	
    	RequestDispatcher dis = null;
    	
    	//3. 요청 서비스 판단 (command 통해서) 문자열 비교
    	//3.1 판단에 의해서 서비스 동작 (DB작업 , 암호화 , ....)
//    	String viewpage="";
    	
//    	Action action = null;
//    	ActionForward forward = null;
    	
    	if(urlcommand.equals("/boardList.do")) {
//    		request.setAttribute("memolist", memolist);
    		// view 페이지 지정
//    		dis = request.getRequestDispatcher("/memolist.jsp");
    	}
    	
    	else if(urlcommand.equals("/boardContent.do")) {

    	} 
      	else if (urlcommand.equals("/boardEdit.do")) {

    	}
    	
      	else if (urlcommand.equals("/boardDelete.do")) {

   	} 
      	else if(urlcommand.equals("/boardRewrite.do")) {

      	}
      	else if (urlcommand.equals("/boardRewrite.do/boardRewriteEdit.do")) {

      	}
      	else if (urlcommand.equals("/boardwrite.do")) {

      	}
      	
//     	if(forward != null) {
//    		if(forward.isRedirect()) { // true // location.href="" 새로운 페이지 처리
//    			response.sendRedirect(forward.getPath());
//    		}else {
//	    	    	RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
//	      	    	dis.forward(request, response);
//    			}
//    		}
//    	}

		// 데이터 전달(forward)
		dis.forward(request, response);
    }
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
    