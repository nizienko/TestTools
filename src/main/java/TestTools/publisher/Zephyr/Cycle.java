package TestTools.publisher.Zephyr;

/**
 * Created by def on 24.11.14.
 */
public class Cycle {
    private Product product;
    private String name;

    public Cycle(Product product, String name){
        this.product = product;
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
