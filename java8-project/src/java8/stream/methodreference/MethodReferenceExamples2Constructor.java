package java8.stream.methodreference;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Created by Albert-IM on 8/26/16.
 */
public class MethodReferenceExamples2Constructor {

    public static void main(String[] args) {

        final Section section1 = new Section(1);

        /*
         * factory method
         */


        final Function<Integer, Section> sectionFactory = number -> new Section(number);
        final Section section1WithLambdaExpression = sectionFactory.apply(1);

        final Function<Integer, Section> sectionFactoryWithMethodReference = Section::new;
        final Section section1WithMethodReference = sectionFactoryWithMethodReference.apply(1);


        System.out.println(section1);
        System.out.println(section1WithLambdaExpression);
        System.out.println(section1WithMethodReference);
        System.out.println("==============================================\n");

        final OldProduct product = new OldProduct(1L, "A", new BigDecimal("100"));
        System.out.println(product);

        final OldProductCreator oldProductCreator = OldProduct::new;
        System.out.println(oldProductCreator.create(1L, "A", new BigDecimal("100")));
        System.out.println("==============================================\n");

        final ProductA a = createProduct(1L, "A", new BigDecimal("123"), ProductA::new);
        final ProductB b = createProduct(1L, "B", new BigDecimal("111"), ProductB::new);
        final ProductC c = createProduct(1L, "B", new BigDecimal("10"), ProductC::new);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

    }

    private static <T extends Product> T createProduct(final Long id, final String name, final BigDecimal price, final ProductCreator<T> productCreator) {
        if(id == null || id < 1L) {
            throw new IllegalArgumentException("The id must be a positive Long.");
        }

        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The name is not given.");
        }

        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The price must be greater than 0.");
        }

        return productCreator.create(id, name, price);
    }
}

@FunctionalInterface
interface ProductCreator<T extends Product> {
    T create(Long id, String name, BigDecimal price);
}

@FunctionalInterface
interface OldProductCreator {
    OldProduct create(Long id, String name, BigDecimal price);
}

class Section {
    private int number;

    public Section(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Section{" +
                "number=" + number +
                '}';
    }
}
abstract class Product {
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
    public String
    toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

class OldProduct {
    private Long id;
    private String name;
    private BigDecimal price;

    public OldProduct(Long id, String name, BigDecimal price) {
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
    public String
    toString() {
        return "OldProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}


class ProductA extends Product {
    public ProductA(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "A=" + super.toString();
    }
}

class ProductB extends Product {
    public ProductB(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "B=" + super.toString();
    }
}

class ProductC extends Product {
    public ProductC(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "C=" + super.toString();
    }
}