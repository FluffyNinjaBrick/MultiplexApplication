package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class LogInPresenter {

    private User user;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

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
        user.setUsername(loginField.getText());
        user.setPassword(passwordField.getText());
    }

    private void updateControls() {
        loginField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
    }
}