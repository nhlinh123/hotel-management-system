package com.ptit.hotelmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponseModel<T> {
    private int status;
    private String message;
    private T data;

    public static <T> BaseResponseModel<T> success(T data) {
        return BaseResponseModel.<T>builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> BaseResponseModel<T> success(T data, String message) {
        return BaseResponseModel.<T>builder()
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseResponseModel<T> created(T data) {
        return BaseResponseModel.<T>builder()
                .status(201)
                .message("Created")
                .data(data)
                .build();
    }

    public static <T> BaseResponseModel<T> created(T data, String message) {
        return BaseResponseModel.<T>builder()
                .status(201)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseResponseModel<T> badRequest(String message) {
        return BaseResponseModel.<T>builder()
                .status(400)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> BaseResponseModel<T> unauthorized(String message) {
        return BaseResponseModel.<T>builder()
                .status(401)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> BaseResponseModel<T> forbidden(String message) {
        return BaseResponseModel.<T>builder()
                .status(403)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> BaseResponseModel<T> notFound(String message) {
        return BaseResponseModel.<T>builder()
                .status(404)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> BaseResponseModel<T> error(String message) {
        return BaseResponseModel.<T>builder()
                .status(500)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> BaseResponseModel<T> error(int status, String message) {
        return BaseResponseModel.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }
}
