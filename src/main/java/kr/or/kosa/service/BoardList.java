package kr.or.kosa.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dto.Board;
import kr.or.kosa.utils.ThePager;

public class BoardList implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
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
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false); //forward 방식
		forward.setPath("/WEB-INF/board/board_list.jsp");

		return forward;
	}

}
