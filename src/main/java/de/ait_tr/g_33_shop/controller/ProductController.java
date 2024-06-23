package de.ait_tr.g_33_shop.controller;

import de.ait_tr.g_33_shop.domain.dto.ProductDto;
import de.ait_tr.g_33_shop.domain.entity.Product;
import de.ait_tr.g_33_shop.exception_handling.Response;
import de.ait_tr.g_33_shop.exception_handling.exceptions.FirstTestException;
import de.ait_tr.g_33_shop.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/products")
@Tag(name = "Product controller", description = "Controller for various operations with Products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }




//CRUD Create (POST),Read (GET),Update (PUT) ,Delete (DELETE)
// Create: POST ->localhost:8080/product
//Доступ ко всем продуктам - все пользователи
    //получить продукт по идентификатору - пользователей
    //сохранить продукт - админ





@PostMapping
    public ProductDto save(@RequestBody ProductDto product){
    //обращаемся к сервесу для сохранения продукта
    return service.save(product);
}


//READ GET ->localhost:8080/products?id=3

   @Operation(summary = "Get product by ID", description = "Getting all products that exist in the database")
   @GetMapping
    public ProductDto getById(@RequestParam Long id) {

        // Обращаемся к сервису и запрашиваем продукт с ИД, который пришел на вход
       return service.getById(id);
        }





@GetMapping("/all")
@Operation(summary = "Get all products", description = "Getting all products that exist in the database")
    public List<ProductDto> getAll(){
    return service.getAllActiveProducts();
}

     //UPDATE
    @PutMapping
    public ProductDto update(@RequestBody ProductDto product){
        //обращаемся к серису для обновления продукта в БД
        return service.update(product);
    }

    //DELETE: DELETE ->localhost:8080?id=2
    @DeleteMapping
    public void delete(@RequestParam(required = false) Long id,@RequestParam(required = false) String title){
        if (id!=null){
            service.deleteById(id);
        } else if (title!=null) {
            service.deleteByTitle(title);
        }
    }
//обращаемся к сервису продукт id

    @PutMapping("/restore")
    public void restore(@RequestParam Long id){
        service.restoreById(id);
    }
    @GetMapping("/quantity")
    public Long getProductsQuantity(){
        return service.getAllActiveProductsQuantity();
    }

   @GetMapping("/total-price")
   public BigDecimal getTotalPrice(){
        return service.getAllActiveProductsTotalPrice();
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice(){
        return service.getAllActiveProductsAveragePrice();
    }



}
