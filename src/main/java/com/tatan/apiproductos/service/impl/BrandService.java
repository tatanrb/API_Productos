package com.tatan.apiproductos.service.impl;

import com.tatan.apiproductos.models.dto.BrandDTO;
import com.tatan.apiproductos.models.entity.Brand;
import com.tatan.apiproductos.repository.IBrandRepository;
import com.tatan.apiproductos.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService implements IBrandService {
    @Autowired
    private IBrandRepository brandRepository;


    @Override
    public List<BrandDTO> getAll() {
        List<Brand> brandList = (List<Brand>) brandRepository.findAll();

        List<BrandDTO> brandDTOList = new ArrayList<>();

        for (Brand brandTemp : brandList) {
            BrandDTO brandDTOTemp = BrandDTO.builder()
                    .id(brandTemp.getId())
                    .name(brandTemp.getName())
                    .productsList(brandTemp.getProductsList())
                    .build();

            brandDTOList.add(brandDTOTemp);
        }
        return brandDTOList;
    }

    @Override
    public BrandDTO getById(Long id) {
        // Search for a brand with the id specified
        Brand brand = brandRepository.findById(id).orElse(null);

        if (brand != null){
            // Brand with the id specified exists

            return BrandDTO.builder()
                    .id(brand.getId())
                    .name(brand.getName())
                    .productsList(brand.getProductsList())
                    .build();
        } else {
            // Brand with the id specified not exists
            return null;
        }
    }

    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        try{
            // Create a brand with the data provided
            Brand brand = Brand.builder()
                    .id(brandDTO.getId())
                    .name(brandDTO.getName())
                    .productsList(brandDTO.getProductsList())
                    .build();

            // Save the brand created
            Brand brandBBDD = brandRepository.save(brand);

            // Return a DTO with the new data
            return  BrandDTO.builder()
                    .id(brandBBDD.getId())
                    .name(brandBBDD.getName())
                    .productsList(brandBBDD.getProductsList())
                    .build();

        } catch (DataAccessException exception){
            System.out.println("Error -> " + exception.getMessage());

            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        Brand brand = brandRepository.findById(id).orElse(null);

        if(brand != null) {
            brandRepository.deleteById(id);

            return true;
        } else {
            return false;
        }
    }
}
