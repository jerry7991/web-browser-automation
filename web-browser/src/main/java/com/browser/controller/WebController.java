package com.browser.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.browser.service.WebService;

@RestController
@CrossOrigin
public class WebController {

	@Autowired
	private WebService webService;

	@GetMapping("/start")
	public void startBrowser(HttpServletRequest request) throws IOException {
		String browser = request.getParameter("browser");
		String url = request.getParameter("url");
		webService.openBrowser(browser, url);
	}

	@GetMapping("/geturl")
	public String getCurrentActiveUrl(HttpServletRequest request) {
		return webService.getCurrentActiveUrl(request.getParameter("browserName"));
	}

	@GetMapping("/stop")
	public boolean stopBrowser(HttpServletRequest request) throws IOException {
		return webService.stopBrowser(request.getParameter("browserName"));
	}

	@GetMapping("/cleanup")
	public void cleanBrowser(HttpServletRequest request) throws IOException {
		webService.cleanBrowser(request.getParameter("browserName"));
	}
}