			package view_controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.AccountCollection;
import model.UserAccount;

public class LoginAndCreatePane extends VBox {

	private Button loginButton = new Button("Login");
	private Button logoutButton = new Button("Logout");
	private Button createAccountButton = new Button("Create Account");

	private TextField usernameInput;
	private PasswordField passwordInput;
	private Boolean loggedIn = false;
	
	private MainWordleGUI game;

	private Label usernameLabel = new Label("Username: ");
	private Label passwordLabel = new Label("Password: ");
	
    private AccountCollection accounts;
    private UserAccount curAccount;

	private Label informationLabel = new Label("Create a new Account or Login");

	public LoginAndCreatePane(MainWordleGUI game) {
		this.game = game;
		setupAccounts();
		initilizePanel();
		registerListeners();
	}

	private void setupAccounts() {
		
		try {
			// get the account Collection if it exist
			FileInputStream rawBytes = new FileInputStream("accounts.ser");
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			AccountCollection objects = (AccountCollection) inFile.readObject();
			inFile.close();
			accounts = (AccountCollection) objects;
		} catch (IOException | ClassNotFoundException e) {
			// making a new file
			try {
				accounts = new AccountCollection();
				FileOutputStream bytesToDisk = new FileOutputStream("accounts.ser");
				ObjectOutputStream outFile;
				outFile = new ObjectOutputStream(bytesToDisk);
				outFile.writeObject(accounts);
				outFile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
			
	}

	private void registerListeners() {
		loginButton.setOnAction((event) -> {
			if (!loggedIn) {
				if (usernameInput.getText().length() > 0 && passwordInput.getText().length() > 0 && accounts.login(usernameInput.getText(), passwordInput.getText())) {
					informationLabel.setText("Sucessfully logged in");
					loggedIn = true;
					curAccount = accounts.getAccount(usernameInput.getText(), passwordInput.getText());
					usernameInput.setText("");
					passwordInput.setText("");
					game.setGamePane();
					
					System.out.println(curAccount.getGuessDist());

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
				curAccount = null;
				informationLabel.setText("Sucessfully logged out");
				loggedIn = false;

			} else {
				informationLabel.setText("Not logged in yet");
			}

		});
		createAccountButton.setOnAction((event) -> {
			if (!loggedIn) {
				if (usernameInput.getText().length() > 0 && passwordInput.getText().length() > 0 && accounts.validName(usernameInput.getText())) {
					String username = usernameInput.getText();
					String password = passwordInput.getText();
					usernameInput.setText("");
					passwordInput.setText("");
					accounts.makeAccount(username, password);
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
	public UserAccount getUser() {
		return curAccount;
	}
	public void setupCloseAction(Stage stage) {
		stage.setOnCloseRequest(event -> {
			try {
			FileOutputStream bytesToDisk = new FileOutputStream("accounts.ser");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(accounts);
			outFile.close();
			}
			catch (IOException ioe) {
				System.out.println(ioe);
			}
			// save to ser file
			Platform.exit();
			System.exit(0);
		});
	}

}
