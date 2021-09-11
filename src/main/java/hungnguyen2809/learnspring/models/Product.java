package hungnguyen2809.learnspring.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

//POJO = Plain Object Java Object
@Entity  //cho biết class này là một thực thể
@Table(name = "tbl_products") // để anh xạ cho biết class này ứng với table nào trong database
public class Product {
    @Id // chỉ định trường này là id
//    @GeneratedValue(strategy = GenerationType.AUTO) // generate id key auto
    //use "sequence": tạo ra quy tắc khi thêm mới bản ghi vào trong dữ liệu
    //bên dưới là theo quy tắc mỗi lần tăng lên 1 đơn vị bởi allocationSize = 1
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long id;

    @Column(name = "product_name", nullable = false, unique = true, length = 255)
    private String name; //name required, unique, max length 255

    @Column(name = "product_year")
    private int year;

    @Column(name = "product_price")
    private Double price;

    @Column(name = "product_url")
    private String imageUrl;

    //calculated filed = transient => trường tạm thời không lưu vào cơ sở dữ liệu nhưng đc tính toán bởi trường khác
    // vd: có điểm toán, lý, hóa => trường điểm tổng chính là transient (điểm tổng đó mình không lưu vào csdl, được tính bởi trường khác)
    @Transient
    private int ageProduct; //age of product for "year"

    public Product() {
    }

    public Product(String name, int year, Double price, String imageUrl) {
        this.name = name;
        this.year = year;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getAgeProduct() {
        return Calendar.getInstance().get(Calendar.YEAR) - this.year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return year == product.year && ageProduct == product.ageProduct
                && Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(price, product.price) && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, price, imageUrl, ageProduct);
    }
}
