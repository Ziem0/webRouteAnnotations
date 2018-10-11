package com.webRoute.annotations.helpers;

import com.webRoute.annotations.view.WebDisplay;

public class RequestHandler {

	private String message;

	@WebRoute()
	public String showMainPage() {
		return WebDisplay.getHome();
	}

	@WebRoute(path = "/form")
	public String showForm() {
		return WebDisplay.getForm();
	}

	@WebRoute(path = "/form", query = true)
	public String showGetResult() {
		return WebDisplay.getResult(message);
	}

	@WebRoute(path = "/form", method = WebRoute.METHOD.POST)
	public String showResult() {
		return WebDisplay.getResult(message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
