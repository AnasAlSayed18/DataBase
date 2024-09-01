import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {

    @FXML
    private Button Login2;

    @FXML
    private Button SignUp2;

    @FXML
    private CheckBox checkBox;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private Button forgetBtn;

    @FXML
    private ComboBox<String> forgetCombo;

    @FXML
    private TextField forgetTf;

    @FXML
    private VBox forgetVbox;

    @FXML
    private Button loginBtn;

    @FXML
    private VBox loginVbox;

    @FXML
    private PasswordField passTf;

    @FXML
    private TextField resestTf;

    @FXML
    private Button resetBtn;

    @FXML
    private VBox resetVbox;

    @FXML
    private TextField restCTf;

    @FXML
    private TextField showTf;

    @FXML
    private TextField signAdressTf;

    @FXML
    private TextField signCpassTf;

    @FXML
    private TextField signEmailTf;

    @FXML
    private TextField signPassTf;

    @FXML
    private TextField signPhoneTf;

    @FXML
    private TextField signUserTf;

    @FXML
    private Button signupBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button backBtn1;

    @FXML
    private VBox signupVbox;

    @FXML
    private Button submit;

    @FXML
    private TextField userTf;

    Account account = new Account();
    String[] typeArr = { "Admin", "Coustomer" };
    String[] colroArr = { "Blue", "Green", "Red", "Yellow", "Orange", "white" };
    String username;
    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;
    private Statement statement;

    private ResultSet result3;
    private PreparedStatement prepare3;
    private Statement statement3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().addAll(typeArr);
        comboBox1.getItems().addAll(colroArr);
        forgetCombo.getItems().addAll(colroArr);
        signupVbox.setVisible(false);
        loginVbox.setVisible(true);
        showTf.setVisible(false);
        forgetVbox.setVisible(false);
        resetVbox.setVisible(false);

        loginBtn.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        loginBtn.fire();
                    }
                });
            }
        });
    }

    public void signupAction() {
        try {
            replaceVBox(loginVbox, signupVbox);

        } catch (Exception e) {
        }
    }

    public void LoginAction() {
        alertMassege alret = new alertMassege();

        String userData = "SELECT * FROM Users WHERE username = ? and U_password = ?";

        String userType = "SELECT U_role FROM Users WHERE username = ? and U_password = ?";

        connect = dataBase.connectDb();
        if (connect == null) {
            alret.errorMassage("Incorrect Username/ Password");

        }
        try {

            if (userTf.getText().isBlank() || passTf.getText().isBlank()) {
                alret.errorMassage("Incorrect Username/ Password");
            } else {
                prepare = connect.prepareStatement(userData);
                prepare.setString(1, userTf.getText());
                prepare.setString(2, passTf.getText());

                result = prepare.executeQuery();

                if (result.next()) {

                    String email = String.valueOf(result.getString(1));
                    String userName = String.valueOf(result.getString(2));
                    String passwd = String.valueOf(result.getString(3));
                    String address = String.valueOf(result.getString(4));
                    String phone = String.valueOf(result.getString(5));
                    String role = String.valueOf(result.getString(6));
                    String favoriteColor = (result.getString(7));
                    String budget = String.valueOf(result.getDouble(8));

                    account.email = email;
                    account.username = userName;
                    account.U_password = passwd;
                    account.U_role = role;
                    account.favoriteColor = favoriteColor;
                    account.addrees = address;
                    account.phone = phone;
                    account.budget = budget;

                    if (role.equals("Employee")) {

                        alret.successMessage("Login Successfully , " + "Welcome " + userTf.getText());

                        Parent root = FXMLLoader.load(getClass().getResource("employeeScreen.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        stage.setTitle("Employee Screen");
                        stage.getIcons().addAll(new Image("img\\Logo.png"));
                        userTf.getScene().getWindow().hide();
                    }
                    if (role.equals("Admin")) {
                        adminController.welcomeMassege = userTf.getText();
                        alret.successMessage("Login Successfully , " + "Welcome " + userTf.getText());

                        Parent root = FXMLLoader.load(getClass().getResource("adminScreen.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        stage.setTitle("Admin Screen");
                        stage.getIcons().addAll(new Image("img\\Logo.png"));
                        userTf.getScene().getWindow().hide();

                    }
                    if (role.equals("Coustomer")) {

                        alret.successMessage("Login Successfully , " + "Welcome " + userTf.getText());

                        Parent root = FXMLLoader.load(getClass().getResource("Coustomer.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        stage.setTitle("Coustomer Screen");
                        stage.getIcons().addAll(new Image("img\\Logo.png"));
                        userTf.getScene().getWindow().hide();

                    }

                } else {
                    alret.errorMassage("Wrong Username/password");

                }

            }

        } catch (

        Exception e) {
        }
    }

    public void signup2loginAction() {
        replaceVBox2(signupVbox, loginVbox);
    }

    public void forgetPassword() {
        forgetVbox.setVisible(true);
        loginVbox.setVisible(false);
        signupVbox.setVisible(false);
    }

    public void Signup2Action() {

        Parent root;

        alertMassege alret = new alertMassege();

        try {
            if (signUserTf.getText().isBlank() || signPassTf.getText().isBlank() || signPhoneTf.getText().isBlank()
                    || signEmailTf.getText().isBlank() || signCpassTf.getText().isBlank()
                    || signAdressTf.getText().isBlank()) {
                alret.errorMassage("Incorrect Username/ Password");
            } else if (!(signCpassTf.getText().equals(signPassTf.getText()))) {
                alret.errorMassage("Password dont match...");

                // } else if (!(signEmailTf.getText().equals(signPassTf.getText()))) {
                // alret.errorMassage("Email dont match...");

            } else if (signPassTf.getText().length() < 8) {
                alret.errorMassage("Invalid Password,at least 8 characters needed");

            }

            else {

                String checkUserName = "SELECT * FROM Users WHERE username = '" + userTf.getText() + "'";
                try {

                    connect = dataBase.connectDb();
                    statement = connect.createStatement();
                    result = statement.executeQuery(checkUserName);

                    if (result.next()) {
                        alret.errorMassage(userTf.getText() + "is already taken");

                    } else {
                        if (comboBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("admin")) {
                            String insertData = "INSERT INTO Users "
                                    + "(email ,username,U_password,addrees,phone,U_role,favoriteColor,budget)"
                                    + "VALUES (?,?,?,?,?,?,?,?)";

                            PreparedStatement prepare1 = connect.prepareStatement(insertData);
                            prepare1.setString(1, signEmailTf.getText());
                            prepare1.setString(2, signUserTf.getText());
                            prepare1.setString(3, signPassTf.getText());
                            prepare1.setString(4, signAdressTf.getText());
                            prepare1.setString(5, signPhoneTf.getText());
                            prepare1.setString(6, (String) comboBox.getSelectionModel().getSelectedItem());
                            prepare1.setString(7, (String) comboBox1.getSelectionModel().getSelectedItem());
                            prepare1.setString(8, String.valueOf(getFirstAdmin()));

                            prepare1.executeUpdate();

                            alret.successMessage("Product Upadted Successfully!");

                        } else {

                            String insertData = "INSERT INTO Users "
                                    + "(email ,username,U_password,addrees,phone,U_role,favoriteColor)"
                                    + "VALUES (?,?,?,?,?,?,?)";

                            prepare = connect.prepareStatement(insertData);
                            prepare.setString(1, signEmailTf.getText());
                            prepare.setString(2, signUserTf.getText());
                            prepare.setString(3, signPassTf.getText());
                            prepare.setString(4, signAdressTf.getText());
                            prepare.setString(5, signPhoneTf.getText());
                            prepare.setString(6, (String) comboBox.getSelectionModel().getSelectedItem());
                            prepare.setString(7, (String) comboBox1.getSelectionModel().getSelectedItem());

                            prepare.executeUpdate();

                            alret.successMessage("Product Upadted Successfully!");

                            if (((String) comboBox.getSelectionModel().getSelectedItem()).equals("Coustomer")) {

                                String insertCostmer = "INSERT INTO Customers "
                                        + "(Cname,phone,Ctype,address)"
                                        + "VALUES (?,?,?,?)";

                                prepare = connect.prepareStatement(insertCostmer);

                                prepare.setString(1, signUserTf.getText());
                                prepare.setString(2, signPhoneTf.getText());
                                prepare.setString(3, "Indevisual");
                                prepare.setString(4, signAdressTf.getText());
                                prepare.executeUpdate();

                            }

                        }
                    }

                } catch (Exception e) {
                    alertMassege alret1 = new alertMassege();
                    alret1.errorMassage("Invalid Please enter the empty field...");
                }

            }

        } catch (Exception e) {
            alertMassege alret1 = new alertMassege();
            alret1.errorMassage("Invalid Please enter the empty field...");
        }

    }

    private void fadeOutVBox(VBox vbox) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), vbox);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> vbox.setVisible(false));
        fadeTransition.play();
    }

    private void replaceVBox(VBox vbox1, VBox vbox2) {
        // Create a translate transition to move vbox1 to the bottom
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(1000), vbox1);
        translateTransition1.setFromY(0);
        translateTransition1.setToY(vbox1.getScene().getHeight());
        translateTransition1.setOnFinished(event -> vbox1.setVisible(false));
        translateTransition1.play();

        // Set initial position of vbox2 above the scene
        vbox2.setTranslateY(-vbox2.getHeight());
        vbox2.setVisible(true);

        // Create a translate transition to move vbox2 to the center
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(1000), vbox2);
        translateTransition2.setFromY(-vbox2.getHeight());
        translateTransition2.setToY((vbox1.getScene().getHeight() - vbox2.getHeight()) / 2);

        // Optionally, create a fade transition for vbox2
        FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(1000), vbox2);
        fadeTransition2.setFromValue(0.0);
        fadeTransition2.setToValue(1.0);

        // Play both transitions for vbox2
        translateTransition2.play();
        fadeTransition2.play();
    }

    private void replaceVBox2(VBox vbox1, VBox vbox2) {
        Scene scene = vbox1.getScene(); // Get the scene from VBox

        // Create a translate transition to move vbox1 to the top
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(1000), vbox1);
        translateTransition1.setFromY(0);
        translateTransition1.setToY(-vbox1.getHeight());
        translateTransition1.setOnFinished(event -> vbox1.setVisible(false));
        translateTransition1.play();

        // Set initial position of vbox2 below the scene
        vbox2.setTranslateY(scene.getHeight());
        vbox2.setVisible(true);

        // Create a translate transition to move vbox2 to the center
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(1000), vbox2);
        translateTransition2.setFromY(scene.getHeight());
        translateTransition2.setToY((scene.getHeight() - vbox2.getHeight()) / 2);

        // Optionally, create a fade transition for vbox2
        FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(1000), vbox2);
        fadeTransition2.setFromValue(0.0);
        fadeTransition2.setToValue(1.0);

        // Play both transitions for vbox2
        translateTransition2.play();
        fadeTransition2.play();
    }

    public void CheckAction() {
        if (checkBox.isSelected()) {
            String passwd = passTf.getText();
            passTf.setVisible(false);
            showTf.setText(passwd);
            showTf.setVisible(true);
        } else {
            showTf.setVisible(false);
            passTf.setVisible(true);
        }
    }

    public void CheckUserForget() {
        alertMassege alret = new alertMassege();
        username = forgetTf.getText();
        String color = String.valueOf(forgetCombo.getValue());
        String checkUserName = "SELECT favoriteColor FROM Users WHERE username = ?";
        connect = dataBase.connectDb();
        if (connect == null) {
            alret.errorMassage(userTf.getText() + "ًWrong Username");

        }

        try {

            if (forgetTf.getText().isBlank()) {
                alret.errorMassage("Incorrect Username");
            } else {
                prepare = connect.prepareStatement(checkUserName);
                prepare.setString(1, forgetTf.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    if (result.getString(1).equals(color)) {
                        loginVbox.setVisible(false);
                        signupVbox.setVisible(false);
                        forgetVbox.setVisible(false);
                        resetVbox.setVisible(true);
                    }
                } else {
                    alret.errorMassage("Wrong Username/password");

                }

            }

        } catch (Exception e) {
        }

    }

    public void RessetPassword() {
        alertMassege alret = new alertMassege();
        String passwd = resestTf.getText();
        String ConfirPasswd = restCTf.getText();
        if (!(passwd.equals(ConfirPasswd))) {
            alret.errorMassage("Password dont match...");

        } else if (passwd.length() < 8) {
            alret.errorMassage("Invalid Password,at least 8 characters needed");

        } else if (passwd.equals(ConfirPasswd)) {
            String str = "Update Users set  U_password=? where ?=username";
            connect = dataBase.connectDb();
            if (connect == null) {
                alret.errorMassage(userTf.getText() + "ًWrong Username");

            } else {
                try {
                    if (resestTf.getText().isBlank()) {
                        alret.errorMassage("Incorrect Passwd");
                    } else {
                        prepare = connect.prepareStatement(str);
                        prepare.setString(1, resestTf.getText());
                        prepare.setString(2, username);

                        int x = prepare.executeUpdate();

                        if (x > 0) {

                        } else {
                            alret.errorMassage("Wrong Username/password");

                        }

                    }
                    alret.successMessage("Password Changed Correctly!");
                    loginVbox.setVisible(true);
                    signupVbox.setVisible(false);
                    forgetVbox.setVisible(false);
                    resetVbox.setVisible(false);

                } catch (Exception e) {
                }
            }
        }

    }

    public void backBtnAction() {
        loginVbox.setVisible(true);
        signupVbox.setVisible(false);
        forgetVbox.setVisible(false);
        resetVbox.setVisible(false);
    }

    public double getFirstAdmin() {
        String getAdmin = "SELECT budget FROM users WHERE U_role = 'Admin' LIMIT 1";
        double budget = 0;
        try {
            prepare = connect.prepareStatement(getAdmin);
            result = prepare.executeQuery();

            if (result.next()) {
                budget = result.getDouble(1);
            }

        } catch (SQLException e) {
   
        }
        return budget;
    }
}