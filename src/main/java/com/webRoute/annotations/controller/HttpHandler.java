package com.webRoute.annotations.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpHandler implements com.sun.net.httpserver.HttpHandler {

	HttpExchange exchange;
	String response;
	String name;
	String lastName;


	public void setExchange(HttpExchange exchange) {
		this.exchange = exchange;
	}

	public String getMethod() {
		return exchange.getRequestMethod();
	}

	public void sendResponse() throws IOException {
		exchange.sendResponseHeaders(200, response.length());
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	public void readInputs() throws IOException {
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
		BufferedReader br = new BufferedReader(isr);

		String values = br.readLine();
		Map<String, String> parsedInputs = getParsedInputs(values);

		name = parsedInputs.get("name");
		lastName = parsedInputs.get("lastName");
	}

	private Map<String, String> getParsedInputs(String values) {
		Map<String, String> parsedInputs = new HashMap<>();

		String[] pairs = values.split("&");

		for (String pair : pairs) {
			String[] kv = pair.split("=");
			String value = URLDecoder.decode(kv[1]);
			parsedInputs.put(kv[0], value);
		}

		return parsedInputs;
	}

}
