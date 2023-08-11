package com.aswagh.OrderService.external.decoder;

import com.aswagh.OrderService.exceptions.CustomException;
import com.aswagh.OrderService.external.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("Error URL {} ", response.request().url());
        log.info("Headers {} ", response.request().headers());

        try {
            ErrorResponse errorResponse =
                    objectMapper.readValue(
                            response.body().asInputStream(),
                            ErrorResponse.class);

            throw new CustomException(errorResponse.getMessage(),
                    errorResponse.getErrorCode(),
                    response.status());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error (ProductService)",
                    "INTERNAL_SERVER_ERROR",500);
        }
    }
}
