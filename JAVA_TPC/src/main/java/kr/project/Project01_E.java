package kr.project;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class Project01_E {

	public static void main(String[] args) {

		String client_id = "kw8m082yxh";
		String client_secret = "MRZ4Mg3pyztgvC8lhR1npZrLfbqxkF9G2PHGhJfN";

		Scanner scan = new Scanner(System.in);
		System.out.print("주소를 입력하세요 : ");
		String address = scan.nextLine();
		scan.close();

		try {
			String encodedAddress = URLEncoder.encode(address, "utf-8");
			String reqURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="
					+ encodedAddress;

			CloseableHttpClient httpClient = HttpClients.createDefault();
			// CloseableHttpClient :HTTP 요청을 보내고 응답을 받기 위한 기본 객체

			HttpGet httpGet = new HttpGet(reqURL);
			// HttpGet : 특정 URL 로 GET 요청을 보낼 때 사용

			httpGet.setHeader("X-NCP-APIGW-API-KEY-ID", client_id);
			httpGet.setHeader("X-NCP-APIGW-API-KEY", client_secret);

			HttpResponse response = (HttpResponse) httpClient.execute(httpGet);
			// HttpResponse 서버로부터 응답, 요청을 처리하고 응답을 반환
			// 여기까지가 응답받는 것의 끝. 이제 json으로 받으면 되

			String responseBody = EntityUtils.toString(response.getEntity());
			// 서버로부터 받은 응답 데이터
			System.out.println("응답 문자열 : " + responseBody);

			// JsonObject
			JSONObject jsonObject = new JSONObject(responseBody);
			System.out.println("Json 객체 : " + jsonObject.toString(2));
			System.out.println("------------------------------------");

			// json 에서 addres 객체 가져와서 addresses 배열변수로 들어감
			JSONArray addresses = jsonObject.getJSONArray("addresses");

			for (int i = 0; i < addresses.length(); i++) {
				JSONObject temp = (JSONObject) addresses.getJSONObject(i);
				System.out.println("도로명 주소 : " + temp.get("roadAddress"));
				System.out.println("지번 주소 : " + temp.get("jibunAddress"));
				System.out.println("경도 : " + temp.get("x"));
				System.out.println("위도 : " + temp.get("y"));
			}

			httpClient.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
