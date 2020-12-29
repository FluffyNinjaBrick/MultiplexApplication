package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Seat;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class AddSeatPresenter {
    private Seat seat;

    @FXML
    private TextField numberField;

    @FXML
    private TextField rowField;

    @FXML
    private TextField roomIdField;

    private Stage dialogStage;

    private boolean approved;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(Seat seat) {
        this.seat = seat;
        updateControls();
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        if(isNumeric(numberField.getText()) && isNumeric(rowField.getText()) && isNumeric(roomIdField.getText())){
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
        seat.setSeatNumber(Integer.parseInt(numberField.getText()));
        seat.setRowNumber(Integer.parseInt(rowField.getText()));
        seat.setScreeningRoomId(Long.parseLong(roomIdField.getText()));
    }

    private void updateControls() {
        numberField.setText(String.valueOf(seat.getSeatNumber()));
        rowField.setText(String.valueOf(seat.getRowNumber()));
        roomIdField.setText(String.valueOf(seat.getScreeningRoomId()));
    }

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
