package java8.stream;

/**
 * Created by Albert-IM on 8/24/16.
 *
 * [ Anonymous Class ]
 *      Anonymous Class 갯수만큼 컴파일시에 임의로 클래스를 생성한다.
 *
 * [ Lambda ]
 *      컴파일시에 클래스가 생성되지 않는다.
 */
public class ClosureExamples2 {
    private int number = 999;

    public static void main(String[] args) {
        new ClosureExamples2().test();
    }

    private void test() {
//        int number = 100;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
            }
        };
        runnable.run();

        Runnable runnable1 = () -> System.out.println(number);
        runnable1.run();
    }
}
