package com.ms8.md.tracking.global.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource("classpath:application-security.yml")
public class GetAddress {

	// @Value("${my.key}")
	private static String KEY = "2a246e072778e8510b53e909dc12270c";

	/**
	 * REST API로 통신하여 받은 JSON형태의 데이터를 String으로 받아오는 메소드
	 */
	public static String getJSONData(String apiUrl) throws Exception {
		HttpURLConnection conn;
		StringBuilder response = new StringBuilder();

		// 인증키
		String auth = "KakaoAK " + KEY;

		// URL 설정
		URL url = new URL(apiUrl);

		conn = (HttpURLConnection)url.openConnection();

		// Request 형식 설정
		conn.setRequestMethod("GET");
		conn.setRequestProperty("X-Requested-With", "curl");
		conn.setRequestProperty("Authorization", auth);

		conn.setDoOutput(true);

		int responseCode = conn.getResponseCode();
		if (responseCode == 400) {
			log.error("400:: 해당 명령을 실행할 수 없음");
		} else if (responseCode == 401) {
			log.error("401:: Authorization가 잘못됨");
		} else if (responseCode == 500) {
			log.error("500:: 서버 에러, 문의 필요");
		} else {
			Charset charset = StandardCharsets.UTF_8;
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
		}
		return response.toString();
	}

	/**
	 * JSON형태의 String 데이터에서 주소값(address_name)만 받아오기
	 */
	public static String getRegionAddress(String jsonString) {
		String value = "";
		JSONObject jObj = new JSONObject(jsonString);
		JSONObject meta = jObj.getJSONObject("meta");

		if ((Integer) meta.get("total_count") > 0) {
			JSONArray jArray = jObj.getJSONArray("documents");
			JSONObject subJobj = jArray.getJSONObject(0);

			value += subJobj.getString("region_2depth_name") + ",";
			value += subJobj.getString("region_3depth_name");
		}
		return value;
	}
}
