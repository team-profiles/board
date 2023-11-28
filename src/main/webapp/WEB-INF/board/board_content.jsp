<%@page import="kr.or.kosa.dto.Reply"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.kosa.dto.Board"%>
<%@page import="kr.or.kosa.service.BoardService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>board_content</title>
<link rel="Stylesheet"
	href="style/default.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

</head>
<body>

	<%
		pageContext.include("/include/header.jsp");
	%>
	<div id="pageContainer">
		<div style="padding-top: 30px; text-align: center">
			<center>
				<b>게시판 글내용</b>
				<table width="80%" border="1">
					<tr>
						<td width="20%" align="center"><b> 글번호 </b></td>
						<td width="30%">${idx}</td>
						<td width="20%" align="center"><b>작성일</b></td>
						<td>${board.writedate}</td>
					</tr>
					<tr>
						<td width="20%" align="center"><b>글쓴이</b></td>
						<td width="30%">${board.writer}</td>
						<td width="20%" align="center"><b>조회수</b></td>
						<td>${board.readnum}</td>
					</tr>
					<tr>
						<td width="20%" align="center"><b>홈페이지</b></td>
						<td>${board.homepage}</td>
						<td width="20%" align="center"><b>첨부파일</b></td>
						<td>${board.filename}</td>
					</tr>
					<tr>
						<td width="20%" align="center"><b>제목</b></td>
						<td colspan="3">${board.subject}</td>
					</tr>
					<tr height="100">
						<td width="20%" align="center"><b>글내용</b></td>
						<td colspan="3">
						<c:if test="${board.content != null}">
							${fn:replace(board.content, '\\n', '<br>')}
						</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
						<a href="${pageContext.request.contextPath}/boardList.do?cp=${cpage}&ps=${pagesize}">목록가기</a> |
						<a href="${pageContext.request.contextPath}/boardEdit.do?idx=${idx}&cp=${cpage}&ps=${pagesize}">편집</a>	|
						<a href="${pageContext.request.contextPath}/boardDelete.do?idx=${idx}&cp=${cpage}&ps=${pagesize}">삭제</a> |
						<a href="${pageContext.request.contextPath}/boardRewrite.do?idx=${idx}&cp=${cpage}&ps=${pagesize}&subject=${board.subject}">답글</a>
						
						</td>
					</tr>
				</table>
				<!--  꼬리글 달기 테이블 -->
				<form name="reply" id="replyForm">
						<!-- hidden 태그  값을 숨겨서 처리  -->
						<input type="hidden" name="idx" value="${idx}"> 
						<input type="hidden" name="userid" value=""><!-- 추후 필요에 따라  -->
						<!-- hidden data -->
						<table width="80%" border="1">
							<tr>
								<th colspan="2">덧글 쓰기</th>
							</tr>
							<tr>
								<td align="left">작성자 :
								 	<input type="text" name="reply_writer"><br /> 
								 	내&nbsp;&nbsp;용 : 
								 	<textarea name="reply_content" rows="2" cols="50"></textarea>
								</td>
								<td align="left">
									비밀번호:
									<input type="password" name="reply_pwd" size="4"> 
									<input type="button" value="등록" id="btn" onclick="reply_check()">
								</td>
							</tr>
						</table>
				</form>
				<!-- 유효성 체크	 -->
