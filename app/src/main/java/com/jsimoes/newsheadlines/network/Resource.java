package com.jsimoes.newsheadlines.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by admin on 29/01/2021.
 */
public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final Throwable serverError;

    public Resource(@NonNull Status status, @Nullable T data,
                    @Nullable Throwable serverError) {
        this.status = status;
        this.data = data;
        this.serverError = serverError;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Throwable serverError, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, serverError);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }
    
    public enum Status {SUCCESS, ERROR, LOADING}
}
