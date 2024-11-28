package com.luv2code.exchange.error;

public class DataNotFoundException extends CustomException {

    public DataNotFoundException() {
        super("해당 데이터를 찾을 수 없습니다.");
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
