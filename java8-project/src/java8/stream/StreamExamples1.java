package java8.stream;

import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Albert-IM on 8/22/16.
 */
public class StreamExamples1 {
    public static void main(String[] args) {
        // range - (start, end) end 미만 범위 ex) 0 1 2 3 4 5 6 7 8 9
        IntStream.range(0, 10).forEach(i -> System.out.print(i + " "));

        // rangeClosed - (start, end) end 이하 범위 ex) 1 2 3 4 5 6 7 8 9 10
        IntStream.rangeClosed(1, 10).forEach(i -> System.out.print(i + " "));

//        IntStream.iterate(1, i -> i + 1)
//                .forEach(i -> System.out.println(i + " "));
//
//        Stream.iterate(BigInteger.ONE, i -> i.add(BigInteger.ONE))
//                .forEach(i -> System.out.print(i + " "));
    }
}
