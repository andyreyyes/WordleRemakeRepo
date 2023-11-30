package model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class is used to store UserAccount objects.
 * 
 * @author Adler Nguyen
 *
 */
public class AccountCollection implements Serializable {
	private static final long serialVersionUID = 1L;
	HashMap<String, UserAccount> accounts;

	/**
	 * This is the constructor of the class that creates a HashMap that is used to
	 * store the accounts.
	 */
	public AccountCollection() {
		accounts = new HashMap<String, UserAccount>();
	}

	/**
	 * This method creates accounts and puts it into the AccountCollection.
	 * 
	 * @param username A String that is the username of the account
	 * @param password A String that is the password of the account.
	 * @return A boolean that is true if the account was made successfully.
	 */
	public boolean makeAccount(String username, String password) {
		// Checks for empty string/spaces in username and password
		if (!username.strip().equals("") && !password.strip().equals("")) {
			// If username doesnt exist
			if (!accounts.containsKey(username)) {
				accounts.put(username, new UserAccount(username, password));
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used when logging in.
	 * 
	 * @param username A String which is the username of the user.
	 * @param password A String which is the password of the user.
	 * @return A boolean that is true if the account exists in the
	 *         AccountCollection.
	 */
	public boolean login(String username, String password) {
		// If username exists and passwords match.
		if (accounts.containsKey(username) && accounts.get(username).getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	/**
	 * This method is used to get an account out of the account collection.
	 * 
	 * @param username A String that is the username of the user.
	 * @param password A String that is the password of the user.
	 * @return An UserAccount object.
	 */
	public UserAccount getAccount(String username, String password) {
		if (login(username, password)) {
			return accounts.get(username);
		} else {
			return null;
		}
	}

	/**
	 * This method checks if a username is valid.
	 * 
	 * @param name A String which is the username of the user.
	 * @return A Boolean that is true if the name exists in the AccountCollection.
	 */
	public boolean validName(String name) {
		return !(accounts.keySet().contains(name));
	}

	/**
	 * This method is used for testing that prints all the usernames in the
	 * AccountCollection.
	 */
	public void printAllNames() {
		System.out.println("All Usernames in the HashMap:");
		for (String username : accounts.keySet()) {
			System.out.println(username);
		}
	}

}