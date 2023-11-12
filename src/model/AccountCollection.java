package model;

import java.io.Serializable;
import java.util.HashMap;

// Holds the Account objects
public class AccountCollection implements Serializable {
	private static final long serialVersionUID = 1L;
	HashMap<String, UserAccount> accounts;
	UserAccount loggedIn;

	public AccountCollection() {
		accounts = new HashMap<String, UserAccount>();
	}

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

	public boolean login(String username, String password) {
		// If username exists and passwords match.
		if (accounts.containsKey(username) && accounts.get(username).getPassword().equals(password)) {
			// Set loggedIn to the currently logged in account
			loggedIn = accounts.get(username);
			return true;
		}
		return false;
	}

	public void logout() {
		loggedIn = null;
	}
	
	public UserAccount getLoggedIn() {
		return loggedIn;
	}

}