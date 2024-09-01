import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class materialController {

    @FXML
    private ImageView img;

    @FXML
    private Label name;

    @FXML
    private Label price;
    Material material;

    public void setData(Material material) {
        this.material = material;
        name.setText(material.getMname());
        price.setText(material.getMprice());
        Image image = new Image(material.getMurl());
        img.setImage(image);
    }
}