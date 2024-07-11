<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<!-- BootStrap -->
<link rel="stylesheet" href="./bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #f7f7f7;
}

#container {
    width: 70%;
    margin: 40px auto;
    background-color: #ffffff;
    padding: 20px;
    border: 1px solid #cccccc;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.table {
    width: 100%;
    margin-top: 20px;
    border-collapse: collapse;
}

.table th, .table td {
    padding: 12px;
    vertical-align: middle;
    border: 1px solid #cccccc;
}

.table th {
    background-color: #e9f5ff;
    color: #333;
    text-align: right;
}

input[type="text"], input[type="password"], textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

textarea {
    resize: vertical;
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

h2 {
    text-align: center;
    color: #333;
}
</style>
</head>
<body>
	<div id="container">
		<h2>게시글 수정하기</h2>
		<form action="BoardUpdateProcCon" method="post">
			<table class="table table-striped table-bordered">
				<tr>
					<th>작성자</th>
					<td>${bean.writer }</td>
					<th>작성일</th>
					<td>${bean.reg_date }</td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan="3">
						<input type="text" size="60" value="${bean.subject }" name="subject"/>
					</td>
				</tr>
				<tr>
					<th>패스워드</th>
					<td colspan="3">
						<input type="password" size="60" name="password"/>
					</td>
				</tr>
				<tr>
					<th>글내용</th>
					<td colspan="3">
						<textarea rows="10" cols="60" align="left" name="content">${bean.content }</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<input type="hidden" name="num" value="${bean.num }"/>
						<input type="hidden" name="pass" value="${bean.password }"/>
						<input type="submit" value="수정하기" />
						<input type="reset" value="취소" />
						<button type="button" onclick="location.href='BoardListCon.do'">전체글보기</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>