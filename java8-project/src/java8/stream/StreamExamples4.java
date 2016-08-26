package java8.stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 * Created by Albert-IM on 8/23/16.
 */
public class StreamExamples4 {
    public static void main(String[] args) {

        List<Product> products = Arrays.asList(
                new Product(1L, "A", new BigDecimal("100.00")),
                new Product(2L, "B", new BigDecimal("51.20")),
                new Product(3L, "C", new BigDecimal("29.00")),
                new Product(4L, "D", new BigDecimal("35.80")),
                new Product(5L, "E", new BigDecimal("77.00"))
        );

        System.out.println("\n===================================");
        System.out.println("result: " +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30.00")) >= 0)
                        .collect(toList())
        );

        System.out.println("joining Result: \n" +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30.00")) >= 0)
                        .map(product -> product.toString())
                        .collect(joining("\n"))
        );

        System.out.println("\n===================================");
        System.out.println("\nIntStream.sum: " +
                IntStream.of(1,2,3,4,5)
                    .sum()
        );


        System.out.println("\n===================================");
        System.out.println("Toal Price: " +
                products.stream()
                        .map(product -> product.getPrice())
                        .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
        );

        System.out.println("\n===================================");
        System.out.println("Toal Price: of Products.price >= 30 \n" +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .map(product -> product.getPrice())
                        .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
        );

        System.out.println("\n===================================");
        System.out.println("Toal Count\n" +
                products.stream()
                        .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                        .count()
        );

        System.out.println("\n===================================");


        OrderedItem item1 = new OrderedItem(1L, products.get(0), 1);
        OrderedItem item2 = new OrderedItem(2L, products.get(1), 3);
        OrderedItem item3 = new OrderedItem(3L, products.get(2), 11);

        Order order = new Order(1L, Arrays.asList(item1, item2, item3));

        System.out.println("order.totalPrice: " +
                order.totalPrice()
        );


        System.out.println("\n===================================");
    }


    static class Product {
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

    static class Order {
        private Long id;
        private List<OrderedItem> items;

        public Order(Long id, List<OrderedItem> items) {
            this.id = id;
            this.items = items;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<OrderedItem> getItems() {
            return items;
        }

        public void setItems(List<OrderedItem> items) {
            this.items = items;
        }

        public BigDecimal totalPrice() {
            return items.stream()
                    .map(item -> item.getTotalPrice())
//                    .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2));
                    // Method Reference
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    static class OrderedItem {
        private Long id;
        private Product product;
        private int quantity;

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

        public BigDecimal getTotalPrice() {
            return product.getPrice().multiply(new BigDecimal(quantity));
        }
    }
}


