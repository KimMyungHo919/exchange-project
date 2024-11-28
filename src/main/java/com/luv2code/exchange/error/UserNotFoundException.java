package com.luv2code.exchange.error;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super("해당 아이디를 찾을 수 없습니다. 아이디를 다시 확인해주세요.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
