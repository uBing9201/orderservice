package com.playdata.orderservice.product.service;

import com.playdata.orderservice.product.dto.ProductResDto;
import com.playdata.orderservice.product.dto.ProductSaveReqDto;
import com.playdata.orderservice.product.entity.Product;
import com.playdata.orderservice.product.repository.ProductRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Product productCreate(ProductSaveReqDto dto) {
        // 원본 이미지를 어딘가에 저장하고, 그 저장된 위치를 Entity에 세팅하자.
        MultipartFile productImage = dto.getProductImage();
        UUID uuid = UUID.randomUUID();
        String uniqueFileName = UUID.randomUUID() + "_" + productImage.getOriginalFilename();
        File file = new File("/Users/ubing/Desktop/playdata/update/" + uniqueFileName);
        try{
            productImage.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 에러");
        }

        Product product = dto.toEntity();
        product.setImagePath(uniqueFileName);

        return productRepository.save(product);

    }

    public List<ProductResDto> productList(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        List<Product> content = products.getContent();
        List<ProductResDto> dtoList = content.stream()
                .map(Product::fromEntity)
                .collect(Collectors.toList());

        return dtoList;
    }
}
