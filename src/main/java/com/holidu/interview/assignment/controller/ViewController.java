package com.holidu.interview.assignment.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class ViewController {

	@GetMapping("/")
	public String getHomePage() {
		return "index";
	}
}

