package com.santi.web.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("hello")
public class HelloWorldController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String hello(Locale locale, Model model) {
		System.out.println("Hello Spring MVC");
		return "sample";
	}
}
