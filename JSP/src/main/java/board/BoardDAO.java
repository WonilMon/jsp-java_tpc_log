package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {

	//#1 데이터베이스 연결
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	public void getCon() {

		try {
			Context initContext = new InitialContext();
			System.out.println("1." + initContext);
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			System.out.println("2." + envContext);
			DataSource ds = (DataSource)envContext.lookup("jdbc/xe");
			System.out.println("3." + ds);
			con = ds.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//#2 게시글 입력
	public void insertBoard(BoardDTO bean) {

		getCon();

		int ref = 0;
		int re_step = 1;
		int re_level = 1;

		try {
			String resql = "select max(ref) from board";
			pstmt = con.prepareStatement(resql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				ref = rs.getInt(1) + 1;
			}

			String sql = "insert into board values(board_seq.nextval,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());

			pstmt.executeUpdate();
			disconnect();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//#3 전체 게시글 개수
	public int getallCount() {
		getCon();
		int count = 0;

		try {
			String sql = "select count(*) from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
	//#4 전체 게시글 목록
		public ArrayList<BoardDTO> getAllBoard(int page, int limit){

			getCon();
			ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

			try {
				//#9 페이징
				int startRow = (page-1)*limit +1;	//현 페이지에서 가장 위에 보일 게시글
				int endRow = page * limit;			//현 페이지에서 가장 아래에 보일 게시글
				//1페이지면 startRow가 1, endRow가 10
				//2페이지면 startRow가 11, endRow가 20
				//2페이지면 startRow가 21, endRow가 30

				//String sql = "select * from board"; => 순서X
				String sql = "select * from"
						+ "(select rownum rnum, A.* from"
						+ "(select * from board order by ref desc, re_step asc)A "
						+ "where rownum<=?)where rnum>=?";
				// => 게시글의 순서(최신글은 위로, 원글-답글-대답글 순으로 배치)
				pstmt = con.prepareStatement(sql);

				pstmt.setInt(1, endRow);
				pstmt.setInt(2, startRow);

				rs = pstmt.executeQuery();

				while(rs.next()) {

					BoardDTO bean = new BoardDTO();

					bean.setNum(rs.getInt("num"));
					bean.setWriter(rs.getString("writer"));
					bean.setEmail(rs.getString("email"));
					bean.setSubject(rs.getString("subject"));
					bean.setPassword(rs.getString("password"));
					bean.setReg_date(rs.getDate("reg_date").toString());
					bean.setRef(rs.getInt("ref"));
					bean.setRe_step(rs.getInt("re_step"));
					bean.setRe_level(rs.getInt("re_level"));
					bean.setReadCount(rs.getInt("readcount"));
					bean.setContent(rs.getString("content"));

					list.add(bean);
				}
				disconnect();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return list;
	}

	//#5 하나의 게시글을 가져오기(상세페이지)
	public BoardDTO getOneBoard(int num) {

		getCon();
		BoardDTO bean = null;

		try {
			//게시글을 불러오기 전 조회수 +1
			String countSql = "update board set readcount = readcount+1 where num = ?";
			pstmt = con.prepareStatement(countSql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

			//하나의 게시글 불러오기
			String sql = "select * from board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if(rs.next()) {

				bean = new BoardDTO();

				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadCount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
			disconnect();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	//#6 게시글 수정
	public void updateBoard(int num, String subject, String content) {

		getCon();

		try {
			String sql = "update board set subject = ?, content = ? where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);

			pstmt.executeUpdate();

			disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//#7 게시글 삭제
	public void deleteBoard(int num) {

		getCon();

		try {
			String sql = "delete from board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

			disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//#8 답글 작성
	public void reInserBoard(BoardDTO bean) {

		getCon();
		int ref = bean.getRef();
		int re_step = bean.getRe_step();
		int re_level = bean.getRe_level();

		try {

			String levelSql = "update board set re_level = re_level+1 where ref=? and re_level > ?";
			pstmt = con.prepareStatement(levelSql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_level);
			pstmt.executeUpdate();

			String sql = "insert into board values(board_seq.nextval, ?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step + 1);
			pstmt.setInt(7, re_level + 1);
			pstmt.setString(8, bean.getContent());

			pstmt.executeUpdate();
			disconnect();

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 자원반납 : 꽉 차있으면 끊어버릴 수 있도록
	public void disconnect () {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if(con!=null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
