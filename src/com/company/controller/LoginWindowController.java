package com.company.controller;

import com.company.EmailManager;
import com.company.controller.services.LoginService;
import com.company.model.EmailAccount;
import com.company.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passField;
    @FXML
    private Label errorLabel;

    @FXML
    void loginAction() {
        if(fieldsAreValid()){
            EmailAccount emailAccount = new EmailAccount(emailField.getText(), passField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = loginService.getValue();
                switch (emailLoginResult){
                    case SUCCESS:
                        System.out.println("success" + emailAccount);
                        if(!viewFactory.isMainViewInitialize()){
                            viewFactory.showMainWindow();
                        }
                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("Invalid credentials!");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("Unexpected error!");
                        return;
                    default:
                        return;
                }
            });

        }

    }

    private boolean fieldsAreValid() {
        if(emailField.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        }
        if(passField.getText().isEmpty()) {
            errorLabel.setText("Please fill password");
            return false;
        }

        return true;

    }

}
