package com.playdata.orderservice.product.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.playdata.orderservice.product.entity.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void bulkInsert() {
        // given
        for (int i = 1; i <= 50; i++) {
            Product p = Product.builder()
                    .name("상품" + i)
                    .category("카테고리" + i)
                    .price(3000)
                    .stockQuantity(100)
                    .build();
            productRepository.save(p);
        }

        // when

        // then
    }
}