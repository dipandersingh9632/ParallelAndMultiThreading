package com.learnjava.thread;

class MyThreadRunnable1 implements Runnable{

    @Override
    public void run() {
        for(int i = 0; i < 100; i++) System.out.println("I am in thread 1");
    }
}

class MyThreadRunnable2 implements Runnable{

    @Override
    public void run() {
        for(int i = 0; i < 100; i++) System.out.println("I am in thread 2");
    }
}

public class ThreadRunnableExample {
    public static void main(String[] args) {
        MyThreadRunnable1 bullet1 = new MyThreadRunnable1();
        MyThreadRunnable2 bullet2 = new MyThreadRunnable2();

        // Since we have created the bullet we have to create the Gun also
        Thread gun1 = new Thread(bullet1);
        Thread gun2 = new Thread(bullet2);

        // NOw you have load now fire
        gun1.start();
        gun2.start();
    }
}
