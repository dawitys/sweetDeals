package java8.stream;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Albert-IM on 8/23/16.
 *
 * Price set을 생성하고 랜덤으로
 * 상품 여러개 생성 리스트에 넣고 특정 상품 찾아서 total을 구하는 코드
 */
public class StreamExamples6ParallelPerformancePractical {
    private static final String[] priceStrings = {"1.0", "100.99", "35.75", "21.30", "88.00"};
    private static final BigDecimal[] targetPrices = {new BigDecimal("30"), new BigDecimal("20"), new BigDecimal("31")};

    private static final Random random = new Random(123);
    private static final Random targetPriceRandom = new Random(111);

    private static final List<Product> products;

    static {
        final int length = 8_000_000;
        final Product[] list = new Product[length];

        for (int i  = 1; i <= length; i++) {
            list[i - 1] = new Product((long) i, "Product" + i, new BigDecimal(priceStrings[random.nextInt(5)]));
        }

        products = Arrays.asList(list);
    }

    private static BigDecimal imperativeSum(final List<Product> products, final Predicate<Product> predicate) {
        BigDecimal sum = BigDecimal.ZERO;

        for (final Product product : products) {
            if(predicate.test(product)) {
                sum = sum.add(product.getPrice());
            }
        }

        return sum;
    }

    /**
     * Stream<Product> stream - #위험한 코드
     * parallel과 parallelStream 의 성능테스트를 위해 두가지를 모두 사용하기위해 사용한것으로 실제 사용할때는 쓰지 않는것이 좋음.
     * stream은 재사용이 불가능
     */
    private static BigDecimal streamSum(final Stream<Product> stream, final Predicate<Product> predicate) {
        return stream.filter(predicate).map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private  static void imperactiveTest(BigDecimal targetPrice) {
        System.out.println("===========================================================");
        System.out.println("\nImperactive Sum\n------------------------------------------------");
        final long start = System.currentTimeMillis();

        System.out.println("Sum: " +
            imperativeSum(products, product -> product.getPrice().compareTo(targetPrice) >= 0)
        );

        System.out.println("It took " + (System.currentTimeMillis() - start) + " ms.");
    }

    private static void streamTest(BigDecimal targetPrice) {
        System.out.println("===========================================================");
        System.out.println("\nStream Sum\n------------------------------------------------");
        final long start = System.currentTimeMillis();

        System.out.println("Sum: " +
                streamSum(products.stream(), product -> product.getPrice().compareTo(targetPrice) >= 0)
        );

        System.out.println("It took " + (System.currentTimeMillis() - start) + " ms.");
    }

    private static void parallelStreamTest(BigDecimal targetPrice) {
        System.out.println("===========================================================");
        System.out.println("\nParallel Stream Sum\n------------------------------------------------");
        final long start = System.currentTimeMillis();

        System.out.println("Sum: " +
                streamSum(products.parallelStream(), product -> product.getPrice().compareTo(targetPrice) >= 0)
        );

        System.out.println("It took " + (System.currentTimeMillis() - start) + " ms.");
    }

    public static void main(String[] args) {

        final BigDecimal targetPrice = new BigDecimal("40");

        parallelStreamTest(targetPrice);
        imperactiveTest(targetPrice);
        streamTest(targetPrice);

        // jvm을 구동하면 설정 작업등을 하게 되므로 정확하지 않을수 있어서 이 다음부터 제대로된 테스트
        System.out.println("\nIgnore Tests Above\n===========================================\n");

        for (int i = 0; i < 5; i++) {
            BigDecimal price = targetPrices[targetPriceRandom.nextInt(3)];

            parallelStreamTest(price);
            imperactiveTest(price);
            streamTest(price);
        }
    }
}

class Product {
    private Long id;
    private String name;
    private BigDecimal price;

    public Product(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}