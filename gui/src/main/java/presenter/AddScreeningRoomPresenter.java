package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.ScreeningRoom;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class AddScreeningRoomPresenter {
    private ScreeningRoom screeningRoom;

    @FXML
    private TextField floorField;

    @FXML
    private TextField capacityField;

    @FXML
    private TextField numberField;

    private Stage dialogStage;

    private boolean approved;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(ScreeningRoom screeningRoom) {
        this.screeningRoom = screeningRoom;
        updateControls();
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        if(isNumeric(capacityField.getText())){
            approved = false;
            dialogStage.close();
        }
        updateModel();
        approved = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel() {
        screeningRoom.setFloor(Integer.parseInt(floorField.getText()));
        screeningRoom.setCapacity(Integer.valueOf(capacityField.getText()));
        screeningRoom.setNumber(numberField.getText());
    }

    private void updateControls() {
        floorField.setText(String.valueOf(screeningRoom.getFloor()));
        capacityField.setText(String.valueOf(screeningRoom.getCapacity()));
        numberField.setText(screeningRoom.getNumber());
    }

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
