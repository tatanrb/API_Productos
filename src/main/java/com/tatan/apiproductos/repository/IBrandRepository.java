package com.tatan.apiproductos.repository;

import com.tatan.apiproductos.models.entity.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandRepository extends CrudRepository<Brand, Long> {
}
