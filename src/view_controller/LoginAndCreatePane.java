package view_controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.UserAccount;

public class LoginAndCreatePane extends VBox {

	private Button loginButton = new Button("Login");
	private Button logoutButton = new Button("Logout");
	private Button createAccountButton = new Button("Create Account");

	private TextField usernameInput;
	private PasswordField passwordInput;
	private Boolean loggedIn = false;

	private Label usernameLabel = new Label("Username: ");
	private Label passwordLabel = new Label("Password: ");

	private Label informationLabel = new Label("Create a new Account or Login");

	public LoginAndCreatePane() {
		initilizePanel();
		registerListeners();
	}

	private void registerListeners() {
		loginButton.setOnAction((event) -> {
			if (!loggedIn) {
				if (usernameInput.getText().length() > 0 && passwordInput.getText().length() > 0) {
					
					String username = usernameInput.getText();
					String password = passwordInput.getText();
					
					usernameInput.setText("");
					passwordInput.setText("");
					UserAccount tempAccount = new UserAccount(username, password);
					informationLabel.setText("Sucessfully logged in");
					loggedIn = true;

				} else {
					informationLabel.setText("Enter a valid username and Password");
				}
			} else {
				informationLabel.setText("Already Logged in");
			}
		});
		logoutButton.setOnAction((event) -> {
			if (loggedIn) {
				usernameInput.setText("");
				passwordInput.setText("");
				informationLabel.setText("Sucessfully logged out");
				loggedIn = false;

			} else {
				informationLabel.setText("Not logged in yet");
			}

		});
		createAccountButton.setOnAction((event) -> {
			if (!loggedIn) {
				if (usernameInput.getText().length() > 0 && passwordInput.getText().length() > 0) {
					String username = usernameInput.getText();
					String password = passwordInput.getText();
					usernameInput.setText("");
					passwordInput.setText("");
					UserAccount newAccount = new UserAccount(username, password);
					informationLabel.setText("Sucessfully created an Account");
					
				} else {
					informationLabel.setText("Enter a valid username and Password");
				}
			} else {
				informationLabel.setText("Already Logged in");
			}
		});
	}

	private void initilizePanel() {
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setSpacing(10);

		this.getChildren().add(informationLabel);

		HBox usernameHbox = new HBox();
		usernameInput = new TextField();
		usernameHbox.getChildren().addAll(usernameLabel, usernameInput);
		usernameHbox.setAlignment(Pos.CENTER);
		usernameHbox.setSpacing(10);

		this.getChildren().add(usernameHbox);

		HBox passwordHbox = new HBox();
		passwordInput = new PasswordField();
		passwordHbox.getChildren().addAll(passwordLabel, passwordInput);
		passwordHbox.setAlignment(Pos.CENTER);
		passwordHbox.setSpacing(10);

		this.getChildren().add(passwordHbox);

		HBox buttonsHbox = new HBox();
		buttonsHbox.getChildren().addAll(loginButton, logoutButton, createAccountButton);
		buttonsHbox.setAlignment(Pos.CENTER);
		buttonsHbox.setSpacing(10);

		this.getChildren().add(buttonsHbox);

		this.setAlignment(Pos.CENTER);
	}
	
	public boolean getLoggedInStatus() {
		return loggedIn;
	}

}
