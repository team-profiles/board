package kr.or.kosa.action;

import lombok.Data;

/*
 servlet 요청
 
  1. 화면을 보여 주세요 (UI)
  2. 처리해 주세요     (SERVICE)
  
 */

@Data
public class ActionForward {
   private boolean isRedirect = false; //뷰의 전환 (redirect <> forward)
   private String path=null; //이동경로 (뷰의 주소)
 	   
}
