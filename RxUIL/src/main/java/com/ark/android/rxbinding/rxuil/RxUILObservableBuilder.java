package com.ark.android.rxbinding.rxuil;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * Created by Ark on 6/9/2017.
 */

public final class RxUILObservableBuilder extends Observable<RxUILObservableBuilder.RXUILObject> {

    private String imageUri;
    private ImageView view;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader;

    public RxUILObservableBuilder preview(@NonNull ImageView imageView) {
        this.view = imageView;
        return this;
    }

    public RxUILObservableBuilder url(@NonNull String url) {
        this.imageUri = url;
        return this;
    }

    public RxUILObservableBuilder displayOptions(@NonNull DisplayImageOptions displayImageOptions) {
        this.displayImageOptions = displayImageOptions;
        return this;
    }

    public RxUILObservableBuilder imageLoader(@NonNull ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public static RxUILObservableBuilder builder() {
        return new RxUILObservableBuilder();
    }

    @Override
    protected void subscribeActual(Observer<? super RXUILObject> observer) {
        Listener listener = new Listener(observer);
        observer.onSubscribe(listener);
    }

    private class Listener implements Disposable {

        private final Observer<? super RXUILObject> observer;

        Listener(Observer<? super RXUILObject> observer) {
            this.observer = observer;
            startDownloadImage();
        }

        private final AtomicBoolean unsubscribed = new AtomicBoolean();

        @Override
        public void dispose() {
            if (unsubscribed.compareAndSet(false, true)) {
                imageLoader.cancelDisplayTask(view);
                observer.onError(new Throwable("Canceled"));
            }
        }

        @Override
        public boolean isDisposed() {
            return unsubscribed.get();
        }

        private void startDownloadImage() {
            if (!isDisposed()) {
                imageLoader.displayImage(imageUri, view, displayImageOptions, new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        if (!isDisposed()) {
                            RXUILObject rxuilObject = new RXUILObject(imageUri, view, loadedImage);
                            observer.onNext(rxuilObject);
                            observer.onComplete();
                        }
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        super.onLoadingFailed(imageUri, view, failReason);
                        if (!isDisposed())
                            observer.onError(failReason.getCause());
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        super.onLoadingCancelled(imageUri, view);
                        observer.onError(new Throwable("Canceled"));
                    }
                });
            }
        }
    }

    public class RXUILObject {
        String imageUri;
        View view;
        Bitmap loadedImage;

        RXUILObject(String imageUri, View view, Bitmap loadedImage) {
            this.imageUri = imageUri;
            this.view = view;
            this.loadedImage = loadedImage;
        }

        public String getImageUri() {
            return imageUri;
        }

        public View getView() {
            return view;
        }

        public Bitmap getLoadedImage() {
            return loadedImage;
        }
    }
}
