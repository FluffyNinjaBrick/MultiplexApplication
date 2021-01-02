package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Reservation;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class AddReservationPresenter {
    private Reservation reservation;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField screeningIdField;

    @FXML
    private TextField seatIdField;

    private Stage dialogStage;

    private boolean approved;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(Reservation reservation) {
        this.reservation = reservation;
        updateControls();
    }

    public boolean isApproved() {
        return approved;
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        if(isNumeric(userIdField.getText()) && isNumeric(screeningIdField.getText()) && isNumeric(seatIdField.getText())){
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
        reservation.setUserId(Long.parseLong(userIdField.getText()));
        reservation.setScreeningId(Long.parseLong(screeningIdField.getText()));
        reservation.setSeatId(Long.parseLong(seatIdField.getText()));
    }

    private void updateControls() {
        userIdField.setText(String.valueOf(reservation.getUserId()));
        screeningIdField.setText(String.valueOf(reservation.getScreeningId()));
        seatIdField.setText(String.valueOf(reservation.getSeatId()));
    }

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
