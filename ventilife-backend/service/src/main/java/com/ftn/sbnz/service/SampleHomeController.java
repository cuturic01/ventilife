package com.ftn.sbnz.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleHomeController {
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
