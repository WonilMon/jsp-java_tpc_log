<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세정보</title>
<style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #f9f9f9;
    color: #333;
    margin: 0;
    padding: 0;
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

h2 {
    margin-bottom: 20px;
    color: #007bff;
}

.table {
    width: 100%;
    margin-bottom: 20px;
    border-collapse: collapse;
}

.table th, .table td {
    padding: 12px;
    border: 1px solid #cccccc;
}

.table th {
    background-color: #e9f5ff;
    color: #333;
}

.table-hover tbody tr:hover {
    background-color: #f1f9ff;
}

input[type="button"] {
    padding: 10px 20px;
    margin: 10px 5px;
    border: none;
    border-radius: 4px;
    background-color: #007bff;
    color: #ffffff;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.3s;
}

input[type="button"]:hover {
    background-color: #0056b3;
}

.hit {
    animation: blink 1.5s ease infinite;
}

@keyframes blink {
    0%, 100% { color: #ffffff; }
    50% { color: #ff0000; font-weight: bold; }
}
</style>
</head>
<body>
	<div id="container">
		<h2>게시글 보기</h2>
		<table class="table table-striped table-bordered table-hover">
			<tbody>
				<tr>
					<th>글번호</th>
					<td>${bean.num }</td>
					<th>조회수</th>
					<td>${bean.readCount }</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${bean.writer }</td>
					<th>작성일</th>
					<td>${bean.reg_date }</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td colspan="3">${bean.email }</td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3">${bean.subject }</td>
				</tr>
				<tr>
					<th>글내용</th>
					<td colspan="3">${bean.content }</td>
				</tr>
				<tr>
					<th colspan="4">
						<input type="button" value="답글달기" onclick="location.href='BoardReWriteCon.do?ref=${bean.ref}&re_step=${bean.re_step }&re_level=${bean.re_level }'" />
						<input type="button" value="글수정" onclick="location.href='BoardUpdateCon.do?num=${bean.num}'"/>
						<input type="button" value="글삭제" onclick="location.href='BoardDeleteCon.do?num=${bean.num}'"/>
						<input type="button" value="목록보기" onclick="location.href='BoardListCon.do'"/>
					</th>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>