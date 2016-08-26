package java8.stream;

import java.math.BigDecimal;

/**
 * Created by Albert-IM on 8/18/16.
 */
public class CustomFunctionalInterfaceExamples {

    public static void main(String[] args) {
        BigDecimalToCurrency bigDecimalToCurrency = bd -> "$" + bd.toString();

        System.out.println(bigDecimalToCurrency.toCurrency(new BigDecimal("120.00")));

        final InvalidFunctionalInterface annoymousClass = new InvalidFunctionalInterface() {
            @Override
            public <T> String mkString(T value) {
                return value.toString();
            }
        };

        System.out.println("anonymous class : " + annoymousClass.mkString(123));

//        final InvalidFunctionalInterface invalidFunctionalInterface = s -> "hello" + s;
//        System.out.println(invalidFunctionalInterface.mkString(123));
    }

}


@FunctionalInterface
interface BigDecimalToCurrency {
    String toCurrency(BigDecimal value);
}


@FunctionalInterface
interface InvalidFunctionalInterface {
    <T> String mkString(T value);
}