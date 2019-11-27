package com.sda.MyShoppingList.entities.product;

import com.sda.MyShoppingList.abstractclasses.AbstractController;
import com.sda.MyShoppingList.abstractclasses.AbstractModel;
import com.sda.MyShoppingList.entities.category.CategoryModel;
import com.sda.MyShoppingList.entities.category.CategoryRepository;
import com.sda.MyShoppingList.exception.BusinessExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/product")
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class ProductController extends AbstractController<Long, ProductModel, ProductRepository, ProductService> {

    @Autowired
    public ProductController(ProductService service) {
        super(service);

    }

    @PostMapping(path = "/{categoryId}/product", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductModel> addProduct(@RequestBody ProductModel productModel, @PathVariable Long categoryId) {
        try {
            return ResponseEntity.ok(service.add(productModel, categoryId));
        } catch (BusinessExeption e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping(path = "/{categoryId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductModel> updateProduct(@RequestBody ProductModel productModel, @PathVariable Long categoryId) {
        try {
            ProductModel updatedProduct = service.updateProduct(productModel, categoryId);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException | BusinessExeption e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/firstFive/{keyWord}", produces = "application/json")
    public ResponseEntity<List<ProductModel>> getFirstFiveByNameOrDetails(@PathVariable String keyWord) {
        List<ProductModel> foundProducts = service.findFirstFiveByNameOrDetails(keyWord);
        System.out.println(keyWord);
        System.out.println(foundProducts.size());
        return
                !foundProducts.isEmpty() ?
                        ResponseEntity.ok(foundProducts) :
                        ResponseEntity.notFound().build();
    }
}
