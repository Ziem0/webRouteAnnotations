package com.webRoute.annotations;

import com.webRoute.annotations.model.Server;

public class App {

	public static void main(String[] args) {
		Server server = new Server(8000);
		server.setup();
		server.run();
	}
}
