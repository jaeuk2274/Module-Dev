package me.jaeuk.hr_module.repository;

import me.jaeuk.hr_module.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    Product selectProductById(Long id);
    List<Product> selectAllProducts();
    void insertProduct(Product product);
}