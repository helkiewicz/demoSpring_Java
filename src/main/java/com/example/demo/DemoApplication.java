package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

@RestController
class ApiController {

	@GetMapping("/api/processFile")
	public String processFile() {
		try {
			Path filePath = Paths.get("C:/Users/emixa/IdeaProjects/demo/plik.txt");

			String content = Files.lines(filePath)
					.limit(10)
					.collect(Collectors.joining("\n"));

			String[] words = content.split("\\s+");
			Arrays.sort(words);
			
			String firstWord = words[0];
			String encryptedFirstWord = encryptCesarCipher(firstWord, 13);

			return encryptedFirstWord;
		} catch (IOException e) {
			e.printStackTrace();
			return "Błąd podczas przetwarzania pliku: " + e.getMessage();
		}

	}

	private String encryptCesarCipher(String input, int key) {
		StringBuilder result = new StringBuilder();

		for (char ch : input.toCharArray()) {
			if (Character.isLetter(ch)) {
				char base = Character.isUpperCase(ch) ? 'A' : 'a';
				result.append((char) ((ch - base + key) % 26 + base));
			} else {
				result.append(ch);
			}
		}

		return result.toString();
	}
}
