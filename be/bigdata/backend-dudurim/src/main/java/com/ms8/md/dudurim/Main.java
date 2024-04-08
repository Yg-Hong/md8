package com.ms8.md.dudurim;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import static org.apache.spark.sql.functions.col;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;

public class Main {
	private static final Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	public static void main(String[] args) throws IOException, InterruptedException {
		SparkSession spark = SparkSession
			.builder()
			.appName("PostgreSQL to API Example")
			.config("spark.master", "yarn")
			.getOrCreate();

		String url = "jdbc:postgresql://j10a208.p.ssafy.io:5432/ms8";
		String table = "md.dudurim";
		String user = "ms8";
		String password = "ms8!@";

		Dataset<Row> jdbcDF = spark.read()
			.format("jdbc")
			.option("url", url)
			.option("dbtable", table)
			.option("user", user)
			.option("password", password)
			.load();

		Dataset<Row> selectedDF = jdbcDF.select(col("ptracking_id"), col("title"), col("lat"), col("lon")).repartition(2);

		selectedDF.foreachPartition(iterator -> {
			Connection conn = DriverManager.getConnection(url, user, password);
			String updateSQL = "UPDATE md.dudurim SET keywords = ?, congestion = ? WHERE ptracking_id = ?";
			PreparedStatement statement = conn.prepareStatement(updateSQL);
			while (iterator.hasNext()) {
				Row row = iterator.next();
				try {
					String response = sendRequest("news", row.getAs("title"));
					Thread.sleep(1000); //1초 대기
					List<String> analyze = analyze(response);
					response = sendRequest("blog", row.getAs("title"));
					Thread.sleep(1000); //1초 대기
					analyze.addAll(analyze(response));
					List<String> wordCount = wordCount(analyze).entrySet()
						.stream()
						.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
						.limit(15)
						.map(Map.Entry::getKey)
						.collect(Collectors.toList());

					String wcResult = "[";
					for (int i = 0; i < wordCount.size(); i++) {
						wcResult += ("[" + String.valueOf(i) + ", " + wordCount.get(i) + "]");
						if(i < wordCount.size()-1){
							wcResult += ", ";
						}
					}
					wcResult += "]";

					// String wcResult = wordCount.stream()
					// 	.collect(Collectors.joining(", ", "[", "]"));
					Integer congestion = getCongestion(row.getAs("lat"), row.getAs("lon"));

					statement.setString(1, wcResult);
					statement.setInt(2, congestion);
					statement.setLong(3, row.getAs("ptracking_id"));
					statement.executeUpdate();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		spark.stop();
	}

	private static String sendRequest(String type, String query) throws Exception {
		String encodedQuery;
		try {
			encodedQuery = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new Exception("Query encoding failed", e);
		}
		String requestURL = String.format("https://openapi.naver.com/v1/search/%s.xml?query=%s&display=10&start=1&sort=sim", type, encodedQuery);
		HttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(requestURL);

		request.addHeader("X-Naver-Client-Id", "lBUSmwVFTFsEWfwQ2fTh");
		request.addHeader("X-Naver-Client-Secret", "v3WfTBHgAG");

		HttpResponse response = client.execute(request);

		HttpEntity entity = response.getEntity();
		return entity != null ? EntityUtils.toString(entity) : null;
	}

	private static List<String> analyze(String query) throws Exception {
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(query));
		Document document = builder.parse(is);
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("item");
		List<String> result = new ArrayList<>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				String title = eElement.getElementsByTagName("title").item(0).getTextContent().replaceAll("[^a-zA-Z0-9\\s가-힣]", "");
				String description = eElement.getElementsByTagName("description").item(0).getTextContent().replaceAll("[^a-zA-Z0-9\\s가-힣]", "");

				String[] split = description.split("\\s");
				result.addAll(komoran.analyze(Arrays.toString(split)).getNouns());
				result.addAll(komoran.analyze(title).getNouns());
			}
		}
		return result;
	}

	private static Map<String, Integer> wordCount(List<String> words){
		Map<String, Integer> wordCount = new HashMap<>();
		for (String word : words) {
			wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
		}
		return wordCount;
	}

	private static Integer getCongestion(Double lat, Double lon){
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			URIBuilder builder = new URIBuilder("https://apis.openapi.sk.com/tmap/geo/reverseLabel");
			builder.setParameter("version", "1")
				.setParameter("format", "json")
				.setParameter("callback", "result")
				.setParameter("reqLevel", "15")
				.setParameter("centerLon", Double.toString(lon))
				.setParameter("centerLat", Double.toString(lat))
				.setParameter("reqCoordType", "WGS84GEO")
				.setParameter("resCoordType", "WGS84GEO");

			HttpGet request = new HttpGet(builder.build());
			request.addHeader("User-Agent", "Mozilla/5.0");
			request.addHeader("appKey", "EjBls4Hwhv2o5dSgDcfyH7R5HkNLgmqD29se5rlW");

			HttpResponse response = httpClient.execute(request);

			String responseString = EntityUtils.toString(response.getEntity());

			JSONObject jsonResponse = new JSONObject(responseString);
			String id = jsonResponse.getJSONObject("poiInfo").getString("id");
			Double poiLat = jsonResponse.getJSONObject("poiInfo").getDouble("poiLat");
			Double poiLon = jsonResponse.getJSONObject("poiInfo").getDouble("poiLon");


			builder = new URIBuilder("https://apis.openapi.sk.com/tmap/puzzle/pois/" + id);
			builder.setParameter("appKey", "EjBls4Hwhv2o5dSgDcfyH7R5HkNLgmqD29se5rlW")
				.setParameter("lat", Double.toString(poiLat))
				.setParameter("lng", Double.toString(poiLon));
			request = new HttpGet(builder.build());

			response = httpClient.execute(request);

			responseString = EntityUtils.toString(response.getEntity());
			System.out.println("responseString = " + responseString);
			jsonResponse = new JSONObject(responseString);
			JSONArray jsonArray = jsonResponse.getJSONObject("contents").getJSONArray("rltm");

			JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());
			return jsonObject.getInt("congestionLevel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;
	}
}
