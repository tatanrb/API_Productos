package com.tatan.apiproductos.models.dto;

import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO implements Serializable {
    private Long id;

    private String name;

    private double price;

    private Long brandId;
}
