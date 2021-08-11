package com.wzn.java8test;

public class Java8Tester {

    public static void main(String[] args) {
        Java8Tester tester = new Java8Tester();

        MathOperation addition = (int a, int b) -> a + b;

        MathOperation subtraction = (a, b) -> a - b;

        MathOperation addition2 = new MathOperation() {
            @Override
            public int operation(int a, int b) {
                return a + b + a + b;
            }
        };

        MathOperation multiplication = (int a, int b) -> {return a * b; };

        MathOperation division = (a,b) -> a / b;

        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 + 5 = " + tester.operate(10, 5, addition2));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        GreetingService gs1 = message -> System.out.println("hello" + message);
        GreetingService gs2 = (message) -> System.out.println("hello" + message);
        gs1.sayMessage("weizenan");
        gs2.sayMessage("panli");
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }
}
