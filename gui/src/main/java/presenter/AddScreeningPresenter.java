package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import model.Screening;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;

public class AddScreeningPresenter {
    private Screening screening;
    private LocalDateStringConverter converter;
    @FXML
    private TextField costField;

    @FXML
    private TextField movieIdField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField roomIdField;

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
    public void initialize(){
        //"2012-03-19T07:22Z"
        String pattern = "yyyy-MM-ddThh:mmZ"; // jakoś tak?
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        converter = new LocalDateStringConverter(formatter, formatter);
    }
    @FXML
    private void handleOkAction(ActionEvent event) {
        if(isNumeric(movieIdField.getText()) && isNumeric(roomIdField.getText()) && isNumeric(costField.getText())){
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
        screening.setTicketCost(Integer.valueOf(costField.getText()));
        screening.setDate(dateField.getText());
        screening.setMovieId(Long.parseLong(movieIdField.getText()));
        screening.setScreeningRoomId(Long.parseLong(roomIdField.getText()));
    }

    private void updateControls() {
        costField.setText(String.valueOf(screening.getTicketCost()));
        dateField.setText(String.valueOf(screening.getDate()));
        movieIdField.setText(String.valueOf(screening.getMovieId()));
        roomIdField.setText(String.valueOf(screening.getScreeningRoomId()));
    }

    public static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
