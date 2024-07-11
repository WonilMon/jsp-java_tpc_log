<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link rel="stylesheet" href="./bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #f7f7f7;
    margin: 0;
    padding: 0;
}

a {
    text-decoration: none;
}

#container {
    width: 70%;
    margin: 40px auto;
    background-color: #ffffff;
    padding: 20px;
    border: 1px solid #cccccc;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    text-align: center;
}

#write {
    width: 100%;
    text-align: right;
    padding: 10px;
}

.table {
    width: 100%;
    margin: 20px 0;
    border-collapse: collapse;
    background-color: #ffffff;
    border: 1px solid #cccccc;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.table th, .table td {
    padding: 12px;
    vertical-align: middle;
    text-align: center;
    border: 1px solid #cccccc;
}

.table th {
    background-color: #e9f5ff;
    color: #333;
}

.table-hover tbody tr:hover {
    background-color: #f1f9ff;
}

input[type="submit"], input[type="reset"], button {
    padding: 10px 20px;
    margin-right: 10px;
    border: none;
    border-radius: 4px;
    background-color: #007bff;
    color: #fff;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s;
}

input[type="submit"]:hover, input[type="reset"]:hover, button:hover {
    background-color: #0056b3;
}

button {
    background-color: #28a745;
}

button:hover {
    background-color: #217530;
}

#paging a {
    margin: 0 5px;
    text-decoration: none;
    color: #007bff;
    transition: color 0.3s;
}

#paging a:hover {
    text-decoration: underline;
    color: #0056b3;
}

h2 {
    color: #333;
}
</style>
</head>
<body>
	<c:if test="${msg == 0 }">
		<script>
			alert("수정 비밀번호가 일치하지 않습니다.");
		</script>
	</c:if>
	<c:if test="${msg == 1 }">
		<script>
			alert("삭제 비밀번호가 일치하지 않습니다.");
		</script>
	</c:if>

	<div id="container">
		<h2>게시판(전체 글: ${count })</h2>
		<div id="write">
			<a href="BoardWrite.jsp">글쓰기</a>
		</div>

		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bean" items="${list }">
					<tr>
						<td>${bean.num }</td>
						<td style="text-align: left">
							<c:if test="${bean.re_step > 1 }">
								<c:forEach var="j" begin="1" end="${(bean.re_step -1) *5 }">
									&nbsp;
								</c:forEach>
							</c:if>
							<a href="BoardInfoCon?num=${bean.num }">
								${bean.subject }
							</a>
						</td>
						<td>${bean.writer }</td>
						<td>${bean.reg_date }</td>
						<td>${bean.readCount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="paging">
			<!-- 페이지 1이상일 때만 나타나게 하기 -->
			<c:if test="${startPage > 1 }">
				<a href="BoardListCon.do?page=${startPage - 1 }">[이전]</a>
			</c:if>
			<c:forEach var="i" begin="${startPage +1}" end="${endPage }">
				<c:choose>
					<c:when test="${i == page }">
						<span>[${i }]</span>	<!-- 내가 있는 페이지와 덱스가 같으면 링크 지워버리기 -->
					</c:when>
					<c:otherwise>
					<a href="BoardListCon.do?page=${i }">[${i }]</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<!-- 마지막 페이지보다 최대 페이지가 더 크면 -->
			<c:if test="${endPage < maxPage }">
				<a href="BoardListCon.do?page=${endPage + 1 }">[다음]</a>
			</c:if>
		</div>
	</div>
</body>
</html>