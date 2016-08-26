package java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Albert-IM on 8/22/16.
 */
public class StreamExamples2 {
    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5)
                .forEach(i -> System.out.print(i + " "));

        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println("");

        /**
         * Java8 - Stream 방식
         *
         *
         * Console Result (순차적으로 비교하고 조건이 맞으면 바로 다음조건을 바로 실행, 다시 조건이 안맞으면 진행하던 부분에서 이어서 다시 진행)
         number > 3
         number > 3
         number > 3
         number > 3
         number < 9
         number * 2
         number > 10
         number > 3
         number < 9
         number * 2
         number > 10
         number > 3
         number < 9
         number * 2
         number > 10
         */
//        System.out.println("\nFunctional Result : " +
//                numbers.stream()
//                        .filter(number -> {
//                            System.out.println("number > 3");
//                            return number > 3;
//                        })
//                        .filter(number -> {
//                            System.out.println("number < 9");
//                            return number < 9;
//                        })
//                        .map(number -> {
//                            System.out.println("number * 2");
//                            return number * 2;
//                        })
//                        .filter(number -> {
//                            System.out.println("number > 10");
//                            return number > 10;
//                        })
//                        .findFirst()
//        );
        /**
         * Stream 방식
         */
        System.out.println("\nFunctional Result : " +
                numbers.stream()
                        .filter(number -> number > 3)
                        .filter(number -> number < 9)
                        .map(number -> number * 2)
                        .filter(number -> number > 10)
                        .findFirst()
        );


        /**
         * 예전방식
         */
        Integer result = null;
        // 3보다 크고 9보다 작은수에 2를 곱하고 그중에 10보다 큰수의 첫번째 수는?
        for(final Integer number : numbers) {
            if(number > 3 && number < 9) {
                final Integer newNumber = number * 2;
                if(newNumber > 10) {
                    result = newNumber;
                    break;
                }
            }
        }

        System.out.println("Imperative Result : " + result);


//        final List<Integer> result = new ArrayList<>();
//
//        for(final Integer number : numbers) {
//            if(number > 3 && number < 9) {
//                final Integer newNumber = number * 2;
//                result.add(newNumber);
//            }
//        }
//
//        System.out.println("\nImperative Result : " + result);
    }
}
