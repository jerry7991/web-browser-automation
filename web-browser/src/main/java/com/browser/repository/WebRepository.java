package com.browser.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class WebRepository {
	private Map<String, Stack<String>> browserTable;
	private Map<String, String> browserInstance;

	public WebRepository() {
		browserTable = new HashMap<>();
		browserTable.put("chrome", new Stack<>());
		browserTable.put("edge", new Stack<>());

		browserInstance = new HashMap<>();
		browserInstance.put("chrome", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe ");
		browserInstance.put("edge", "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe ");
	}

	public String getBrowserPath(String browserName) {
		return browserInstance.get(browserName);
	}

	public String getCurrentActiveUrl(String browserName) {
		return browserTable.get(browserName).peek();
	}

	public void registerUrl(String browserName, String url) {
		browserTable.get(browserName).push(url);
	}

	public boolean openedBrowser(String browserName) {
		return browserTable.containsKey(browserName);
	}

	public void clearHistory(String browserName) {
		browserTable.get(browserName).clear();
	}

	public List<String> getAllUrl(String browserName) {
		return browserTable.get(browserName).stream().collect(Collectors.toList());
	}
}