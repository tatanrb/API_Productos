package com.tatan.apiproductos.service;

import com.tatan.apiproductos.models.dto.BrandDTO;
import com.tatan.apiproductos.models.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAll();

    ProductDTO getById(Long id);

    ProductDTO save(ProductDTO productDTO, BrandDTO brandDTO);

    void delete(Long id);
}
