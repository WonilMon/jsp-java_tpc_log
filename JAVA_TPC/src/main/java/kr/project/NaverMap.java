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

public class NaverMap implements ActionListener {

	MapFrame naverMap;

	public NaverMap(MapFrame naverMap) {
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

		map_service();

	}

	private void map_service() {

		String client_id = "kw8m082yxh";
		String client_secret = "MRZ4Mg3pyztgvC8lhR1npZrLfbqxkF9G2PHGhJfN";

		String address = naverMap.address.getText();

		try {
			String encodedAddress = URLEncoder.encode(address, "utf-8");
			String reqURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + encodedAddress;

			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpGet httpGet = new HttpGet(reqURL);

			httpGet.setHeader("X-NCP-APIGW-API-KEY-ID", client_id);
			httpGet.setHeader("X-NCP-APIGW-API-KEY", client_secret);

			HttpResponse response = (HttpResponse) httpClient.execute(httpGet);

			String responseBody = EntityUtils.toString(response.getEntity());

			JSONObject jsonObject = new JSONObject(responseBody);

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

			String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster";
			String marker = URLEncoder.encode("type:t|pos:" + x + " " + y, "utf-8");
			String url = reqUrl + "?w=700&h=500&markers=" + marker;

			CloseableHttpClient httpClient1 = HttpClients.createDefault();
			HttpGet httpGet1 = new HttpGet(url);
			httpGet1.setHeader("X-NCP-APIGW-API-KEY-ID", client_id);
			httpGet1.setHeader("X-NCP-APIGW-API-KEY", client_secret);

			HttpResponse response1 = httpClient.execute(httpGet1);

			byte imageBytes[] = EntityUtils.toByteArray(response1.getEntity());

			String tempName = Long.valueOf(new Date().getTime()).toString();
			File file = new File(tempName + ".jpg");
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(imageBytes);
			}
			String n = file.getName();

			ImageIcon img = new ImageIcon(n);
			System.out.println("경로 :"+file.getName());
			System.out.println("경로 :"+file.getName());

			for (int i = 0; i < addresses.length(); i++) {
				JSONObject temp1 = (JSONObject) addresses.getJSONObject(i);
				naverMap.imageLabel.setIcon(img);
				naverMap.resAddress.setText(temp1.getString("roadAddress"));
				naverMap.jibunAddress.setText(temp1.getString("jibunAddress"));
				naverMap.resX.setText(temp1.getString("x"));
				naverMap.resY.setText(temp1.getString("y"));
			}

			httpClient.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
