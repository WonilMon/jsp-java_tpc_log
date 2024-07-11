package kr.project;

import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Project02_B {

	public static void main(String[] args) {

		String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";
		Scanner scan = new Scanner(System.in);

		try {
			System.out.println("[입력 : 년(yyyy)-월(mm)-일(dd)] : ");
			String bible = scan.nextLine();
			url += "&Base_de=" + bible;
			System.out.println(url);
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");

			Document doc = Jsoup.connect(url).get();
			System.out.println(doc);
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");

			Element bibleInfo_box = doc.select(".bible_text").first();
			System.out.println(bibleInfo_box);
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");

			Element bibleInfo_text = doc.select(".bibleinfo_text").first();
			System.out.println(bibleInfo_text);
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");
			System.out.println("------------------------");

			Elements li = doc.select(".body_list > li");

			for (Element liList : li) {
				System.out.println(liList.select(".info").first().text());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
