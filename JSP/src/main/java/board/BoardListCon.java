package board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BoardListCon.do")
public class BoardListCon extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BoardDAO bdao = new BoardDAO();

		int count = 0;
		count = bdao.getallCount();//전체 게시글의 수

		int page = 1; //기본 페이지
		int limit = 10; //페이지당 게시글 수

		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		list = bdao.getAllBoard(page, limit);	//전체 게시글 가져오기

		int maxPage = (count + limit -1)/limit;	//전체 페이지
		//게시글 1개 => 전체 페이지 1
		//게시글 10개 => 전체 페이지 1
		//게시글 11개 => 전체 페이지 11
		//게시글 53개 => 전체 페이지 6

		int startPage = ((page-1)/10)*(10+1);
		//현재 페이지가 1이면 시작페이지 1
		//현재 페이지가 2이면 시작페이지 1
		//현재 페이지가 11이면 시작페이지 11
		int endPage = startPage + 10 - 1;
		//시작 페이지 1이면 마지막 페이지는 10
		//시작 페이지 2이면 마지막 페이지는 20

		if(endPage > maxPage) {
			endPage = maxPage;
		}

		request.setAttribute("page", page);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);

		request.setAttribute("count", count);
		request.setAttribute("list", list);

		RequestDispatcher dis = request.getRequestDispatcher("BoardList.jsp");
		dis.forward(request, response);
	}

}
