package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import model.UserAccount;

public class TestUserAccount {

	@Test
	public void test() {
		
		UserAccount user = new UserAccount("Adler","hi");
		assertEquals(user.getPassword(),"hi");
		assertEquals(user.getUsername(),"Adler");
		
		assertFalse(user.attemptLogin("hii"));
		assertTrue(user.attemptLogin("hi"));
		
	}

}
