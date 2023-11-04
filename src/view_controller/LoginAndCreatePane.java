package view_controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginAndCreatePane extends VBox{
	
	private Button loginButton = new Button("Login");
	private Button logoutButton = new Button("Logout");
	private Button createAccountButton = new Button("Create Account");
	
	private TextField usernameInput;
	private PasswordField passwordInput;
	
	private Label informationLabel = new Label("Create a new Account or Login");
	
	
	public LoginAndCreatePane() {
		initilizePanel();
		registerListeners();
	}

	private void registerListeners() {
		// TODO Auto-generated method stub
		
	}

	private void initilizePanel() {
		

		
	}

}
