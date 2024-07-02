package de.ait_tr.g_33_shop.service;

import de.ait_tr.g_33_shop.domain.dto.ProductDto;
import de.ait_tr.g_33_shop.domain.entity.Product;
import de.ait_tr.g_33_shop.exception_handling.Response;
import de.ait_tr.g_33_shop.exception_handling.exceptions.*;
import de.ait_tr.g_33_shop.repository.ProductRepository;
import de.ait_tr.g_33_shop.service.interfaces.ProductService;
import de.ait_tr.g_33_shop.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    private ProductMappingService mappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    //1.
//
    @Override
    public ProductDto save(ProductDto dto) {
        Product entity = mappingService.mapDtoToEntity(dto);
        if (repository.findAll().stream().anyMatch(element -> element.getTitle().equals(entity.getTitle()))) {
            throw new DataIntegrityViolationException("Product with this title exist allready");
        }
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
        return mappingService.mapEntityToDto(entity);
    }


    @Override
    public List<ProductDto> getAllActiveProducts() {
        List<ProductDto> productDtoList = repository.findAll()
                .stream().filter(Product::isActive).map(mappingService::mapEntityToDto).toList();
        if (productDtoList.isEmpty()) {
            throw new EmptyListException("List of active products is empty");
        }
        return productDtoList;
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product != null && product.isActive()) {
            return mappingService.mapEntityToDto(product);
        }
        throw new ProductNotFindException("This product isn't found");
    }

    @Override
    public ProductDto update(ProductDto product) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        if (repository.findById(id).orElse(null) == null) {
            throw new ProductNotFindException("This product isn't found und can not be removed");
        }
        repository.deleteById(id);
    }

    @Override
    public void deleteByTitle(String title) {
    }

    @Override
    public void restoreById(Long id) {

    }

    @Override
    public long getAllActiveProductsQuantity() {
        return 0;
    }

    @Override
    public BigDecimal getAllActiveProductsTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAllActiveProductsAveragePrice() {
        return null;
    }

    @Override
    @Transactional
    public void attachImage(String imgUrl, String productTitle) {
        Product product = repository.findByTitle(productTitle).orElseThrow(() -> new ProductNotFindException("Product not find"));
        product.setImage(imgUrl);
    }


}
