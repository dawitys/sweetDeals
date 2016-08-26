package java8.stream;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by Albert-IM on 8/18/16.
 */
public class HelloJava8 {
    public static void main(String[] args) throws Exception {

        System.out.println("Hello InteliJ !");

        /**
         * 1. Function<T,R>
         *
         * @FunctionalInterface
         * R apply(T t);
         *
         * T타입의 파라미터를 넘겨주면 R타입으로 리턴함
         */
        Function<Integer, Integer> add = i -> i + 1;
        System.out.println("10 + 1 = " + add.apply(10));

        /**
         * 2. Consumer<T>
         *
         * @FunctionalInterface
         * void accept(T t);
         *
         * T타입의 파라미터를 넘겨주면 void 형태로 처리함
         */
        Consumer<Integer> consumerVoid = number -> System.out.println("Consumer Test. Value is " + number);
        consumerVoid.accept(10);

        /**
         * 3. Predicate<T>
         *
         * @FunctionalInterface
         * boolean test(T t);
         *
         * T타입의 파리미터를 넘겨주면 boolean 형태로 리턴함
         */

        Predicate<Integer> predicateTest = number -> number <= 10 ? true : false;

        predicatePrint(predicateTest.test(10));
        predicatePrint(predicateTest.test(20));

        /**
         * 4. Supplier<T>
         *
         * @FunctionalInterface
         * T get();
         *
         * T타입의 파라미터를 넘겨주면 T를 리턴함
         */
        Supplier<String> supplierTest = () -> "Hello ";

        System.out.println(supplierTest.get() + "Albert");
    }

    public static void predicatePrint(boolean isSmall) {
        if (isSmall)
            System.out.println("This value is smaller than 10");
        else
            System.out.println("This value is bigger than 10");
    }
}
