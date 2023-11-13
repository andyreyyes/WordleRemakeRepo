package model;

import java.io.Serializable;
import java.util.HashMap;

// Holds the Account objects
public class AccountCollection implements Serializable {
	private static final long serialVersionUID = 1L;
	HashMap<String, UserAccount> accounts;
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
			return true;
		}
		return false;
	}
	public UserAccount getAccount(String username, String password) {
		if(login(username,password)) {
			return accounts.get(username);
		}
		else {
			return null;
		}
	}
	public boolean validName(String name) {
		return !(accounts.keySet().contains(name));
	}
}