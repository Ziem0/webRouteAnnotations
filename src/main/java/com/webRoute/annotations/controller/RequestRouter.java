package com.webRoute.annotations.controller;

import com.sun.net.httpserver.HttpExchange;
import com.webRoute.annotations.helpers.RequestHandler;
import com.webRoute.annotations.helpers.WebRoute;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestRouter extends HttpHandler {

	private String method;
	private URI uri;
	private String query;
	private boolean isQuery;

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		setExchange(httpExchange);

		method = getMethod();
		uri = exchange.getRequestURI();
		query = uri.getQuery();
		isQuery = query != null;

		boolean routeExist = false;

		try {
			routeExist = routeExist();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

		if (!routeExist) {
			redirect();
		} else {
			sendResponse();
		}
	}

	private boolean routeExist() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, IOException {
		Class clazz = RequestHandler.class;
		RequestHandler handler = new RequestHandler();
//		Constructor constructor = clazz.getConstructor();
//		Object instance = constructor.newInstance();

		Method[] methods = clazz.getMethods();

		for (Method m : methods) {
			if (m.isAnnotationPresent(WebRoute.class)) {
				String annPath = m.getAnnotation(WebRoute.class).path();
				String annMethod = m.getAnnotation(WebRoute.class).method().toString();
				boolean isMethodQuery = m.getAnnotation(WebRoute.class).query();

				if (isMethodFounded(annPath, annMethod)) {
					if (isQuery) {
						getNameLastName();
						String message = getMessage();
						handler.setMessage(message);
						response = handler.showResult();
						return true;
					} else if (method.equalsIgnoreCase("post")) {
						readInputs();
						String message = getMessage();
						handler.setMessage(message);
					}
					response = (String) m.invoke(handler);
					return true;
				}
			}
		}
		return false;
	}

	private boolean isMethodFounded(String annPath, String annMethod) {
		return annPath.equalsIgnoreCase(uri.getPath()) && annMethod.equalsIgnoreCase(method);
	}

	private void getNameLastName() {
		Map<String, String> result = new HashMap<>();
		String[] pairs = query.split("&");

		for (String pair : pairs) {
			String[] kv = pair.split("=");
			String value = URLDecoder.decode(kv[1]);
			result.put(kv[0], value);
		}
		name = result.get("name");
		lastName = result.get("lastName");
	}

	private String getMessage() {
		StringBuilder result = new StringBuilder();
		String names = name + lastName;
		int length = names.length();

		for (int i = length-1; i >= 0; i--) {
			result.append(names.charAt(i));
		}
		return result.toString();
	}

	private void redirect() throws IOException {
		exchange.getResponseHeaders().set("Location", "/");
		exchange.sendResponseHeaders(302, -1);
	}

}
