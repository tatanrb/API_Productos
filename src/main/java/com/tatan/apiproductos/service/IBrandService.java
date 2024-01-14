package com.tatan.apiproductos.service;

import com.tatan.apiproductos.models.dto.BrandDTO;

import java.util.List;

public interface IBrandService {
    List<BrandDTO> getAll();

    BrandDTO getById(Long id);

    BrandDTO save(BrandDTO brandDTO);

    boolean delete(Long id);
}
