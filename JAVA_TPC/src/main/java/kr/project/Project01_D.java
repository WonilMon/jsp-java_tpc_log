package kr.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.apache.tomcat.jni.Buffer;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_D {

	public static void main(String[] args) {

		String client_id = "kw8m082yxh";
		String client_secret = "MRZ4Mg3pyztgvC8lhR1npZrLfbqxkF9G2PHGhJfN";

		// 사용자로부터 입력을 받기 위한 InputStreamReader 생성(BufferedReader 로 묶기)

		BufferedReader io = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.print("주소를 입력하세요 : ");
			String address = io.readLine();
			String addr = URLEncoder.encode(address, "UTF-8"); // 한글 보정
			String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;

			URL url = new URL(reqUrl);

			HttpURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);

			int responseCode = con.getResponseCode();
			BufferedReader br = null;

			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			} else if (responseCode == 400) {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = br.readLine()) != null) {
				response.append(line); // 한줄한줄 읽어와서 스트림버퍼에 담기
			}
			br.close();

			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString(2));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
