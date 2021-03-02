package com.inyaa.web.exception;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * @author: yuxh
 * @date: 2021/3/2 22:12
 */
public class CustomErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) {
        return true;
    }

    @Override
    public void handleError(ClientHttpResponse response) {

    }
}
