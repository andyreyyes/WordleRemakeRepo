package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.WordleGame;

public class TestWordleGame {

	@Test
	public void test() {
		WordleGame game = new WordleGame();
		assertEquals(5,game.getTargetWord().length());
		WordleGame game2 = new WordleGame("capon");
		assertFalse(game2.processGuess("slope"));
		assertTrue(game2.processGuess("capon"));
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("slope");
		temp.add("capon");
		game2.printGuesses();
		assertEquals(temp,game2.getGuesses());
		
		ArrayList<Character> temp2 = new ArrayList<Character>();
		temp2.add('s');
		temp2.add('l');
		temp2.add('o');
		temp2.add('p');
		temp2.add('e');
		
		temp2.add('c');
		temp2.add('a');
		temp2.add('n');
		
		assertEquals(temp2,game2.getGuessedLetters());

	}

}
