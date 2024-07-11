package kr.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.soldesk.BookDTO;

public class Project01_A {

	public static void main(String[] args) {

		// 객체(objcect) => Json(String)
		BookDTO book = new BookDTO("JAVA", 21000, "에어콘", 680);
		// 이 객체를 외부와 왔닥갔다 연결 할 수 있는 json 으로 만들거임

		Gson g = new Gson();
		// Gson : 자바객체를 Json으로 반환 라이브러리

		String json = g.toJson(book);
		// 객체를 json 으로 반환

		System.out.println("----object => Json----");
		System.out.println(json);

		System.out.println();
		System.out.println("==================================");
		System.out.println();

		System.out.println("----Json => object----");
		BookDTO book1 = g.fromJson(json, BookDTO.class);
		// (JSON, 객체로 만들어질 클래스)
		System.out.println(book1);
		System.out.println("도서제목 :" + book1.getTitle() + ", 가격 : " + book1.getPrice());
		// 객체로 꺼내는 것도 가능하겠죠

		List<BookDTO> lst = new ArrayList<BookDTO>();
		lst.add(new BookDTO("JSP", 28000, "솔데스크", 720));
		lst.add(new BookDTO("JPA", 32000, "솔데스크", 680));
		lst.add(new BookDTO("Spring", 54000, "솔데스크", 900));

		System.out.println();
		System.out.println("==================================");
		System.out.println();

		System.out.println("----List(object) => Json----");
		String lst_Json = g.toJson(lst);
		System.out.println(lst_Json);

		System.out.println();
		System.out.println("==================================");
		System.out.println();

		System.out.println("----Json => List(object)----");
		List<BookDTO> lst1 = g.fromJson(lst_Json, new TypeToken<List<BookDTO>>() {
		}.getType());

		for (BookDTO vo : lst1) {
			System.out.println(vo);
		}
	}

}
