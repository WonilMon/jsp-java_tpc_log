package kr.project;

import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_C {

	public static void main(String[] args) {

		String src = "Info.json";

		// 외부파일을 바로 읽어올수 없기 때문에 InputStream을 통해 읽어올 수 있다
		InputStream is = Project01_C.class.getResourceAsStream(src);

		if (is == null) {
			throw new NullPointerException("파일이 없습니다.");
		}

		// JSONTokener : 문자열,스트림을 JSON 문자열로 바꿔줌 -> JSON 으로 바꾸고 객체로 변경해버리기
		JSONTokener tokener = new JSONTokener(is);
		JSONObject object = new JSONObject(tokener);
		System.out.println(object.toString(2));

	}

}
