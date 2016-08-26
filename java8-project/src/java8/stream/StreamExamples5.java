package java8.stream;

/**
 * Created by Albert-IM on 8/23/16.
 */

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Stream을 이용한 parallel programming(병렬 프로그래밍)
 */
public class StreamExamples5 {

    public static void main(String[] args) {

        final int[] sum = {0};
        IntStream.range(0, 100)
                .forEach(i -> sum[0] += i);
        System.out.println("            sum (side-effect): " + sum[0]);

        final int[] sum2 = {0};
        IntStream.range(0, 100)
                .parallel()
                .forEach(i -> sum2[0] += i);

        System.out.println("   parallel sum (side-effect): " + sum2[0]);

        System.out.println("  stream sum (no side-effect): " +
                IntStream.range(0, 100)
                        .sum()
        );

        System.out.println("parallel sum (no side-effect): " +
                IntStream.range(0, 100)
                        .parallel()
                        .sum()
        );


//        System.out.println("\n==========================================");
//        System.out.println("Stream");
//        final long start = System.currentTimeMillis();
//        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
//                .stream()
//                .map(i -> {
//
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    return i;
//                })
//                .forEach(i -> System.out.println(i));
//        System.out.println(System.currentTimeMillis() - start);


        /**
         * 코어 갯수 조절하기
         *
         * 내 컴퓨터는 쿼드코어(4) 라서 4개의 엘리먼트로 테스트!
         */
        System.out.println("\n==========================================");
        System.out.println("Parallel Stream (4 elements)");
        final long start2 = System.currentTimeMillis();
        Arrays.asList(1, 2, 3, 4)
                .parallelStream()
                .map(i -> {

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return i;
                })
                .forEach(i -> System.out.println(i));
        System.out.println(System.currentTimeMillis() - start2);

        System.out.println("\n==========================================");
        System.out.println("Parallel Stream (5 elements)");
        final long start3 = System.currentTimeMillis();
        Arrays.asList(1, 2, 3, 4, 5)
                .parallelStream()
                .map(i -> {

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return i;
                })
                .forEach(i -> System.out.println(i));
        System.out.println(System.currentTimeMillis() - start3);


        // 코어 관리 - 코어 갯수 : 사용하고자하는 코어갯수 - 1  ex) 8코어 쓰고싶으면 7로 설정
        System.out.println("\n==========================================");
        System.out.println("Parallel Stream (4 element) with parallelism: 0");
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "0");
        final long start4 = System.currentTimeMillis();
        Arrays.asList(1, 2, 3, 4)
                .parallelStream()
                .map(i -> {

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return i;
                })
                .forEach(i -> System.out.println(i));
        System.out.println(System.currentTimeMillis() - start4);
    }
}
