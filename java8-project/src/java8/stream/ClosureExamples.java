package java8.stream;

/**
 * Created by Albert-IM on 8/24/16.
 *
 * [ Closure TEST ]
 *      Lambda에서는 정해진 scope이 없기 때문에 클래스의 멤버메소드, 변수들을 사용할수 있다.
 *      Anonymous class에서는 이름이 동일한 메소드가 있으면 파라미터를 다르게 하더라도 shadowing이 생겨서 구분하지 인식하지 못한다.
 */
public class ClosureExamples {

    private int number = 999;

    public static void main(String[] args) {

//        testClosure("Anonymous Class", new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(number);
//            }
//        });
//
//        testClosure("Lambda Expression", () -> System.out.println(number));

        new ClosureExamples().test1();
        new ClosureExamples().test2();
    }

    private static void testClosure(final String name, final Runnable runnable) {
        System.out.println("=====================================");
        System.out.println(name + ": ");
        runnable.run();
        System.out.println("=====================================");
    }

    private void test() {

        // java8 에서는 final 이 아니어도 anonymous class 에서 접근 가능(final, effectively final 이면된다. 변수를 생성하고 수정하지 않으면 됨.)
        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
            }
        });

        testClosure("Lambda Expression", () -> System.out.println(number));
    }


    // 멤버변수 / 지역변수 했을때 어떻게 되는지 테스트
    private void test1() {

        // java8 에서는 final 이 아니어도 anonymous class 에서 접근 가능(final, effectively final 이면된다. 변수를 생성하고 수정하지 않으면 됨.)
        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(ClosureExamples.this.number);
//                System.out.println(this.number); // Runnable에 number가 없기때문에 에러
            }
        });

        testClosure("Lambda Expression", () -> System.out.println(this.number));
//        testClosure("Lambda Expression", () -> System.out.println(this.number)); // Lambda에서는 ClosureExamples.this.number를 한것과 같음.
    }


    // toString 했을때 어떤값이 나오는지 테스트
    private void test2() {
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println("this.toString(): " + this.toString());
            }
        });

        testClosure("Anonymous Class2", new Runnable() {
            @Override
            public void run() {
                System.out.println("ClosureExamples.this.toString(): " + ClosureExamples.this.toString());
            }
        });

        testClosure("Lambda Expression", () -> System.out.println("this.toString(): " + this.toString()));
    }

    // 멤버 메소드에 접근
    private void test3() {
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                // anonymous class가 가지고 있는 메소드(상속한 메소드 포함)와 이름이 동일한 외부 메소드에 접근할 경우 shadowing이 발생한다.
//                System.out.println("ClosureExamples.this.toString(): " + toString("Test")); // 에러

                System.out.println("ClosureExamples.this.toString(): " + ClosureExamples.this.toString("Test"));  // 이렇게 사용해야함
            }
        });

        testClosure("Lambda Expression", () -> System.out.println("this.toString(): " + toString("Test")));
    }

    // 내부 변수 지정
    private void test4() {
        int number = 100;

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                int number = 50; // no compile-time error
                System.out.println(number);
            }
        });

        /**
         * Enclosing class (class 를 둘러싼다.)
         *
         * 확장이 되어버려서 이미 number가 정의 되어있다고 나옴.
         */
        testClosure("Lambda Expression", () -> {
//            int number = 50; // compile-time error
            System.out.println(number);
        });
    }


    @Override
    public String toString() {
        return new StringBuilder("ClosureExamples{")
                .append("number=")
                .append(number)
                .append("}")
                .toString();

    }

    public static <T> String toString(T value) {
        return "The value is " + String.valueOf(value) + ".";
    }
}
