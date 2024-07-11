package kr.project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Project02_A {

	// Jsoup 으로 다른사람이 만든 요소를 끌고올수 있습니다. (정적인 요소만 가져와서 이건 잘 쓰진 않습니다)
	public static void main(String[] args) {

		String url = "https://sports.news.naver.com/wfootball/index.nhn";

		Document doc = null;

		try {
			// 해당 링크가 연결되고 객체들이 doc으로 들어옴
			doc = Jsoup.connect(url).get();
			System.out.println(doc);
			System.out.println("------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// div를 끌고올건데 '요소들'을 끌고올거임
		Elements elements = doc.select("div.home_news");
		System.out.println(elements);
		System.out.println("------------------");

		// 또 그 자식 중에서 h2 요소를 담기
		String title = elements.select("h2").text();
		System.out.println(title);
		System.out.println("------------------");

		// list를 배열처럼 가져오기 (Element 는 하나, Elements 는 여러개)
		elements.select("li");
		for (Element el : elements.select(title)) {
			System.out.println(el.text());
		}
	}

}
