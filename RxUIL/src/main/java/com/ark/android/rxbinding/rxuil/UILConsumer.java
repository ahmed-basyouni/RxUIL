package com.ark.android.rxbinding.rxuil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * Created by Ark on 6/9/2017.
 */

public abstract class UILConsumer implements Observer<RxUILObservableBuilder.RXUILObject> {
    @Override
    public void onSubscribe(Disposable d) {}

    @Override
    public void onComplete() {}
}
