package tictactoeclient;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class HomeScreenBase extends VBox {

    protected final HBox hBox;
    protected final ScrollPane scrollPane;
    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final Button singlePlayerButton;
    protected final Button localTwoPlayersButton;
    protected final ScrollPane scrollPane0;
    protected final VBox vBox;
    protected final AnchorPane anchorPane0;
    protected final Circle avatar;
    protected final ImageView userImage;
    protected final Circle onlineCircle;
    protected final Text username;
    protected final Text text;
    protected final Text score;
    protected final AnchorPane anchorPane1;
    protected final Button muteBtn;
    protected final ImageView muteImg;
    protected final Button button;
    protected final ImageView imageView0;
    protected final Button editProfileButton;
    protected final Button logOutButton;

    public HomeScreenBase() {

        hBox = new HBox();
        scrollPane = new ScrollPane();
        anchorPane = new AnchorPane();
        imageView = new ImageView();
        singlePlayerButton = new Button();
        localTwoPlayersButton = new Button();
        scrollPane0 = new ScrollPane();
        vBox = new VBox();
        anchorPane0 = new AnchorPane();
        avatar = new Circle();
        userImage = new ImageView();
        onlineCircle = new Circle();
        username = new Text();
        text = new Text();
        score = new Text();
        anchorPane1 = new AnchorPane();
        muteBtn = new Button();
        muteImg = new ImageView();
        button = new Button();
        imageView0 = new ImageView();
        editProfileButton = new Button();
        logOutButton = new Button();

        setAlignment(javafx.geometry.Pos.CENTER);
        setPrefHeight(860.0);
        setPrefWidth(1430.0);
        setStyle("-fx-background-color: #16697A;");

        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.setPrefHeight(860.0);
        hBox.setPrefWidth(1430.0);
        hBox.setStyle("-fx-background-color: #16697A;");

        scrollPane.setPrefHeight(872.0);
        scrollPane.setPrefWidth(1459.0);
        scrollPane.setStyle("-fx-background-color: #16697A;");

        anchorPane.setPrefHeight(860.0);
        anchorPane.setPrefWidth(1430.0);
        anchorPane.setStyle("-fx-background-color: #009EAD;");

        AnchorPane.setLeftAnchor(imageView, 96.0);
        AnchorPane.setRightAnchor(imageView, 825.0);
        AnchorPane.setTopAnchor(imageView, 84.0);
        imageView.setFitHeight(198.0);
        imageView.setFitWidth(605.0);
        imageView.setLayoutX(96.0);
        imageView.setLayoutY(84.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("../Images/logo.png").toExternalForm()));

        singlePlayerButton.setAlignment(javafx.geometry.Pos.CENTER);
        singlePlayerButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        singlePlayerButton.setLayoutX(245.0);
        singlePlayerButton.setLayoutY(405.0);
        singlePlayerButton.setMnemonicParsing(false);
        singlePlayerButton.setOnAction(this::onSinglePlayerButtonClicked);
        singlePlayerButton.setPrefHeight(48.0);
        singlePlayerButton.setPrefWidth(270.0);
        singlePlayerButton.setStyle("-fx-background-color: linear-gradient(to right,#82C0CC, white); -fx-background-radius: 8;");
        singlePlayerButton.setText("Single Player");
        singlePlayerButton.setTextFill(javafx.scene.paint.Color.WHITE);
        singlePlayerButton.setFont(new Font("Calibri Bold", 22.0));

        localTwoPlayersButton.setAlignment(javafx.geometry.Pos.CENTER);
        localTwoPlayersButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        localTwoPlayersButton.setLayoutX(245.0);
        localTwoPlayersButton.setLayoutY(488.0);
        localTwoPlayersButton.setMnemonicParsing(false);
        localTwoPlayersButton.setOnAction(this::onLocalTwoPlayersButtonClicked);
        localTwoPlayersButton.setPrefHeight(48.0);
        localTwoPlayersButton.setPrefWidth(270.0);
        localTwoPlayersButton.setStyle("-fx-background-color: linear-gradient(to right,#82C0CC, white); -fx-background-radius: 8;");
        localTwoPlayersButton.setText("Local Two Players");
        localTwoPlayersButton.setTextFill(javafx.scene.paint.Color.valueOf("#fffbfb"));
        localTwoPlayersButton.setFont(new Font("Calibri Bold", 22.0));

        AnchorPane.setBottomAnchor(scrollPane0, 33.0);
        AnchorPane.setRightAnchor(scrollPane0, 311.0);
        AnchorPane.setTopAnchor(scrollPane0, 54.0);
        scrollPane0.setLayoutX(825.0);
        scrollPane0.setLayoutY(70.0);

        vBox.setPrefHeight(773.0);
        vBox.setPrefWidth(386.0);
        vBox.setStyle("-fx-background-color: White; -fx-background-radius: 25;");

        anchorPane0.setPrefHeight(465.0);
        anchorPane0.setPrefWidth(311.0);

        AnchorPane.setTopAnchor(avatar, 29.0);
        avatar.setFill(javafx.scene.paint.Color.DODGERBLUE);
        avatar.setLayoutX(193.0);
        avatar.setLayoutY(74.0);
        avatar.setRadius(45.0);
        avatar.setStroke(javafx.scene.paint.Color.TRANSPARENT);
        avatar.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        avatar.setStyle("-fx-fill: linear-gradient(#82C0CC, white);");

        userImage.setFitHeight(63.0);
        userImage.setFitWidth(57.0);
        userImage.setLayoutX(167.0);
        userImage.setLayoutY(42.0);
        userImage.setPickOnBounds(true);
        userImage.setPreserveRatio(true);
        userImage.setImage(new Image(getClass().getResource("../Images/girl.png").toExternalForm()));

        onlineCircle.setFill(javafx.scene.paint.Color.DODGERBLUE);
        onlineCircle.setLayoutX(221.0);
        onlineCircle.setLayoutY(106.0);
        onlineCircle.setRadius(5.0);
        onlineCircle.setStroke(javafx.scene.paint.Color.TRANSPARENT);
        onlineCircle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        onlineCircle.setStyle("-fx-fill: #008000;");

        username.setLayoutX(135.0);
        username.setLayoutY(135.0);
        username.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        username.setStrokeWidth(0.0);
        username.setText("Esraa Mosaad");
        username.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        username.setWrappingWidth(116.0595703125);
        username.setFont(new Font("Arial", 18.0));

        text.setLayoutX(106.0);
        text.setLayoutY(206.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Online Players");
        text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        text.setWrappingWidth(173.0595703125);
        text.setFont(new Font("Arial Bold", 20.0));

        score.setFill(javafx.scene.paint.Color.valueOf("#000000a1"));
        score.setLayoutX(135.0);
        score.setLayoutY(164.0);
        score.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        score.setStrokeWidth(0.0);
        score.setText("Score : 100");
        score.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        score.setWrappingWidth(116.0595703125);
        score.setFont(new Font("Arial", 18.0));

        anchorPane1.setLayoutX(3.0);
        anchorPane1.setLayoutY(221.0);
        anchorPane1.setPrefHeight(62.0);
        anchorPane1.setPrefWidth(386.0);
        scrollPane0.setContent(vBox);

        muteBtn.setLayoutX(61.0);
        muteBtn.setLayoutY(688.0);
        muteBtn.setMnemonicParsing(false);
        muteBtn.setOnAction(this::onMuteBtnClicked);
        muteBtn.setPrefHeight(58.0);
        muteBtn.setPrefWidth(71.0);
        muteBtn.setStyle("-fx-background-color: transparent;");
        muteBtn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        muteBtn.setTextFill(javafx.scene.paint.Color.TRANSPARENT);

        muteImg.setFitHeight(34.0);
        muteImg.setFitWidth(32.0);
        muteImg.setPickOnBounds(true);
        muteImg.setPreserveRatio(true);
        muteImg.setImage(new Image(getClass().getResource("../Images/volume.png").toExternalForm()));
        muteBtn.setGraphic(muteImg);

        button.setLayoutX(27.0);
        button.setLayoutY(33.0);
        button.setMnemonicParsing(false);
        button.setOnAction(this::onBackIconClicked);
        button.setStyle("-fx-background-color: transparent;");
        button.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        imageView0.setFitHeight(40.0);
        imageView0.setFitWidth(39.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        imageView0.setImage(new Image(getClass().getResource("../Images/previous.png").toExternalForm()));
        button.setGraphic(imageView0);

        editProfileButton.setAlignment(javafx.geometry.Pos.CENTER);
        editProfileButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        editProfileButton.setLayoutX(516.0);
        editProfileButton.setLayoutY(627.0);
        editProfileButton.setMnemonicParsing(false);
        editProfileButton.setOnAction(this::onEditProfileButtonClicked);
        editProfileButton.setPrefHeight(44.0);
        editProfileButton.setPrefWidth(211.0);
        editProfileButton.setStyle("-fx-background-color: linear-gradient(to right,#82C0CC, white); -fx-background-radius: 8;");
        editProfileButton.setText("Edit Profile");
        editProfileButton.setTextFill(javafx.scene.paint.Color.valueOf("#fffbfb"));
        editProfileButton.setFont(new Font("Calibri Bold", 18.0));

        logOutButton.setAlignment(javafx.geometry.Pos.CENTER);
        logOutButton.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        logOutButton.setLayoutX(515.0);
        logOutButton.setLayoutY(695.0);
        logOutButton.setMnemonicParsing(false);
        logOutButton.setOnAction(this::onLogoutButtonClicked);
        logOutButton.setPrefHeight(45.0);
        logOutButton.setPrefWidth(212.0);
        logOutButton.setStyle("-fx-background-color: linear-gradient(to right,#82C0CC, white); -fx-background-radius: 8;");
        logOutButton.setText("Logout");
        logOutButton.setTextFill(javafx.scene.paint.Color.valueOf("#fffafa"));
        logOutButton.setFont(new Font("Calibri Bold", 18.0));
        anchorPane.setPadding(new Insets(16.0));
        scrollPane.setContent(anchorPane);

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(singlePlayerButton);
        anchorPane.getChildren().add(localTwoPlayersButton);
        anchorPane0.getChildren().add(avatar);
        anchorPane0.getChildren().add(userImage);
        anchorPane0.getChildren().add(onlineCircle);
        anchorPane0.getChildren().add(username);
        anchorPane0.getChildren().add(text);
        anchorPane0.getChildren().add(score);
        anchorPane0.getChildren().add(anchorPane1);
        vBox.getChildren().add(anchorPane0);
        anchorPane.getChildren().add(scrollPane0);
        anchorPane.getChildren().add(muteBtn);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(editProfileButton);
        anchorPane.getChildren().add(logOutButton);
        hBox.getChildren().add(scrollPane);
        getChildren().add(hBox);

    }

    protected abstract void onSinglePlayerButtonClicked(javafx.event.ActionEvent actionEvent);

    protected abstract void onLocalTwoPlayersButtonClicked(javafx.event.ActionEvent actionEvent);

    protected abstract void onMuteBtnClicked(javafx.event.ActionEvent actionEvent);

    protected abstract void onBackIconClicked(javafx.event.ActionEvent actionEvent);

    protected abstract void onEditProfileButtonClicked(javafx.event.ActionEvent actionEvent);

    protected abstract void onLogoutButtonClicked(javafx.event.ActionEvent actionEvent);

}
