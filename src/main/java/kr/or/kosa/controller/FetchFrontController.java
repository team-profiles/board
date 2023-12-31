package kr.or.kosa.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.or.kosa.dto.Reply;
import kr.or.kosa.service.BoardService;

/**
 * Servlet implementation class FetchFrontController
 */
@WebServlet("*.fetch")
public class FetchFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchFrontController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String urlcommand = requestUri.substring(contextPath.length());
		
    	if(urlcommand.equals("/boardInsert.fetch")) {
            String userid = "empty";
            String writer = request.getParameter("reply_writer");
            String content = request.getParameter("reply_content");
            String pwd = request.getParameter("reply_pwd");
            
            BoardService service = BoardService.getInBoardService();
            String idx_fk = request.getParameter("idx");
            // service 객체 생성

            try {
            	int result = service.replyWrite(Integer.parseInt(idx_fk), writer, userid, content, pwd);
            	
			} catch (NumberFormatException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


            } else if(urlcommand.equals("/boardDelete.fetch")) {
            	String no = request.getParameter("no");
                String password = request.getParameter("delPwd");
                System.out.println(no);

                BoardService service = BoardService.getInBoardService();
                
                int success;
                try {
                    success = service.replyDelete(no, password);
                    if (success>0) {
                        response.getWriter().write("success"); 
                    } else {
                        response.getWriter().write("failure"); 
                    }
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
            
            } else if(urlcommand.equals("/boardReply.fetch")) {
                BoardService service = BoardService.getInBoardService();
                String idx_fk = request.getParameter("idx");
            	List<Reply> replyList;
				try {
					replyList = service.replyList(idx_fk);
					JSONArray jsonArray = new JSONArray();
                    for (Reply reply : replyList) {
                        JSONObject jsonObject = new JSONObject(reply);
                        jsonArray.put(jsonObject);
                    }
                    // Send the JSON array as a response
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    // response를 의미한다.
                    response.getWriter().print(jsonArray.toString());
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }

    }
    	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
