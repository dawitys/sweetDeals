package java8.stream.methodreference;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Created by Albert-IM on 8/25/16.
 */
public class MethodReferenceExamples {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(System.out::println);
//                .forEach(i -> System.out.println(i)); // 파라미터를 그대로 전달할때

        // 클래스 메소드를 사용한 메소드 레퍼런스
        System.out.println(
                Arrays.asList(new BigDecimal("10.0"), new BigDecimal("23"), new BigDecimal("5"))
                        .stream()
                        .sorted(BigDecimalUtil::compare)
                        .collect(toList())
        );

        // 인스턴스 메소드를 사용한 메소드 레퍼런스
        System.out.println(
                Arrays.asList(new BigDecimal("10.0"), new BigDecimal("23"), new BigDecimal("5"))
                        .stream()
                        .sorted(BigDecimal::compareTo)
                        .collect(toList())
        );

        // 특정 객체를 이용한 메소드 레퍼런스
        final String targetString = "c";
        System.out.println(
                Arrays.asList("a", "b", "c", "d")
                        .stream()
//                        .anyMatch(x -> x.equals("c"))
                        .anyMatch(targetString::equals)
//                        .anyMatch("c"::equals)
        );

        System.out.println("\n================================================");
        System.out.println("\nmethodReference03()");
        System.out.println("\n------------------------------------------------");
        methodReference03();
    }



    private static void methodReference03() {
        /* First Class Function */

        /**
         * Function can be passed as a parameter to another function.
         */

        // Using Lambda Expression
        System.out.println(
                testFirstClassFunction1(3, i -> String.valueOf(i * 2))
        );

        // Using Method Reference
        System.out.println(
                testFirstClassFunction1(3, MethodReferenceExamples::doubleThenToString)
        );

        /**
         * A function can be returned as the result of another function.
         */
        // Using Lambda Expression
        final Function<Integer, String> fl = getDoubleThenToStringUsingLambdaExpression();
        System.out.println(fl.apply(3));


        // Using Method Reference
        final Function<Integer, String> frm = getDoubleThenToStringUsingMethodReference();
        System.out.println(frm.apply(3));

        System.out.println("\n------------------------------------------------");
        /**
         * A function can be stored in the data structure.
         */
        // Using Lambda Expression
        final List<Function<Integer, String>> fsL = Arrays.asList(i -> String.valueOf(i * 2));
        for(Function<Integer, String> f : fsL) {
            final String result = f.apply(3);
            System.out.println(result);
        }

        // Using Method Reference
        final List<Function<Integer, String>> fsMr = Arrays.asList(MethodReferenceExamples::doubleThenToString);
        for(Function<Integer, String> f : fsMr) {
            final String result = f.apply(3);
            System.out.println(result);
        }

        System.out.println("\n------------------------------------------------");
        // Using Lambda Expression
        final Function<Integer, String> fl2 = i -> String.valueOf(i * 2);
        final String resultFl2 = fl2.apply(5);
        System.out.println(resultFl2);

        // Using Method Reference
        final Function<Integer, String> fmr2 = MethodReferenceExamples::doubleThenToString;
        final String resultFmr2 = fmr2.apply(5);
        System.out.println(resultFmr2);


        System.out.println("\n------------------------------------------------");
        /**
         * Both Lambda Expression and Method Reference
         */
        List<Function<Integer, String>> fsBoth = Arrays.asList(
                i -> String.valueOf(i * 2),
                MethodReferenceExamples::doubleThenToString,
                MethodReferenceExamples::addHashPrefix
        );

        for(final Function<Integer, String> f : fsBoth) {
            final String result = f.apply(7);
            System.out.println(result);
        }
    }

    private static String doubleThenToString(int i) {
        return String.valueOf(i * 2);
    }

    private static String addHashPrefix(int number) {
        return "#" + number;
    }

    private static String testFirstClassFunction1(int n, Function<Integer, String> f) {
        return "The result is " + f.apply(n);
    }

    private static Function<Integer, String> getDoubleThenToStringUsingLambdaExpression() {
        return i -> String.valueOf(i * 2);
    }

    private static Function<Integer, String> getDoubleThenToStringUsingMethodReference() {
        return MethodReferenceExamples::doubleThenToString;
    }
}

class BigDecimalUtil {
    public static int compare(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2);
    }
}
