package com.tatan.apiproductos.models.dto;

import com.tatan.apiproductos.models.entity.Product;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BrandDTO implements Serializable {
    private Long id;

    private String name;

    private List<Product> productsList;
}
