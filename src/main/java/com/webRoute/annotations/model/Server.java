package com.webRoute.annotations.model;

import com.sun.net.httpserver.HttpServer;
import com.webRoute.annotations.controller.RequestRouter;
import com.webRoute.annotations.controller.Static;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

	private final int port;
	private HttpServer server;

	public Server(int port) {
		this.port = port;
	}

	public void setup() {
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/", new RequestRouter());
			server.createContext("/static", new Static());
			server.setExecutor(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		server.start();
	}
}
