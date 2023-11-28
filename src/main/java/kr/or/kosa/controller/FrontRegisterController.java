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

import kr.or.kosa.dto.Board;
import kr.or.kosa.dto.Reply;
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

//    	Action action = null;
//    	ActionForward forward = null;

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
				ThePager pager = new ThePager(totalboardcount, cpage, pagesize, pagersize, "boardList.do");
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
				e.printStackTrace();
			}

			viewpage = "/WEB-INF/board/board_list.jsp";

		} else if (urlcommand.equals("/boardwrite.do")) {
			viewpage = "/WEB-INF/board/board_write.jsp";
		}

		else if (urlcommand.equals("/boardwriteok.do")) {

			String resultData = "";
			resultData = "게시글이 성공적으로 작성되었습니다.";
			viewpage = "/WEB-INF/board/board_writeok.jsp";

			request.setAttribute("data", resultData);

			RequestDispatcher dis = request.getRequestDispatcher(viewpage);
			dis.forward(request, response);

		}
    	
    	else if(urlcommand.equals("/boardContent.do")) {
    		String idx= request.getParameter("idx"); //글번호 받기
    		try {
    			//글 번호를 가지고 오지  않았을 경우 예외처리
        		if(idx == null || idx.trim().equals("")){
        			response.sendRedirect("board_list.jsp");
        			return; //더 이상 아래 코드가 실행되지 않고 클라이언트에게 바로 코드 전달
        		}
        		
        		idx=idx.trim();
        		//http://192.168.0.12:8090/WebServlet_5_Board_Model1_Sample/board/board_content.jsp?idx=19&cp=1&ps=5
        		//board_content.jsp?idx=19&cp=1&ps=5  //다시 목록으로 갔을때  ... cp , ps 가지고 ...
        		//why: 목록으로 이동시 현재 page 유지하고 싶어요
        		String cpage = request.getParameter("cp"); //current page
        		String pagesize = request.getParameter("ps"); //pagesize
        		
        		//List 페이지 처음 호출 ...
        		if(cpage == null || cpage.trim().equals("")){
        			//default 값 설정
        			cpage = "1"; 
        		}
        	
        		if(pagesize == null || pagesize.trim().equals("")){
        			//default 값 설정
        			pagesize = "5"; 
        		}
        		
        		//상세보기 내용
        		BoardService service = BoardService.getInBoardService();
        		
        		//옵션
        		//조회수 증가
        		boolean isread = service.addReadNum(idx);
        		if(isread)System.out.println("조회증가 : " + isread);
        		
        		
        		//데이터 조회 (1건 (row))
        		Board board = service.content(Integer.parseInt(idx));
        		
        		
	    		request.setAttribute("idx", idx);
	    		request.setAttribute("board", board);
	    		request.setAttribute("cpage", cpage);
	    		request.setAttribute("pagesize", pagesize);
        		
	    		//------------------------------------
	    		//덧글 목록 보여주기
	    		List<Reply> replylist = service.replyList(idx); //참조하는 글번호
  				request.setAttribute("replylist", replylist);
  				
			} catch (Exception e) {
				System.out.println(e);
			}

    		try {
    			//글 번호를 가지고 오지  않았을 경우 예외처리
        		if(idx == null || idx.trim().equals("")){
        			response.sendRedirect("board_list.jsp");
        			return; //더 이상 아래 코드가 실행되지 않고 클라이언트에게 바로 코드 전달
        		}
        		
        		idx=idx.trim();
        		//http://192.168.0.12:8090/WebServlet_5_Board_Model1_Sample/board/board_content.jsp?idx=19&cp=1&ps=5
        		//board_content.jsp?idx=19&cp=1&ps=5  //다시 목록으로 갔을때  ... cp , ps 가지고 ...
        		//why: 목록으로 이동시 현재 page 유지하고 싶어요
        		String cpage = request.getParameter("cp"); //current page
        		String pagesize = request.getParameter("ps"); //pagesize
        		
        		//List 페이지 처음 호출 ...
        		if(cpage == null || cpage.trim().equals("")){
        			//default 값 설정
        			cpage = "1"; 
        		}
        	
        		if(pagesize == null || pagesize.trim().equals("")){
        			//default 값 설정
        			pagesize = "5"; 
        		}
        		
        		//상세보기 내용
        		BoardService service = BoardService.getInBoardService();
        		
        		//옵션
        		//조회수 증가
        		boolean isread = service.addReadNum(idx);
        		if(isread)System.out.println("조회증가 : " + isread);
        		
        		
        		//데이터 조회 (1건 (row))
        		Board board = service.content(Integer.parseInt(idx));
        		
	    		request.setAttribute("idx", idx);
	    		request.setAttribute("board", board);
	    		request.setAttribute("cpage", cpage);
	    		request.setAttribute("pagesize", pagesize);
        		
	    		//------------------------------------
	    		//덧글 목록 보여주기
	    		List<Reply> replylist = service.replyList(idx); //참조하는 글번호
  				request.setAttribute("replylist", replylist);
  				
			} catch (Exception e) {
				System.out.println(e);
			}
    		
    		viewpage="/WEB-INF/board/board_content.jsp";
    		
    	} 
				
    	else if (urlcommand.equals("/boardEdit.do")) {
    		String idx = request.getParameter("idx");
    		BoardService service = null;
    		Board board = null;
    		String url = "/WEB-INF/board/board_edit.jsp";
    		
    		try {
    			if(idx == null || idx.trim().equals("")){
//    				response.sendRedirect("board_list.jsp"); //cpage=1 , ps=5
    				url = "/WEB-INF/board/board_list.jsp";
    				return;
    			}
    			service = BoardService.getInBoardService();
    			board = service.board_EditContent(idx);
    			if(board == null){
    				url = "/WEB-INF/board/board_list.jsp";
//    				response.sendRedirect("board_list.jsp");
//    				out.print("데이터 오류");
//    				out.print("<hr><a href='board_list.jsp'>목록가지</a>");
//    				return;
    			}
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    		request.setAttribute("board", board);
      		viewpage="/WEB-INF/board/board_edit.jsp";
    	}
		
		
		
		
      	else if (urlcommand.equals("/boardEditOk.do")) {
      		String idx = request.getParameter("idx");
      		try {
      			if (idx == null || idx.strip().equals("")) {
      	            response.sendRedirect("board_list.jsp");
      	            return;
      			}
      			BoardService service = BoardService.getInBoardService();
      			int result = service.board_Edit(request);
      			String msg="";
      			String url="";
      			if(result > 0){
      				msg="edit success";
      				url="board_list.jsp";
      			}else{
      				msg="edit fail";
      				url="board_edit.jsp?idx="+idx;
      			}
      			request.setAttribute("board_msg",msg);
      			request.setAttribute("board_url",url);
      		}catch (Exception e) {
      			e.printStackTrace();
      		}	
//      		viewpage="/WEB-INF/board/board_editok.jsp";
      	}
		
	 
	    else if (urlcommand.equals("/boardDelete.do")) {
	    	viewpage="/WEB-INF/board/board_delete.jsp";
	    }
    	 
      	else if (urlcommand.equals("/boardDeleteOk.do")) {

      		String idx = request.getParameter("idx");
      		String pwd = request.getParameter("pwd");
      		String msg="";
			String url="";
      		BoardService service = BoardService.getInBoardService();
      		try {
				int result =service.board_Delete(idx, pwd);
				if(result > 0){
					msg="delete success";
					url="/boardList.do";
				}else{
					msg="delete fail";
					url="/boardList.do";
					// response.sendRedirect("board_list.jsp");
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
      		request.setAttribute("board_url",url);
      		request.setAttribute("board_msg", msg);
      		viewpage = "/WEB-INF/board/redirect.jsp";

      	} 


      	else if(urlcommand.equals("/boardRewrite.do")) {
      		
			String idx = request.getParameter("idx");
			String cpage = request.getParameter("cp");
			String pagesize = request.getParameter("ps");
			String subject = request.getParameter("subject"); // 답글의 제목으로 사용
			
			if(idx == null || subject == null || idx.trim().equals("") || subject.trim().equals("")){
				viewpage="/WEB-INF/board/board_list.jsp";
				return;
			}
			if(cpage == null || pagesize == null){
				cpage ="1";
				pagesize = "5";
			}
			
			request.setAttribute("idx", idx);
    		request.setAttribute("subject", subject);
    		request.setAttribute("cpage", cpage);
    		request.setAttribute("pagesize", pagesize);
			
      		viewpage="/WEB-INF/board/board_rewrite.jsp";
      	}
    	
      	else if(urlcommand.equals("/boardRewriteOk.do")) {
      		int idx = Integer.parseInt(request.getParameter("idx")); 
      		String writer = request.getParameter("writer");
      		String subject = request.getParameter("subject");
      		String content = request.getParameter("content");
      		String email = request.getParameter("email");
      		String homepage = request.getParameter("homepage");
      		String filename = request.getParameter("filename");
      		String pwd = request.getParameter("pwd"); 
      		
      		String cpage = request.getParameter("cp"); //current page
      		String pagesize = request.getParameter("ps"); //pagesize
      		
      		Board board = new Board();
      		BoardService service = BoardService.getInBoardService();
      		
      		board.setIdx(idx);
      		board.setWriter(writer);
      		board.setSubject(subject);
      		board.setContent(content);
      		board.setEmail(email);
      		board.setHomepage(homepage);
      		board.setFilename(filename);
      		board.setPwd(pwd);
      	
      		try {
				int result = service.rewriteok(board);
				String msg="";
			    String url="";
			    if(result > 0){
			    	msg ="rewrite insert success";
			    	url ="/boardList.do";
			    }else{
			    	msg="rewrite insert fail";
			    	url="/boardContent.do?idx="+idx+"&cp="+cpage+"&ps="+pagesize;
			    	
			    }
			    
			    request.setAttribute("board_msg",msg);
			    request.setAttribute("board_url", url);
			    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		
      		viewpage="/WEB-INF/board/redirect.jsp";

      		
      		}else if (urlcommand.equals("/boardReplyOk.do")) {
			
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
				        url = "/boardContent.do?idx=" + idx_fk;
				    } else {
				        msg = "댓글 입력 실패";
				        url = "/boardContent.do?idx=" + idx_fk;
				    }

				    request.setAttribute("board_msg", msg);
				    request.setAttribute("board_url", url);
				    
			} catch (NumberFormatException e) {
				System.out.println("넘버포맷");
			} catch (NamingException e) {
				System.out.println("네이밍");				
				e.printStackTrace();
			}
		   
		    viewpage = "/WEB-INF/board/redirect.jsp";
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

		RequestDispatcher dis = request.getRequestDispatcher(viewpage);
		// 데이터 전달(forward)
		dis = request.getRequestDispatcher(viewpage);

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