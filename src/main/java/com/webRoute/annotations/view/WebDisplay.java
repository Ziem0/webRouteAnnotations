package com.webRoute.annotations.view;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class WebDisplay {

	public static String getHome() {
		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/home.twig");
		JtwigModel model = JtwigModel.newModel();
		return template.render(model);
	}

	public static String getForm() {
		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/form.twig");
		JtwigModel model = JtwigModel.newModel();
		return template.render(model);
	}

	public static String getResult(String message) {
		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/result.twig");
		JtwigModel model = JtwigModel.newModel();
		model.with("message", message);
		return template.render(model);
	}

}
