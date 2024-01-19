package com.arsios.exchange.api.btcchina.client.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.arsios.exchange.api.btcchina.client.IBTCChinaAPIClient;
import com.arsios.exchange.api.btcchina.config.BTCChinaAPIConfig;
import com.arsios.exchange.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Repository
public class BTCChinaAPIClient implements IBTCChinaAPIClient {

	private static final Logger	LOGGER				= LoggerFactory.getLogger(BTCChinaAPIClient.class);
	private static final Gson	GSON				= new GsonBuilder().create();
	private static final String	HMAC_SHA1_ALGORITHM	= "HmacSHA1";

	private static Mac			mac;

	private String				protocol	= "https";

	@Value("${" + BTCChinaAPIConfig.APIURL + ":localhost}")
	private String				apiUrl;

	@Value("${" + BTCChinaAPIConfig.APIKEY + ":}")
	private String				apiKey;

	@Value("${" + BTCChinaAPIConfig.APISECRET + ":}")
	private String				apiSecret;
	
	@Value("${" + BTCChinaAPIConfig.APIVERSION + ":0}")
	private Integer				apiVersion;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException, InvalidKeyException {
		BTCChinaAPIClient.mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		BTCChinaAPIClient.mac.init(new SecretKeySpec(this.apiSecret.getBytes(), HMAC_SHA1_ALGORITHM));
		this.apiUrl = this.protocol + "://" + this.apiUrl + "/api_trade_v" + this.apiVersion + ".php";
	}
	
	public String execute(String method) throws IOException {
		return this.getResponse(this.apiUrl, method, new ArrayList<Object>(0));
	}
	
	public String execute(String method, List<Object> params) throws IOException {
		return this.getResponse(this.apiUrl, method, params);
	}

	private String getResponse(String url, String method, List<Object> params) throws IOException {
		
		String response = "";

		try {
			String tonce = Long.valueOf(TimeUnit.SECONDS.toMillis(System.currentTimeMillis())).toString();
			
			Map<String, String> getDataMap = new LinkedHashMap<String, String>();
			
			getDataMap.put("tonce", tonce);
			getDataMap.put("accesskey", this.apiKey);
			getDataMap.put("requestmethod", "post");
			getDataMap.put("id", "1");
			getDataMap.put("method", method);
			
			String getData = Utilities.buildQueryString(getDataMap) + "&params=" + params.toString().replaceAll("\\[|\\]|\\s+|\\bnull\\b|\\bfalse\\b", "").replaceAll("\\btrue\\b", "1");
			
			String hash = Hex.encodeHexString(mac.doFinal(getData.getBytes("UTF-8")));
	
			HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();
			String userpass = this.apiKey + ":" + hash;
			String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes());
			 
			con.setRequestMethod("POST");
			con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
			con.setRequestProperty("Authorization", basicAuth);
			 
			String postData = "{\"method\": \"" + method + "\", \"params\": " + GSON.toJson(params) + ", \"id\": 1}";
	
			con.setDoOutput(true);
			
			StringReader reader = new StringReader(postData);
			IOUtils.copy(reader, con.getOutputStream(), "UTF-8");
			reader.close();
			
			if (con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
				StringWriter writer = new StringWriter();
				IOUtils.copy(con.getInputStream(), writer, "UTF-8");
				response = writer.toString();
				writer.close();
			} else {
				throw new IOException("HTTP error code: " + con.getResponseCode());
			}
			
		} catch (ProtocolException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		} catch (MalformedURLException e) {
			LOGGER.error(ExceptionUtils.getMessage(e));
		}
		
		return response;
	}

}
