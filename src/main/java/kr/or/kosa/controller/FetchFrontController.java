package kr.or.kosa.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dto.Reply;
import kr.or.kosa.service.BoardReplyOk;
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
		
    	if(urlcommand.equals("/boardReplyOk.fetch")) {
            int ps = Integer.parseInt(request.getParameter("ps"));
            int cp = Integer.parseInt(request.getParameter("cp")); // 데이터 받기
            String userid = "empty";
            String writer = request.getParameter("reply_writer");
            String content = request.getParameter("reply_content");
            String pwd = request.getParameter("reply_pwd");
            String idx_fk = request.getParameter("idx");
            // service 객체 생성
            BoardService service = BoardService.getInBoardService();
            int result;

            try {
                result = service.replyWrite(Integer.parseInt(idx_fk), writer, userid, content, pwd);

                if (result > 0) {
                    // If the write operation is successful, retrieve the updated reply list
                    List<Reply> replyList = service.replyList(idx_fk);

                    // Convert the reply list to JSON array
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

                }

                // Set the redirect URL based on the result
                //url = "/boardContent.do?idx=" + idx_fk + "&cp=" + cp + "&ps=" + ps;

            } catch (Exception e) {
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
