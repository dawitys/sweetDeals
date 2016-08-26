package java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Created by Albert-IM on 8/22/16.
 */
public class StreamExamples3 {

    /**
     * Stream 에는 두가지 메소드 타입이 있다.
     * 1. Intermediate Operation Method(중간메소드) - Stream을 리턴하기 때문에 계속 Method Chaining을 통해서 무엇을 해야할지 스트림에게 지시할수 있다.
     *      Stream을 리턴하면 Intermediate Operation Method
     *
     * 2. Terminal Operation Method(종료메소드) - 메소드가 호출되면 'Intermediate Operation Method' 를 처리하고 결과값을 리턴
     *      Optional, long, int, void 등을 리턴하면 Terminal Operation Method
     *
     * stream.filter(i -> i > 10)   # Intermediate Operation Method(중간메소드)
     *      .map(i -> i * 2)        # Intermediate Operation Method(중간메소드)
     *      .findFirst              # Terminal Operation Method(종료메소드)
     */


    public static void main(String[] args) {

        System.out.println("collect(Collectors.toList()): " +
            Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(toList())
        );

        System.out.println("collect(toSet()):  " +
            Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(toSet())
        );

        System.out.println("collect(joining(\", \")):  " +
            Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .collect(joining(", "))
        );

        System.out.println("collect(joining(\", \", \"[\", \"]\"):  " +
            Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .distinct()
                .collect(joining(", ", "[", "]"))
        );

        System.out.println(".distinct().collect(toList()): " +
            Stream.of(1, 3, 3, 5, 5)
                .filter(i -> i > 2)
                .map(i -> i * 2)
                .map(i -> "#" + i)
                .distinct()
                .collect(toList())
        );


        final Integer integer3 = 3;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5)
                        .filter(i -> i == integer3)
                        .findFirst()
        );

        final Integer integer127 = 127;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 127)
                        .filter(i -> i == integer127)
                        .findFirst()
        );

        final Integer integer128 = 128;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i == integer128)
                        .findFirst()
        );

        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i.equals(integer128))
                        .findFirst()
        );

        System.out.println(".filter(i -> i > integer3).count(): " +
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i > integer3)
                        .count()
        );


        System.out.println("");
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        // External Iterator (외부 forEach)
        for (Integer i : numbers) {
            System.out.print("Exteranl Iterator = " + i + " ");
        }

        System.out.println("");
        // internal Iterator (내부 forEach)
        Stream.of(1, 2, 3, 4, 5)
                .forEach(i -> System.out.print("Internal Iterator = " + i + " "));
    }
}

