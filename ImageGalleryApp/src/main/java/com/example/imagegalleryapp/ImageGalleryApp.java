package com.example.imagegalleryapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageGalleryApp extends Application {

    private final String[] imagePaths = {
            "src/main/resources/com/example/imagegalleryapp/image/image1.jpg",
            "src/main/resources/com/example/imagegalleryapp/image/image2.jpg",
            "src/main/resources/com/example/imagegalleryapp/image/image3.jpg",
            "src/main/resources/com/example/imagegalleryapp/image/image4.jpg",
            "src/main/resources/com/example/imagegalleryapp/image/image5.jpg",
            "src/main/resources/com/example/imagegalleryapp/image/image7.jpg",
            "src/main/resources/com/example/imagegalleryapp/image/image8.jpg",
            "src/main/resources/com/example/imagegalleryapp/image/image9.jpg",
            "src/main/resources/com/example/imagegalleryapp/image/image10.jpg"
    };

    private ImageView fullImageView;
    private final List<ImageView> thumbnails = new ArrayList<>();
    private int currentIndex = -1;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Thumbnail pane
        FlowPane thumbnailPane = new FlowPane();
        thumbnailPane.setAlignment(Pos.CENTER);
        thumbnailPane.setHgap(10);
        thumbnailPane.setVgap(10);

        // Populate thumbnail pane
        for (String imagePath : imagePaths) {
            ImageView thumbnail = createThumbnail(imagePath);
            thumbnail.setOnMouseClicked(event -> showFullImage(thumbnails.indexOf(thumbnail)));
            thumbnailPane.getChildren().add(thumbnail);
            thumbnails.add(thumbnail);
        }

        // Full image view
        fullImageView = new ImageView();
        fullImageView.setFitWidth(600);
        fullImageView.setFitHeight(400);
        fullImageView.setPreserveRatio(true);
        fullImageView.setVisible(false);

        // Navigation controls
        HBox navigationBar = new HBox(10);
        navigationBar.setAlignment(Pos.CENTER);
        Text previousText = new Text("Previous");
        Text nextText = new Text("Next");
        previousText.setOnMouseClicked(event -> showPreviousImage());
        nextText.setOnMouseClicked(event -> showNextImage());
        navigationBar.getChildren().addAll(previousText, nextText);

        // Add thumbnail pane, full image view, and navigation controls to root layout
        root.getChildren().addAll(thumbnailPane, fullImageView, navigationBar);

        // Set up and show the scene
        File f = new File("styles.css");
        Scene scene = new Scene(root);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("C:\\Users\\USER\\IdeaProjects\\ImageGalleryApp\\src\\main\\resources\\styles.css" + f.getAbsolutePath().replace("\\", "/"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Gallery");
        primaryStage.show();
    }

    // Method to create thumbnail ImageView
    private ImageView createThumbnail(String imagePath) {
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("thumbnail");
        return imageView;
    }

    // Method to display full-size image
    private void showFullImage(int index) {
        if (index >= 0 && index < thumbnails.size()) {
            fullImageView.setImage(thumbnails.get(index).getImage());
            fullImageView.setVisible(true);
            currentIndex = index;
        }
    }

    // Method to show previous image
    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
            showFullImage(currentIndex);
        }
    }

    // Method to show next image
    private void showNextImage() {
        if (currentIndex < thumbnails.size() - 1) {
            currentIndex++;
            showFullImage(currentIndex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
