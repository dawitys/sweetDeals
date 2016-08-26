package java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Albert-IM on 8/22/16.
 */
public class StreamPrelude {
    public static void main(String[] args) {

        final int abs1 = Math.abs(-1);
        final int abs2 = Math.abs(1);

        System.out.println("abs1 : " + abs1);
        System.out.println("abs2 : " + abs2);
        System.out.println("abs1 == abs2 is " + (abs1 == abs2));

        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        final int minInt = Math.abs(Integer.MIN_VALUE);
        System.out.println("minInt : " + minInt);

        final List<Integer> numberes = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println("\nmapOld(numbers, i -> i * 2)");
        System.out.println(
                mapOld(numberes, i -> i * 2)
        );

        System.out.println("\nmap(numbers, i -> i)");
        System.out.println(
                map(numberes, i -> i)
        );
        System.out.println("map(numbers, Function.identity())");
        System.out.println(
                map(numberes, Function.identity())
        );
    }

    private static <T, R> List<R> map(final List<T> list, final Function<T, R> mapper) {
        final List<R> result =  new ArrayList<R>();
        for(final T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }

    private static <T, R> List<R> mapOld(final List<T> list, final Function<T, R> mapper) {
        final List<R> result =  new ArrayList<R>();
        for(T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }
}
