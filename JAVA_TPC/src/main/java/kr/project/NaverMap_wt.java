package kr.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.swing.ImageIcon;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import kr.soldesk.AddressVO;

public class NaverMap_wt implements ActionListener {

	MapFrame naverMap;

	public NaverMap_wt(MapFrame naverMap) {
		this.naverMap = naverMap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if ("클릭".equals(command)) {
			searchAddress();
		} else if ("저장".equals(command)) {
			// saveAddress();
		}
	}

	// 주소검색 메서드
	public void searchAddress() {

		String client_id = "kw8m082yxh";
		String client_secret = "MRZ4Mg3pyztgvC8lhR1npZrLfbqxkF9G2PHGhJfN";

		try {
			String address = naverMap.address.getText(); // 검색한 주소값 (String)
			String addr = URLEncoder.encode(address, "utf-8"); //
			String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;
			// API 요청 파라미터 중 query 는 필수였음

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(reqUrl);
			httpGet.setHeader("X-NCP-APIGW-API-KEY-ID", client_id);
			httpGet.setHeader("X-NCP-APIGW-API-KEY", client_secret);

			// 요청이제대로 들어가면response 객체에 요청값을 받아서
			HttpResponse response = httpClient.execute(httpGet);
			// 문자열
			String responseBody = EntityUtils.toString(response.getEntity(), "utf-8");

			System.out.println(responseBody);

			JSONObject object = new JSONObject(responseBody);

			// 배열 안에 있으니까 꺼내기 (key 가 addresses 로 되어있죠)
			// 값을 보낼 때 애매하게 보내면 안됨 (ex. 중구 -> 서울시중구, 대구시중구,,,,)
			JSONArray arr = object.getJSONArray("addresses");

			AddressVO vo = new AddressVO();

			// JSONObject로 꺼낸다음 그 중 가장 위의 값만 가져오겠다.
			if (arr.length() > 0) {
				JSONObject temp = (JSONObject) arr.get(0);

				String roadAddress = (String) temp.getString("roadAddress");
				String jibunAddress = (String) temp.getString("jibunAddress");
				String x = (String) temp.getString("x");
				String y = (String) temp.getString("y");

				// 일일히 변수를 만들어서 가져오는거보다 DB에 담는다고 가정하면 더 간단하겠죠
				vo.setRoadAddress(roadAddress);
				vo.setJibunAddress(jibunAddress);
				vo.setX(x);
				vo.setY(y);
			}
			httpClient.close();

			// API 요청해서 지도 이미지 받아오기
			map_service(vo);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void map_service(AddressVO vo) {

		String client_id = "kw8m082yxh";
		String client_secret = "MRZ4Mg3pyztgvC8lhR1npZrLfbqxkF9G2PHGhJfN";
		String url_staticMap = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";

		try {
			String markers = URLEncoder
					.encode("type:t|pos:" + vo.getX() + " " + vo.getY() + "|label:" + vo.getRoadAddress(), "utf-8");
			url_staticMap += "w=700&h=500" + markers;

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url_staticMap);

			httpGet.setHeader("X-NCP-APIGW-API-KEY-ID", client_id);
			httpGet.setHeader("X-NCP-APIGW-API-KEY", client_secret);

			HttpResponse response = httpClient.execute(httpGet);

			// 응답을 받으면 byte 형식으로 오겠죠
			byte imgByte[] = EntityUtils.toByteArray(response.getEntity());
			String tempName = Long.valueOf(new Date().getTime()).toString();
			File file = new File(tempName + ".jsp");

			// 아직은 바이트 처리된 이미지를 진짜 이미지로 만들어주기
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(imgByte);

			} catch (Exception e) {
				e.printStackTrace();
			}

			ImageIcon img = new ImageIcon(file.getName());
			naverMap.imageLabel.setIcon(img);
			naverMap.resAddress.setText(vo.getRoadAddress());
			naverMap.jibunAddress.setText(vo.getJibunAddress());
			naverMap.resX.setText(vo.getX());
			naverMap.resY.setText(vo.getY());

			httpClient.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
