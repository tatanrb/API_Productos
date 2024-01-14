package com.tatan.apiproductos.service.impl;

import com.tatan.apiproductos.models.dto.BrandDTO;
import com.tatan.apiproductos.models.dto.ProductDTO;
import com.tatan.apiproductos.models.entity.Brand;
import com.tatan.apiproductos.models.entity.Product;
import com.tatan.apiproductos.repository.IBrandRepository;
import com.tatan.apiproductos.repository.IProductRepository;
import com.tatan.apiproductos.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IBrandRepository brandRepository;


    @Override
    public List<ProductDTO> getAll() {
        try {
            List<Product> products = (List<Product>) productRepository.findAll();
            List<ProductDTO> productDTOList = new ArrayList<>();

            for (Product product : products) {
                productDTOList.add(
                        ProductDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .brandId(product.getBrand() == null ? null : product.getBrand().getId())
                                .build()
                );
            }

            return productDTOList;
        } catch (DataAccessException exception){
            return null;
        }
    }

    @Override
    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null){

            return ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .brandId(product.getBrand().getId())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public ProductDTO save(ProductDTO productDTO, BrandDTO brandDTO) {
        // Convert the BrandDTO in a Brand class
        Brand brand = Brand.builder()
                .id(brandDTO.getId())
                .name(brandDTO.getName())
                .productsList(brandDTO.getProductsList())
                .build();

        // Convert the ProductDTO provided in a Product class
        Product product = Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .brand(brand)
                .build();

        // Save the product
        Product new_product = productRepository.save(product);

        // Convert the Product in a ProductDTO and return it
        return ProductDTO.builder()
                .id(new_product.getId())
                .name(new_product.getName())
                .price(new_product.getPrice())
                .brandId(new_product.getBrand().getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
