package controller;


import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.SortEvent;
import model.Gallery;
import model.Photo;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import util.PhotoDownloader;

public class GalleryController {

    @FXML
    private TextField imageNameField;
    @FXML
    private ImageView imageView;
    @FXML
    private ListView<Photo> imagesListView;
    @FXML
    private TextField searchTextField;
    private Gallery galleryModel;

    @FXML
    public void initialize() {
        imagesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Photo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView photoIcon = new ImageView(item.getPhotoData());
                    photoIcon.setPreserveRatio(true);
                    photoIcon.setFitHeight(50);
                    setGraphic(photoIcon);
                }
            }
        });
        imagesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {if(newValue != null) bindSelectedPhoto(newValue);});
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
        imagesListView.setItems(gallery.getPhotos());
        imagesListView.getSelectionModel().select(0);
    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        imageNameField.textProperty().bind(selectedPhoto.nameProperty());
        imageView.imageProperty().bind(selectedPhoto.getPhotoProperty());
    }

    public void searchButtonClicked(ActionEvent event) {
        PhotoDownloader photoDownloader = new PhotoDownloader();
        galleryModel.clear();
        photoDownloader.searchForPhotos(searchTextField.getText())
                .subscribeOn(Schedulers.io())
                .subscribe(photo -> {
                    Platform.runLater(() -> galleryModel.addPhoto(photo));
                }, throwable -> {
                    System.out.println("ERROR " + throwable.getMessage());
                });
    }
}

