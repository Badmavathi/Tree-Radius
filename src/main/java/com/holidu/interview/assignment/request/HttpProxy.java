package com.holidu.interview.assignment.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public class HttpProxy {

	CloseableHttpClient httpClient;

	public HttpProxy(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getData(HttpGet getRequest) {
		
		String result = null;
		getRequest.addHeader("accept", "application/json");

		try {
			CloseableHttpResponse response = this.httpClient.execute(getRequest);

			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			try {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
					StringBuilder sb = new StringBuilder();

					String line = null;
					while ((line = reader.readLine()) != null)
						sb.append(line + "\n");

					result = sb.toString();
					instream.close();

				}
			} finally {
				response.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
	}
}
