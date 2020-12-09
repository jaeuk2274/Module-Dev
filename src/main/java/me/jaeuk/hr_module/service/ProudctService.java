package me.jaeuk.hr_module.service;

import me.jaeuk.hr_module.domain.Product;
import me.jaeuk.hr_module.repository.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public Product getProductById(Long id) {
        return productMapper.selectProductById(id);
    }

    public List<Product> getAllProducts() {
        return productMapper.selectAllProducts();
    }

    @Transactional
    public void addProduct(Product product) {
        productMapper.insertProduct(product);
    }
}