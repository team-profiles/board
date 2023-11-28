package kr.or.kosa.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dto.Board;
import kr.or.kosa.service.BoardService;
import kr.or.kosa.utils.ThePager;

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
		String viewpage = "";

		Action action = null;
		ActionForward forward = null;

		Board board = new Board();

		String writer = request.getParameter("writer");
		String pwd = request.getParameter("pwd");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String email = request.getParameter("email");
		String homepage = request.getParameter("homepage");
		String filename = request.getParameter("filename");
		
		board.setSubject(subject);
		board.setWriter(writer);
		board.setEmail(email);
		board.setHomepage(homepage);
		board.setContent(content);
		board.setPwd(pwd);
		board.setFilename(filename);

		if (urlcommand.equals("/boardList.do")) {
			BoardService service = BoardService.getInBoardService();

			// 게시물 총 건수
			int totalboardcount;
			try {
				totalboardcount = service.totalBoardCount();
				// 상세보기 >> 다시 LIST 넘어올때 >> 현재 페이지 설정
				String ps = request.getParameter("ps"); // pagesize
				String cp = request.getParameter("cp"); // current page

				// List 페이지 처음 호출 ...
				if (ps == null || ps.trim().equals("")) {
					// default 값 설정
					ps = "5"; // 5개씩
				}

				if (cp == null || cp.trim().equals("")) {
					// default 값 설정
					cp = "1"; // 1번째 페이지 보겠다
				}

				int pagesize = Integer.parseInt(ps);
				int cpage = Integer.parseInt(cp);
				int pagecount = 0;

				// 23건 % 5
				if (totalboardcount % pagesize == 0) {
					pagecount = totalboardcount / pagesize; // 20 << 100/5
				} else {
					pagecount = (totalboardcount / pagesize) + 1;
				}

				int pagersize = 3; // [1][2][3]
				ThePager pager = new ThePager(totalboardcount, cpage, pagesize, pagersize, "board_list.jsp");
				String pagerToString = pager.toString();
				// 102건 : pagesize=5 >> pagecount=21페이지

				// 전체 목록 가져오기
				List<Board> list = service.list(cpage, pagesize); // list >> 1 , 20
				int listSize = list.size();
				request.setAttribute("totalboardcount", totalboardcount);
				request.setAttribute("cpage", cpage);
				request.setAttribute("pagesize", pagesize);
				request.setAttribute("pagecount", pagecount);
				request.setAttribute("pagerToString", pagerToString);
				request.setAttribute("list", list);
				request.setAttribute("listSize", listSize);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			viewpage = "/WEB-INF/board/board_list.jsp";

		}else if (urlcommand.equals("/boardContent.do")) {

		} else if (urlcommand.equals("/boardEdit.do")) {

		}else if (urlcommand.equals("/boardDelete.do")) {

		} else if (urlcommand.equals("/boardRewrite.do")) {

		}else if (urlcommand.equals("/boardRewriteOk.do")) {
//      		BoardService service = BoardService.getInBoardService();
//      		int result = service.rewriteok(board);
//      		
//      		//list 이동시 현재 pagesize , cpage
//      		String cpage = request.getParameter("cp"); //current page
//      		String pagesize = request.getParameter("ps"); //pagesize
//      		//코드는 필요에 따라서  url ="board_list.jsp?cp=<%=cpage";
//      		String msg="";
//      	    String url="";
//      	    if(result > 0){
//      	    	msg ="rewrite insert success";
//      	    	url ="board_list.jsp";
//      	    }else{
//      	    	msg="rewrite insert fail";
//      	    	url="board_content.jsp?idx="+board.getIdx();
//      	    }
//      	    
//      	    request.setAttribute("board_msg",msg);
//      	    request.setAttribute("board_url", url);
		} else if (urlcommand.equals("boardRewriteEdit.do")) {

		} else if (urlcommand.equals("/boardwrite.do")) {
			viewpage = "/WEB-INF/board/board_write.jsp";
		} else if (urlcommand.equals("/boardwriteok.do")) {
//      	action = new WriteOkServiceAction();
//    		forward = action.execute(request, response);
			int result = 0;
			BoardService service = BoardService.getInBoardService();
			try {
				result = service.writeOk(board);
				System.out.println(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewpage = "/WEB-INF/board/board_list.jsp";
		}

//     	if(forward != null) {
//    		if(forward.isRedirect()) { // true // location.href="" 새로운 페이지 처리
//    			response.sendRedirect(forward.getPath());
//    		}else {
//	    	    	RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
//	      	    	dis.forward(request, response);
//    			}
//    		}

		RequestDispatcher dis = request.getRequestDispatcher(viewpage);
		// 데이터 전달(forward)
		dis.forward(request, response);
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
