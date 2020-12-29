package command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandRegistry {
    private ObservableList<Command> commandStack = FXCollections.observableArrayList();

    public void executeCommand(Command command){
        command.execute();
        commandStack.add(command);
    }
}
