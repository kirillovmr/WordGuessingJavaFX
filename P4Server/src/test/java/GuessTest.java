import static org.junit.jupiter.api.Assertions.*;

import communication.*;
import javafx.util.Pair;
import logic.GameLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

class GuessTest {

	@Test
	@DisplayName("Generating a word per category")
	void test1() {
		ArrayList<Pair<String, String>> pairs = GameLogic.generateWords();
		assertEquals(3, pairs.size(), "generateWords returns wrong number of pairs");

		for(Pair<String, String> pair : pairs) {
			assertTrue(pair.getKey().length() > 0, "Category is undefined");
			assertTrue(pair.getValue().length() > 0, "Word is undefined");
		}
	}

	@Test
	@DisplayName("Generating unique categories")
	void test2() {
		for (int i=0; i<10; i++) {
			ArrayList<Pair<String, String>> pairs = GameLogic.generateWords();
			ArrayList<String> categories = new ArrayList<>();

			for (Pair<String, String> pair : pairs) {
				categories.add(pair.getKey());
			}

			HashSet<String> unique_categories = new HashSet<>(categories);
			assertEquals(3, unique_categories.size(), "generateWords returns repeating categories");
		}
	}

	@Test
	@DisplayName("getWordFromCategory returns a proper word 1")
	void test3() {
		ArrayList<String> wordsFromCategory = new ArrayList<>(Arrays.asList("Zurich", "Chicago", "Dublin", "Munich", "London", "Mumbai", "Madrid", "Houston", "Budapest", "Brussels", "Athens", "Warsaw", "Hamburg", "Helsinki", "Seville", "Bilbao"));

		for(int i=0; i<100; i++) {
			String word = GameLogic.getWordFromCategory("Cities", new ArrayList<>());
			assertTrue(wordsFromCategory.indexOf(word) >= 0, "Returned word isn't in the category");
		}
	}

	@Test
	@DisplayName("getWordFromCategory returns a proper word 2")
	void test4() {
		ArrayList<String> wordsFromCategory = new ArrayList<>(Arrays.asList("Kitchen", "Knife", "Fridge", "Curtain", "Window", "Garage", "Bicycle", "Armchair", "Mattress", "Bathroom", "Mirror", "Shower", "Blanket", "Speaker", "Laptop", "Candle", "Laundry"));

		for(int i=0; i<100; i++) {
			String word = GameLogic.getWordFromCategory("Home", new ArrayList<>());
			assertTrue(wordsFromCategory.indexOf(word) >= 0, "Returned word isn't in the category");
		}
	}


	@Test
	@DisplayName("getWordFromCategory works properly with the blacklist 1")
	void test5() {
		ArrayList<String> wordsFromCategory = new ArrayList<>(Arrays.asList("Zurich", "Chicago", "Dublin", "Munich", "London", "Mumbai", "Madrid", "Houston", "Budapest", "Brussels", "Athens", "Warsaw", "Hamburg", "Helsinki", "Seville", "Bilbao"));
		wordsFromCategory.remove("Zurich");
		wordsFromCategory.remove("Chicago");
		wordsFromCategory.remove("Dublin");

		for(int i=0; i<100; i++) {
			String word = GameLogic.getWordFromCategory("Cities", new ArrayList<>(Arrays.asList("Zurich", "Chicago", "Dublin")));
			assertTrue(wordsFromCategory.indexOf(word) >= 0, "Returned word isn't in the category");
		}
	}

	@Test
	@DisplayName("getWordFromCategory works properly with the blacklist 2")
	void test6() {
		ArrayList<String> wordsFromCategory = new ArrayList<>(Arrays.asList("Kitchen", "Knife", "Fridge", "Curtain", "Window", "Garage", "Bicycle", "Armchair", "Mattress", "Bathroom", "Mirror", "Shower", "Blanket", "Speaker", "Laptop", "Candle", "Laundry"));
		wordsFromCategory.remove("Kitchen");

		for(int i=0; i<200; i++) {
			String word = GameLogic.getWordFromCategory("Home", new ArrayList<>(Collections.singletonList("Kitchen")));
			assertTrue(wordsFromCategory.indexOf(word) >= 0, "Returned word isn't in the category");
		}
	}

	@Test
	@DisplayName("generateCharacters produces correct number of letters present in the word")
	void test7() {
		String word = "ABRACADABRA";
		Pair<ArrayList<Character>, ArrayList<Character>> pair = GameLogic.generateCharacters(word);
		int lettersFromWord = 0;

		for(Character c: pair.getKey()) {
			if (word.indexOf(c) >= 0) {
				lettersFromWord += 1;
			}
		}

		assertTrue(lettersFromWord >= 3, "wrong number");
	}


	@Test
	@DisplayName("generateCharacters produces exactly 6 letters")
	void test8() {
		String word = "abracadabra";
		Pair<ArrayList<Character>, ArrayList<Character>> pair = GameLogic.generateCharacters(word);
		assertEquals(6, pair.getKey().size(), "wrong array size");
	}

	@Test
	@DisplayName("generateCharacters returns the remaining letters")
	void test9() {
		String word = "abcdefgxyz";
		Pair<ArrayList<Character>, ArrayList<Character>> pair = GameLogic.generateCharacters(word);
		assertEquals(26 - 6, pair.getValue().size(), "wrong array size");
	}

	@Test
	@DisplayName("Communication classes check")
	void test10() {
		ArrayList<Class<?>> commClasses = new ArrayList<>(Arrays.asList(GameInfo.class, LetterCheck.class, LettersRequest.class, RequestNewWord.class, WordCheck.class));
		for(Class<?> _class : commClasses) {
			Class<?>[] interfaces = _class.getInterfaces();
			boolean found = false;
			for (Class<?> anInterface : interfaces) {
				if (anInterface.getName().equals("java.io.Serializable")) {
					found = true;
				}
			}
			assertTrue(found, "Not all of communication classes implementing Serializable");
		}
	}

}
