package java8.stream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Albert-IM on 8/18/16.
 */
public class FunctionalInterfaceExamples {
    public static void main(String[] args) {

        final Product productA = new Product(1L, "A", new BigDecimal("10.00"));
        final Product productB = new Product(2L, "B", new BigDecimal("55.50"));
        final Product productC = new Product(3L, "C", new BigDecimal("14.10"));
        final Product productD = new Product(4L, "D", new BigDecimal("33.00"));
        final Product productE = new Product(5L, "E", new BigDecimal("110.00"));

        final List<Product> products = Arrays.asList(
                productA,
                productB,
                productC,
                productD,
                productE
        );

        final BigDecimal twenty = new BigDecimal("20");

        System.out.println("product >= $20: " + filter(products, product -> product.getPrice().compareTo(twenty) >= 0));
        System.out.println("product <= $10: " + filter(products, product -> product.getPrice().compareTo(new BigDecimal("10")) <= 0));


        final List<Product> expensiveProducts = filter(products, product -> product.getPrice().compareTo(new BigDecimal("50")) > 0);

//        final List<DiscountedProduct> discountedProducts = new ArrayList<>();
//
//        for(final Product product : expensiveProducts) {
//            discountedProducts.add(new DiscountedProduct(product.getId(), product.getName(), product.getPrice()));
//        }

        final List<DiscountedProduct> discountedProducts = map(expensiveProducts, product -> new DiscountedProduct(product.getId(), product.getName(), product.getPrice().multiply(new BigDecimal("0.5"))));

        System.out.println("expensiveProducts: " + expensiveProducts);
        System.out.println("discountedProducts: " + discountedProducts);

        final Predicate<Product> lessThanOrEqualTo30 = product -> product.getPrice().compareTo(new BigDecimal("30")) <= 0;

        System.out.println("discountedProducts (<= $30): " + filter(discountedProducts, lessThanOrEqualTo30));
        System.out.println("          Products (<= $30): " + filter(products, lessThanOrEqualTo30));

        final List<BigDecimal> prices = map(products, product -> product.getPrice());
        BigDecimal total = BigDecimal.ZERO;
        for (final BigDecimal price : prices) {
            total = total.add(price);
        }
        System.out.println("total Prices: " + total);

        final BigDecimal newTotal = total(products, product -> product.getPrice());
        System.out.println("newTotal Prices: " + newTotal);

        final BigDecimal discountTotal = total(discountedProducts, product -> product.getPrice());
        System.out.println("discountTotal Prices: " + discountTotal);

        Order order = new Order(1L, "on-1234", Arrays.asList(
                new OrderedItem(1L, productA, 2),
                new OrderedItem(2L, productC, 1),
                new OrderedItem(3L, productE, 10)
        ));

        System.out.println("OrderTotal Prices: " + order.totalPrice());

    }

    private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        final List<T> result = new ArrayList<>();

        for (final T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }

        return result;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        final List<R> result = new ArrayList<>();

        for (final T t : list) {
            result.add(function.apply(t));
        }

        return result;
    }

    private static <T> BigDecimal total(List<T> list, Function<T, BigDecimal> mapper) {
        BigDecimal total = BigDecimal.ZERO;

        for (final T t : list) {
            total = total.add(mapper.apply(t));
        }
        return total;
    }


    // lombok 플러그인을 설치하면 필요한 필드만 남겨놓으면 Constructor, getter, setter, hashcode 등등 모든것을 자동으로 만들어줌
    // @Data 로 사용
    // Constructor에서 필드값을 전체 다 받으려면 lombok의 @AllArgsConstructor를 사용하면 된다.
    // * annotation(@) 을 사용하려면 Preferences... > Build, Execution, Deployment > Compiler > Annotation Processors - Enable annotation processing 를 활성화 시켜야한다.
    static class Product {

        private Long id;
        private String name;
        private BigDecimal price;

        public Product() {
        }

        public Product(final Long id, final String name, final BigDecimal price) {
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

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Product product = (Product) o;

            if (id != null ? !id.equals(product.id) : product.id != null) return false;
            if (name != null ? !name.equals(product.name) : product.name != null) return false;
            return price != null ? price.equals(product.price) : product.price == null;

        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (price != null ? price.hashCode() : 0);
            return result;
        }
    }

    static class DiscountedProduct extends Product {
        public DiscountedProduct(Long id, String name, BigDecimal price) {
            super(id, name, price);
        }

        @Override
        public String toString() {
            return "DiscountedProduct{" +
                    "id=" + super.getId() +
                    ", name='" + super.getName() + '\'' +
                    ", price=" + super.getPrice() +
                    '}';
        }
    }

    static class OrderedItem {
        private Long id;
        private Product product;
        private int quantity;

        public BigDecimal getItemTotal() {
            return product.getPrice().multiply(new BigDecimal(quantity));
        }

        public OrderedItem() {
        }

        public OrderedItem(Long id, Product product, int quantity) {
            this.id = id;
            this.product = product;
            this.quantity = quantity;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }


    static class Order {
        private Long id;
        private String orderNumber;
        List<OrderedItem> items;

        public BigDecimal totalPrice() {
            return total(items, item -> item.getItemTotal());
        }

        public Order() {
        }

        public Order(Long id, String orderNumber, List<OrderedItem> items) {
            this.id = id;
            this.orderNumber = orderNumber;
            this.items = items;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public List<OrderedItem> getItems() {
            return items;
        }

        public void setItems(List<OrderedItem> items) {
            this.items = items;
        }
    }
}

