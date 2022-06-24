package com.app.controllers;

import com.app.animations.Shake;
import com.app.database.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signGuestButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        signInButton.setOnAction(actionEvent -> {
            try {
                String login = loginField.getText().trim();
                String password = passwordField.getText().trim();
                loginUser(login, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        signUpButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/registration-view.fxml", signUpButton);
        });
        signGuestButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/edit_menu-view.fxml", signGuestButton);
        });

    }

    private void loginUser(String login, String password) throws SQLException {
        if (!login.equals("") && !password.equals("")) {
            ResultSet user = User.get(login, password);
            int counter = 0;
            while (user.next()) {
                counter++;
            }
            if (counter >= 1) {
                openNewWindow("/com/app/edit_menu-view.fxml", signInButton);
            } else {
                loginErrorAnimation();
            }
        } else {
            loginErrorAnimation();
        }
    }

    public static void openNewWindow(String window, Button button) {
        button.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AuthController.class.getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();

        stage.setMaxHeight(438);
        stage.setMaxWidth(610);
        stage.setMinHeight(438);
        stage.setMinWidth(610);

        stage.setScene(new Scene(root));
        stage.show();
    }

    private void loginErrorAnimation() {
        Shake userLoginAnimation = new Shake(loginField);
        Shake userPasswordAnimation = new Shake(passwordField);
        userLoginAnimation.play();
        userPasswordAnimation.play();
    }

}