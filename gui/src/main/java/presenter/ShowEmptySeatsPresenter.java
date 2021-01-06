package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Screening;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class ShowEmptySeatsPresenter {

    private Screening screening;

    @FXML
    private TextField idField;

    private Stage dialogStage;

    private boolean approved;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(Screening screening) {
        this.screening = screening;
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
        }else {
            approved = false;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel() {
        screening.setId(Long.parseLong(idField.getText()));
    }

    private void updateControls() {
        idField.setText(String.valueOf(screening.getId()));
    }

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}