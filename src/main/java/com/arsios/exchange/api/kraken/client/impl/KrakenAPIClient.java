package com.arsios.exchange.api.kraken.client.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.arsios.exchange.api.kraken.client.IKrakenAPIClient;
import com.arsios.exchange.api.kraken.config.KrakenAPIConfig;
import com.arsios.exchange.utils.Utilities;
import com.google.common.primitives.Bytes;

@Repository
public class KrakenAPIClient implements IKrakenAPIClient {

	private static final Logger	LOGGER		= LoggerFactory.getLogger(KrakenAPIClient.class);
	private static final int	NONCE_BYTES	= 16;

	private static Mac			mac;

	private String				port		= "-1";
	private String				protocol	= "https";

	@Value("${" + KrakenAPIConfig.APIURL + ":localhost}")
	private String				apiUrl;

	@Value("${" + KrakenAPIConfig.APIKEY + ":}")
	private String				apiKey;

	@Value("${" + KrakenAPIConfig.APISECRET + ":}")
	private String				apiSecret;

	@Value("${" + KrakenAPIConfig.APIVERSION + ":0}")
	private Integer				apiVersion;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException, InvalidKeyException {
		KrakenAPIClient.mac = Mac.getInstance("HmacSHA512");
		KrakenAPIClient.mac.init(new SecretKeySpec(Base64.decodeBase64(this.apiSecret), "HmacSHA512"));
	}

	public String getPublic(String method) throws IOException {
		return this.getPublic(method, null);
	}

	public String getPublic(String method, Map<String, String> params) throws IOException {
		return this.getResponse(this.apiUrl, "/" + this.apiVersion + "/public/" + method, params, false);
	}

	public String getPrivate(String method) throws IOException {
		return this.getPrivate(method, new HashMap<String, String>());
	}

	public String getPrivate(String method, Map<String, String> params) throws IOException {
		return this.getResponse(this.apiUrl, "/" + this.apiVersion + "/private/" + method, params, true);
	}

	private String getResponse(String url, String request, Map<String, String> params, boolean isPrivate) throws IOException {

		String response = null;

		HttpHost targetHost = new HttpHost(url, Integer.valueOf(port), protocol);

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpPost httpPost = new HttpPost(request);

			if (isPrivate) {
				httpPost.setHeader("API-Key", this.apiKey);
				httpPost.setHeader("API-Sign", signData(request, params));
			}
			if (params != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (String key : params.keySet()) {
					nvps.add(new BasicNameValuePair(key, params.get(key)));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			}

			CloseableHttpResponse httpResponse = httpclient.execute(targetHost, httpPost, new BasicHttpContext());

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				try {
					HttpEntity entity = httpResponse.getEntity();

					StringWriter writer = new StringWriter();
					IOUtils.copy(entity.getContent(), writer, "UTF-8");
					response = writer.toString();
					EntityUtils.consume(entity);
					writer.close();
				} finally {
					httpResponse.close();
				}
			} else {
				throw new IOException("Response Status :" + httpResponse.getStatusLine());
			}

			httpclient.close();

		} catch (UnsupportedEncodingException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		} catch (ClientProtocolException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}

		return response;
	}

	private String signData(String request, Map<String, String> params) {

		String nonce = StringUtils.rightPad(String.valueOf(System.nanoTime()), NONCE_BYTES, "0").substring(0, NONCE_BYTES);
		params.put("nonce", nonce);

		try {
			
			String postData = Utilities.buildQueryString(params);

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

			byte[] data = Bytes.concat(request.getBytes(), messageDigest.digest((nonce + postData).getBytes("UTF-8")));

			return Base64.encodeBase64String(mac.doFinal(data));

		} catch (UnsupportedEncodingException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		
		return null;

	}

}
