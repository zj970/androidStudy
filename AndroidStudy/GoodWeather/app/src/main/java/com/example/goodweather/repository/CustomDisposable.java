package com.example.goodweather.repository;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @auther zj970
 * @create 2023-06-03 上午12:34
 */
public class CustomDisposable {

    private static final CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * Flowable
     * @param flowable
     * @param consumer
     * @param <T>
     */
    public static <T> void addDisposable(Flowable<T> flowable, Consumer<T> consumer) {
        compositeDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer));
    }

    /**
     * Completable
     * @param completable
     * @param action
     * @param <T>
     */
    public static <T> void addDisposable(Completable completable, Action action) {
        compositeDisposable.add(completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action));
    }
}
