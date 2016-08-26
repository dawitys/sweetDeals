package java8.stream;

import java.util.Arrays;

/**
 * Created by Albert-IM on 8/25/16.
 */
public class MethodReferenceExamples {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(System.out::println);
//                .forEach(i -> System.out.println(i));
    }
}
