package com.mechamic38.barattus.util;

import java.util.Objects;

/**
 * Base result class to transfer objects, as well as report issues.
 *
 * @param <T> Result type
 */
public class Result<T> {

    T result;
    String error = "";

    protected Result() {
    }

    private Result(T result) {
        this.result = result;
    }

    private Result(String error) {
        this.error = error;
    }

    public static <T> Result<T> success(T result) {
        return new Result<>(result);
    }

    public static <T> Result<T> error(String error) {
        return new Result<>(error);
    }

    public boolean isSuccess() {
        return !Objects.isNull(result);
    }

    public boolean isError() {
        return !error.isEmpty();
    }

    public T getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}
