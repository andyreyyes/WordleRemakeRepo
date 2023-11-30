package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import model.UserAccount;

/**
 * This class is used to test the accounts.
 * 
 * @author Andy Reyes Adler Nguyen Brian Nguyen John Le
 *
 */
public class TestUserAccount {

	@Test
	/**
	 * This method tests the UserAccount class using JUnit tests.
	 */
	public void test() {

		UserAccount user = new UserAccount("Adler", "hi");
		assertEquals(user.getPassword(), "hi");
		assertEquals(user.getUsername(), "Adler");

		assertFalse(user.attemptLogin("hii"));
		assertTrue(user.attemptLogin("hi"));

	}

}
