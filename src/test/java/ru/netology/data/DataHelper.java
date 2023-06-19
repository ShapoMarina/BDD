package ru.netology.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;

    }

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static int validAmount(int balance) {
        return new Random().nextInt(balance) + 1;
    }

    public static int invalidAmount(int balance) {
        return Math.abs(balance) + new Random().nextInt(10000);
    }

    @Value
    public static class Card {
        private String CardNumber;
    }

    public static Card getFirstCard() {
        return new Card("5559 0000 0000 0001");
    }

    public static Card getSecondCard() {
        return new Card("5559 0000 0000 0002");
    }
}