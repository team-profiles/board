package kr.or.kosa.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.or.kosa.action.Action;
import kr.or.kosa.action.ActionForward;
import kr.or.kosa.dto.Reply;

public class BoardReplyOk implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
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
        String msg = "";
        String url = "";

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
                response.getWriter().print(jsonArray.toString());

            }

            // Set the redirect URL based on the result
            //url = "/boardContent.do?idx=" + idx_fk + "&cp=" + cp + "&ps=" + ps;

        } catch (Exception e) {
            e.printStackTrace();
        }

        ActionForward forward = new ActionForward();
        forward.setRedirect(false); // forward 방식
        forward.setPath(url);

        return forward;
    }
}
