package java8.stream;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by Albert-IM on 8/23/16.
 *
 *
 * Parallel Stream 의 성능
 *      - 간단한 연산에서 사용하면 오히려 나누고 합치고 하는 시간들이 오래 걸린다.
 *      - Stream이 StreamExamples3에서 설명한 Intermediate Operation Method(중간메소드) 과정을 거치고 계산하는 과정이 복잡해질때 parallel stream이 빛을 발한다.
 *          sequential 계산에서는 1second * n 으로 계산
 *          parallel 계산에서는   1second * (n / core 갯수) 로 계산
 *
 *
 * Test Code
 * # parallel이 단순 연산에서 느리다.
 * # 딜레이를 적용하니 parallel이 10배정도 빨라졌다.
 *
 *
 */

public class StreamExamples6 {

    private static void slowDown() {
        try {
            TimeUnit.MILLISECONDS.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for(long i = 0; i <= n; i++) {
            result += i;
            slowDown();
        }

        return result;
    }

    public static long sequentialSum(long n) {
//        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();

        // 딜레이 걸어주기
        return Stream.iterate(1L, i -> i + 1).limit(n).peek(i -> slowDown()).reduce(Long::sum).get();
    }

    public static long parallelSum(long n) {
//        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();

        // 딜레이 걸어주기
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().peek(i -> slowDown()).reduce(Long::sum).get();
    }

    public static long rangedSum(long n) {
//        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();

        // 딜레이 걸어주기
        return LongStream.rangeClosed(1, n).peek(i -> slowDown()).reduce(Long::sum).getAsLong();

    }

    public static  long parallelRangedSum(long n) {
//        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();

        // 딜레이 걸어주기
        return LongStream.rangeClosed(1, n).parallel().peek(i -> slowDown()).reduce(Long::sum).getAsLong();
    }

    public static void main(String[] args) {
        final long n = 1000;

        // 가우스 방법(단순연산에서 알고리즘이 좋으므로 속도가 엄청나다. 따라잡을수가 없음)
        final long start = System.currentTimeMillis();
        System.out.println((1 + n) * (n / 2));
        System.out.println((System.currentTimeMillis() - start) + " ms\n");


        final long start1 = System.currentTimeMillis();
        System.out.println("    iterativeSum(n): " + iterativeSum(n));
        System.out.println("                     " + (System.currentTimeMillis() - start1) + " ms\n");

        final long start2 = System.currentTimeMillis();
        System.out.println("    sequentialSum(n): " + sequentialSum(n));
        System.out.println("                     " + (System.currentTimeMillis() - start2) + " ms\n");

        final long start3 = System.currentTimeMillis();
        System.out.println("      parallelSum(n): " + parallelSum(n));
        System.out.println("                     " + (System.currentTimeMillis() - start3) + " ms\n");

        final long start4 = System.currentTimeMillis();
        System.out.println("        rangedSum(n): " + rangedSum(n));
        System.out.println("                     " + (System.currentTimeMillis() - start4) + " ms\n");

        final long start5 = System.currentTimeMillis();
        System.out.println("parallelRangedSum(n): " + parallelRangedSum(n));
        System.out.println("                     " + (System.currentTimeMillis() - start5) + " ms\n");
    }
}
