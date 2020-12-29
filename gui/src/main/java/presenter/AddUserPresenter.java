package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.User;

import javafx.scene.control.TextField;

public class AddUserPresenter {

    private User user;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    private Stage dialogStage;

    private boolean approved;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(User user) {
        this.user = user;
        updateControls();
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel() {
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setUserName(loginField.getText());
        user.setPassword(passwordField.getText());
        user.setEmail(emailField.getText());
    }

    private void updateControls() {
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        loginField.setText(user.getUserName());
        passwordField.setText(user.getPassword());
        emailField.setText(user.getEmail());
    }
}
