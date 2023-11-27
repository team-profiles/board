package kr.or.kosa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Controller 부담.....
//service 영역 (dao ,dto) ...

//서비스 클래스 만들어서 사용시 : 동일한 함수와 리턴타입과 , parameter  가지고 사용
public interface Action {
	ActionForward execute(HttpServletRequest request , HttpServletResponse response);
}
