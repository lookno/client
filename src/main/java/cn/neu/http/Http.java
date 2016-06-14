package cn.neu.http;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public class Http {
	public static int CODE;

	public static String getConnect(String url, String token) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Content-Type", "application/json");
			if (null != token && !token.trim().equals("")) {
				httpget.addHeader("HTTP_TOKEN", token);
			}
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					CODE = response.getStatusLine().getStatusCode();
					HttpEntity entity = response.getEntity();
					try {
						return entity != null ? EntityUtils.toString(entity) : null;
					} catch (ParseException ex) {
						throw new ClientProtocolException(ex);
					}
				}

			};

			String responseBody = httpclient.execute(httpget, responseHandler);
			return responseBody;
		}
	}

	public static String postConnect(String url, String token, String body) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPost httppost = new HttpPost(url);
			httppost.addHeader("Content-Type", "application/json");
			if (null != token && !token.trim().equals("")) {
				httppost.addHeader("HTTP_TOKEN", token);
			}
			StringEntity stringEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
			httppost.setEntity(stringEntity);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					CODE = response.getStatusLine().getStatusCode();
					HttpEntity entity = response.getEntity();
					try {
						return entity != null ? EntityUtils.toString(entity) : null;
					} catch (ParseException ex) {
						throw new ClientProtocolException(ex);
					}
				}

			};

			String responseBody = httpclient.execute(httppost, responseHandler);
			return responseBody;
		}
	}

	public static String putConnect(String url, String token, String body) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPut httpput = new HttpPut(url);
			httpput.addHeader("Content-Type", "application/json");
			if (null != token && !token.trim().equals("")) {
				httpput.addHeader("HTTP_TOKEN", token);
			}
			StringEntity stringEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
			httpput.setEntity(stringEntity);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					CODE = response.getStatusLine().getStatusCode();
					HttpEntity entity = response.getEntity();
					try {
						return entity != null ? EntityUtils.toString(entity) : null;
					} catch (ParseException ex) {
						throw new ClientProtocolException(ex);
					}
				}

			};

			String responseBody = httpclient.execute(httpput, responseHandler);
			return responseBody;
		}
	}

	public final static void main(String[] args) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpGet httpget = new HttpGet("http://localhost:8080/storage/user/logout");

			System.out.println("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						try {
							return entity != null ? EntityUtils.toString(entity) : null;
						} catch (ParseException ex) {
							throw new ClientProtocolException(ex);
						}
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			System.out.println("----------------------------------------");
			System.out.println(responseBody);
		}
	}
}