<script type="text/javascript">
	window.onload = fetch_reply();
	
    function fetch_reply() {
        $.ajax({
            url: "${pageContext.request.contextPath}/boardReply.fetch",
            type: "get",
            data: {'idx': document.getElementsByName('idx')[0].value},
            dataType: "json",
            success: function(data) {
                // Clear the existing data
                $('#replyBody').empty();

                // Append new data to the tbody
                $.each(data, function (index, reply) {
                    addComment(reply.writer, reply.content, reply.writedate, reply.no, reply.idx_fk);
                });
            },
            error: function(xhr) {
                alert("데이터 로딩 실패");
            }
        });
    };
    
    function addComment(writer, content, writedate, no, idx_fk) {
        // 동적으로 댓글 행을 생성하고 추가
        var dynamicCommentsContainer = $("#replyBody");

        var newRow = $("<tr>").attr("align", "left");

        var newCell1 = $("<td>").attr("width", "80%").html("[" + writer + "]" + ":" + content + "<br>작성일:" + writedate);

        var newCell2 = $("<td>").attr("width", "20%");
        var deleteForm = $("<form>").attr({
            action: "${pageContext.request.contextPath}/boardreply_deleteOk.jsp",
            method: "POST",
            name: "replyDel"
        });

        var hiddenNo = $("<input>").attr({
            type: "hidden",
            name: "no",
            value: no
        });
        deleteForm.append(hiddenNo);

        var hiddenIdx = $("<input>").attr({
            type: "hidden",
            name: "idx",
            value: idx_fk
        });
        deleteForm.append(hiddenIdx);

        var passwordInput = $("<input>").attr({
            type: "password",
            name: "delPwd",
            size: "4"
        });

        var passwordLabel = $("<label>").html("password:");
        deleteForm.append(passwordLabel);

        deleteForm.append(passwordInput);

        var deleteButton = $("<input>").attr({
            type: "button",
            value: "삭제",
            name:"del",
            id: 'delIdx' + no
        }).click(function() {
            reply_del(this.form);
        });
        deleteForm.append(deleteButton);

        newCell2.append(deleteForm);

        newRow.append(newCell1, newCell2);
        dynamicCommentsContainer.append(newRow);
    }

    function reply_check() {
        var frm = document.reply;
        if (frm.reply_writer.value == "" || frm.reply_content.value == "" || frm.reply_pwd.value == "") {
            alert("리플 내용, 작성자, 비밀번호를 모두 입력해야합니다.");
            return false;
        }
        
        $.ajax({
            url: "${pageContext.request.contextPath}/boardInsert.fetch",
            type: "get",
            data: { 
                'idx': document.getElementsByName('idx')[0].value,
                'userid': document.getElementsByName('userid')[0].value,
                'reply_writer': document.getElementsByName('reply_writer')[0].value,
                'reply_content': document.getElementsByName('reply_content')[0].value,
                'reply_pwd': document.getElementsByName('reply_pwd')[0].value
            },
            success: function(data) {
                // Clear the existing data
                console.log(data);
                fetch_reply();
                
            },
            error: function(xhr) {
                alert("데이터 로딩 실패");
                console.log(xhr);
            }
        });
       
       
    };

    
    function reply_del(frm) {
	    if (frm.delPwd.value === "") {
	        alert("비밀번호를 입력하세요");
	        frm.delPwd.focus();
	        return false;
	    }

	    // Ajax를 사용해 서블릿에 요청 보내기
	    $.ajax({
	        url: "${pageContext.request.contextPath}/boardDelete.fetch",
	        type: "POST",
	        data: {
	            no: frm.no.value,
	            delPwd: frm.delPwd.value
	        },
	        success: function(response) {
	            if (response === "success") {
	                alert("댓글 삭제 성공");
	                console.log(response);
	                fetch_reply();
	                // 여기서 삭제 후의 동작을 수행할 수 있어요
	            } else {
	                alert("댓글 삭제 실패");
	            }
	            
	        },
	        error: function(xhr) {
	            console.log(xhr);
	            alert("댓글 삭제 중 오류가 발생했습니다.");
	        }
	    });
	   
	    // 폼 전송을 막기 위해 false 반환
	    return false;
	}
</script>

				<br>
				<!-- 꼬리글 목록 테이블 -->
					<c:if test="${list eq null || listSize eq 0}">
					<tr><td colspan='5'>데이터가 없습니다</td></tr>
				
						<table width="80%" border="1" id="reply">
							<tr>
								<th colspan="2">REPLY LIST</th>
							</tr>
							<tbody id="replyBody">

							</tbody>
					
					</table>
				</c:if>
			</center>
		</div>
	</div>
</body>
</html>