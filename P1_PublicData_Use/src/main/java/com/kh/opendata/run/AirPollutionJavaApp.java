package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AirPollutionJavaApp {

	public static final String serviceKey = "Ym%2BmoBAWcRuKVboT2B%2FI%2Fgxw79m%2FSodALP2wKOokarkTEOKXE8TsxIBx3LSWxmCOQNcjDVn1Okb88PS6k4p3rA%3D%3D";
	
	public static void main(String[] args) throws IOException {
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty";
		url += "?serviceKey=" + "서비스키";	//한글로 서비스키가 아니라 발급받은 서비스키를 입력해야함?
		url += "&sidoName=" + URLEncoder.encode("서울", "UTF-8");
		url += "&returnType=json";
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String line = null;
		
		while((line = br.readLine()) != null) {
			System.out.println(line);
		}
		
		br.close();
		urlConnection.disconnect();
	}

}