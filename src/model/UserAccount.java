package model;

public class UserAccount {
	
	private String username;
    private String password;
    private WordleGame currentGame;
    
    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.currentGame = null;
    }

    // Getters and setters for the attributes
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setCurrentGame(WordleGame game) {
    	this.currentGame = game;
    }
    public boolean attemptLogin(String pwAttempt) {
    	return password == pwAttempt;
    }
}
