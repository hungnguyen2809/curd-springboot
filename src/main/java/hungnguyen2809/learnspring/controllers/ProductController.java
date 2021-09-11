package hungnguyen2809.learnspring.controllers;

import hungnguyen2809.learnspring.models.Product;
import hungnguyen2809.learnspring.common.ResponseObject;
import hungnguyen2809.learnspring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
    //DI = Dependency Injection
    @Autowired
    private ProductRepository repository;

    // https://localhost:8080/api/v1/products
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllProducts() {
        List<Product> products = repository.findAll();
        return ResponseEntity.ok(ResponseObject.success(products));
    }

    // https://localhost:8080/api/v1/products/1
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findProduct(@PathVariable Long id) {
        // Optional:  value can null (not use orElseThrow)
        // orElseThrow: when data is null then throw exception with message
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.success(product, "Query product successfully")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.error(null, "Cannot find product with id = " + id)
        );
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addProduct(@RequestBody Product product) {
        try {
            // 2 products must not have the same name
            List<Product> foundProduct = repository.findByName(product.getName().trim());
            if (foundProduct.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        ResponseObject.error(null, "Product name is exists")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.success(repository.save(product), "Created product successfully")
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.error(null, "Error: " + e.getMessage())
            );
        }
    }

    //update, insert = upsert => update if found, otherwise insert
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> upsertProduct(@PathVariable Long id, @RequestBody Product newProduct) {
        // if product found then update otherwise insert
        // orElseGet: if not found product by id
        try {
            Product upsertProduct = repository.findById(id)
                    .map((product) -> {
                        product.setName(newProduct.getName());
                        product.setYear(newProduct.getYear());
                        product.setPrice(newProduct.getPrice());
                        product.setImageUrl(newProduct.getImageUrl());
                        return repository.save(product);
                    }).orElseGet(() -> {
                        return repository.save(newProduct);
                    });
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.success(upsertProduct, "Upsert product successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.error(null, "Error: " + e.getMessage())
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        try {
            boolean exists = repository.existsById(id);
            if (exists) {
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        ResponseObject.success(id, "Delete product successfully")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.error(null, "Cannot find product with id = " + id)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseObject.error(null, "Error: " + e.getMessage())
            );
        }
    }

    // https://localhost:8080/api/v1/products/test?name=<xxx>&age=<xxx>
    @GetMapping("/test")
    public ResponseEntity<ResponseObject> showParams(@RequestParam("name") String nameParam, @RequestParam String age) {
        return ResponseEntity.ok(
                ResponseObject.success(new String[]{nameParam, age})
        );
    }
}
