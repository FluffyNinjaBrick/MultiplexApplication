package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class GetUserByIdPresenter {

    private User user;

    @FXML
    private TextField idField;

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
        if(isNumeric(idField.getText())){
            updateModel();
            approved = true;
            dialogStage.close();
        }else{
            approved = false;
            dialogStage.close();
        }

    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel() {
        user.setId(Long.parseLong(idField.getText()));
    }

    private void updateControls() {
        idField.setText(String.valueOf(user.getId()));
    }

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
