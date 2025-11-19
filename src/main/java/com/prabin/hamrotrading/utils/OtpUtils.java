package com.prabin.hamrotrading.utils;

import java.util.Random;

public class OtpUtils {

    public static String generateOTP() {
        int otpLength = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
