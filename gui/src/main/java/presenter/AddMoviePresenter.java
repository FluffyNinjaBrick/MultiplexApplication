package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Movie;

public class AddMoviePresenter {
    private Movie movie;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField descriptionField;

    private Stage dialogStage;

    private boolean approved;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(Movie movie) {
        this.movie = movie;
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
        movie.setTitle(titleField.getText());
        movie.setAuthor(authorField.getText());
        movie.setDescription(descriptionField.getText());
    }

    private void updateControls() {
        titleField.setText(movie.getTitle());
        authorField.setText(movie.getAuthor());
        descriptionField.setText(movie.getDescription());
    }
}
