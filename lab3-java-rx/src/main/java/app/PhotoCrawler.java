package app;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Photo;
import model.PhotoSize;
import util.PhotoDownloader;
import util.PhotoProcessor;
import util.PhotoSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoCrawler {

    private static final Logger log = Logger.getLogger(PhotoCrawler.class.getName());

    private final PhotoDownloader photoDownloader;

    private final PhotoSerializer photoSerializer;

    private final PhotoProcessor photoProcessor;

    public PhotoCrawler() throws IOException {
        this.photoDownloader = new PhotoDownloader();
        this.photoSerializer = new PhotoSerializer("./photos");
        this.photoProcessor = new PhotoProcessor();
    }

    public void resetLibrary() throws IOException {
        photoSerializer.deleteLibraryContents();
    }

//    public void downloadPhotoExamples() {
//        try {
//            List<Photo> downloadedExamples = photoDownloader.getPhotoExamples();
//            for (Photo photo : downloadedExamples) {
//                photoSerializer.savePhoto(photo);
//            }
//        } catch (IOException e) {
//            log.log(Level.SEVERE, "Downloading photo examples error", e);
//        }
//    }
    public void downloadPhotoExamples() {
        photoDownloader.getPhotoExamples()
                .compose(this::processPhotos)
                .subscribe(photoSerializer::savePhoto);
    }

    public void downloadPhotosForQuery(String query) throws IOException {
        photoDownloader.searchForPhotos(query)
                .compose(this::processPhotos)
                .subscribe(photoSerializer::savePhoto,
                    throwable -> log.log(Level.SEVERE, "Could not download a photo", throwable));
    }

    public void downloadPhotosForMultipleQueries(List<String> queries) {
        photoDownloader.searchForPhotos(queries)
                .compose(this::processPhotos)
                .subscribe(photoSerializer::savePhoto);
    }
    public Observable<Photo> processPhotos(Observable<Photo> photos) {
        return photos
                .groupBy(PhotoSize::resolve)
                .flatMap(grouped -> {
                    switch (Objects.requireNonNull(grouped.getKey())) {
                        case SMALL:
                            return grouped.ignoreElements().toObservable();
                        case MEDIUM:
                            return grouped.buffer(5, TimeUnit.SECONDS)
                                    .flatMapIterable(bufferedPhotos -> bufferedPhotos)
                                    .doOnNext(photoSerializer::savePhoto);
                        case LARGE:
                            return grouped.observeOn(Schedulers.computation())
                                    .map(photo -> {
                                        try {
                                            return photoProcessor.convertToMiniature(photo);
                                        } catch (IOException e) {
                                            log.log(Level.WARNING, "Could not convert photo to miniature", e);
                                            return photo;
                                        }
                                    })
                                    .doOnNext(photoSerializer::savePhoto);
                        default:
                            return Observable.empty();
                    }
                });
    }
}
