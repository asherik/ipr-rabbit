package com.consumer.helpers;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class Helper {
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static boolean getImitateWorkRandomResult() {
        //random from 1000 to 7000
        int randomSleep = (int) (Math.random() * 7000 + 1000);
        sleep(randomSleep);
        //return random result
        boolean randomResult = Math.random() < 0.5;
        log.info("Got randomSleep: {}, randomResult: {}", randomSleep, randomResult);
        randomException();
        return Math.random() < 0.5;
    }

    public static void randomException() {
        int randomFirstInt = getRandomNumberInRange(0, 1);
        int randomTwoInt = getRandomNumberInRange(0, 1);
        int res = randomFirstInt / randomTwoInt;
    }

    public static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }
}
