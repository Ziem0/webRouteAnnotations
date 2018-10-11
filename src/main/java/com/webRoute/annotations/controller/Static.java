package com.webRoute.annotations.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.webRoute.annotations.helpers.MimeTypeResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public class Static implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		URI uri = httpExchange.getRequestURI();
		String path = "." + uri.getPath();

		ClassLoader classLoader = getClass().getClassLoader();
		URL url = classLoader.getResource(path);

		if (url == null) {
			send404(httpExchange);
		} else {
			sendFile(httpExchange, url);
		}
	}

	private void send404(HttpExchange exchange) throws IOException {
		String response = "Page not found (404)";
		exchange.sendResponseHeaders(404, response.length());
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	private void sendFile(HttpExchange exchange, URL url) throws IOException {
		File file = new File(url.getFile());

		MimeTypeResolver resolver = new MimeTypeResolver(file);
		String mime = resolver.getMimeType();

		exchange.getResponseHeaders().set("Content-Type", mime);
		exchange.sendResponseHeaders(200, 0);

		OutputStream os = exchange.getResponseBody();

		FileInputStream fs = new FileInputStream(file);
		final byte[] buffer = new byte[0x10000];
		int count;

		while ((count = fs.read(buffer)) >= 0) {
			os.write(buffer,0,count);
		}
		os.close();
	}
}
