package kr.project;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Project01_F {
	public static void main(String[] args) {

		String client_id = "kw8m082yxh";
		String client_secret = "MRZ4Mg3pyztgvC8lhR1npZrLfbqxkF9G2PHGhJfN";

		Scanner scan = new Scanner(System.in);
		System.out.print("주소를 입력하세요 : ");
		String address = scan.nextLine();
		scan.close();

		try {
			String encodedAddress = URLEncoder.encode(address, "utf-8");
			String reqURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + encodedAddress;

			CloseableHttpClient httpClient = HttpClients.createDefault();
			// CloseableHttpClient :HTTP 요청을 보내고 응답을 받기 위한 기본 객체

			HttpGet httpGet = new HttpGet(reqURL);
			// HttpGet : 특정 URL 로 GET 요청을 보낼 때 사용

			httpGet.setHeader("X-NCP-APIGW-API-KEY-ID", client_id);
			httpGet.setHeader("X-NCP-APIGW-API-KEY", client_secret);

			HttpResponse response = (HttpResponse) httpClient.execute(httpGet);
			// HttpResponse 서버로부터 응답, 요청을 처리하고 응답을 반환
			// 여기까지가 응답받는 것의 끝. 이제 json 으로 받으면 됨

			// EntityUtils.toString() : 괄호안 HttpResponse 값 전체(getEntuty)를 스트링으로 변경
			String responseBody = EntityUtils.toString(response.getEntity());
			// 서버로부터 받은 응답 데이터
			System.out.println("응답 문자열 : " + responseBody);

			// JsonObject
			JSONObject jsonObject = new JSONObject(responseBody);
			System.out.println("Json 객체 : " + jsonObject.toString(2));
			System.out.println("------------------------------------");

			// json 에서 addresses 객체 가져와서 addresses 배열변수로 들어감
			JSONArray addresses = jsonObject.getJSONArray("addresses");

			String x = "";
			String y = "";
			String roadAddress = "";

			for (int i = 0; i < addresses.length(); i++) {
				JSONObject temp = (JSONObject) addresses.getJSONObject(i);

				x = temp.getString("x");
				y = temp.getString("y");
				roadAddress = temp.getString("roadAddress");

			}
			mapService(x, y, roadAddress);

			httpClient.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mapService(String x, String y, String address) {

		String client_id = "kw8m082yxh";
		String client_secret = "MRZ4Mg3pyztgvC8lhR1npZrLfbqxkF9G2PHGhJfN";

		String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster";

		try {
			String marker = URLEncoder.encode("type:t|pos:" + x + " " + y, "utf-8");
			String url = reqUrl + "?w=700&h=500&markers" + marker;

			// 요청하는법은 위와 똑같
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("X-NCP-APIGW-API-KEY-ID", client_id);
			httpGet.setHeader("X-NCP-APIGW-API-KEY", client_secret);

			HttpResponse response = httpClient.execute(httpGet);

			// 이미지를 받을 거임 (바이트 처리된 이미지 파일)
			// EntityUtils.toByteArray() : 괄호안 HttpResponse 값 전체(getEntuty)를 ByteArray로 변경
			byte imageBytes[] = EntityUtils.toByteArray(response.getEntity());

			// 현재 시간을 밀리초 단위로 가져와 문자열로 변환 뒤 파일의 이름으로 사용
			String tempName = Long.valueOf(new Date().getTime()).toString();
			File file = new File(tempName + ".jpg");
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(imageBytes);
			} // 파일에 바이트 데이터 쓰기
			httpClient.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
