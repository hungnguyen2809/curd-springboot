package hungnguyen2809.learnspring.repositories;

import hungnguyen2809.learnspring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//repositories được dùng là nơi để chứa dữ liệu(kho dữ liệu) khi chạy và lấy dữ liệu về từ database
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Product kiểu của thực thể dữ liệu, Long chính là kiểu dữ liệu của trường ID
    List<Product> findByName(String name); //nâng cao, cách viết tên hàm theo JPA để nó có chức năng tương ứng
}
