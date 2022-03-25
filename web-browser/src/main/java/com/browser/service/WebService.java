package com.browser.service;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.browser.repository.WebRepository;
import com.browser.utils.Constants;

@Service
public class WebService {
	@Autowired
	private WebRepository webRepository;
	private Runtime rt;

	public WebService() {
		rt = Runtime.getRuntime();
	}

	public void openBrowser(String browserName, String url) throws IOException {
		rt.exec(webRepository.getBrowserPath(browserName) + url);
		webRepository.registerUrl(browserName, url);
	}

	public String getCurrentActiveUrl(String browserName) {
		return webRepository.getCurrentActiveUrl(browserName);
	}

	public boolean stopBrowser(String browserName) throws IOException {
		boolean isClosed = false;
		if (isClosed = webRepository.openedBrowser(browserName)) {
			if (browserName.equals("chrome"))
				rt.exec(Constants.KILL_GOOGLE_CMD);
			else
				rt.exec(Constants.KILL_EDGE_CMD);
		}
		return isClosed;
	}

	public void cleanBrowser(String browserName) throws IOException {

		Path dir = Paths
				.get(browserName.equals("chrome") ? Constants.GOOGLE_CACHE_LOCATION : Constants.EDGE_CACHE_LOCATION);
		try {
			Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

					System.out.println("Deleting file: " + file);
					Files.delete(file);
					return CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

					System.out.println("Deleting dir: " + dir);
					if (exc == null) {
						Files.delete(dir);
						return CONTINUE;
					} else {
						throw exc;
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
