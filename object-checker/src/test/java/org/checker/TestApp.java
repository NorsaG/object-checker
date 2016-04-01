package org.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: LazarevEvgeny
 * Date: 29.03.2016
 */
public class TestApp {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start test app");
        Random r = new Random(1000000);
        long t = System.currentTimeMillis();
        System.out.println("Start time - " + t);
        List<TestObject> testObjectList = new ArrayList<TestObject>();
        while (true) {
            Thread.sleep(5000);
            System.out.println("Some value - " + r.nextInt() + ",  real delay " + (System.currentTimeMillis() - t));
            t = System.currentTimeMillis();
            testObjectList.add(new TestObject("Some value - " + r.nextInt()));
        }
    }
}


