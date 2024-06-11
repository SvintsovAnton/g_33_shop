package de.ait_tr.g_33_shop.controller;

import de.ait_tr.g_33_shop.domain.dto.ProductDto;
import de.ait_tr.g_33_shop.service.interfaces.ProductService;
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

    //Create: POST ->localhost:8080/product

@PostMapping
    public ProductDto save(@RequestBody ProductDto product){
    //обращаемся к сервесу для сохранения продукта
    return service.save(product);
}


//READ GET ->localhost:8080/products?id=3

    @GetMapping
    public List<ProductDto> getProduct(@RequestParam(required = false) Long id) {
        // Обращаемся к сервису и запрашиваем продукт с ИД, который пришел на вход
        if (id == null) {
            return service.getAllActiveProducts();
        } else {
            ProductDto product = service.getById(id);
            return product==null?null:List.of(product);
        }
    }




/*@GetMapping("/all")
    public List<Product> getAll(){
    //обращаемся к сервису и запрашиваем все продукты
    return null;
}*/

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
