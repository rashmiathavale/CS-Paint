import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
* This class represents an Paint class.
* @author Rashmi Athavale
* @version 1.0
*/
public class Paint {
    /**
    * This method is used to alert the user if an invalid color has been entered.
    */
    public void alert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Invalid Color");
        alert.setContentText("Invalid color entered!");
        alert.showAndWait();
    }
}
