package com.tatan.apiproductos.controller;

import com.tatan.apiproductos.models.dto.BrandDTO;
import com.tatan.apiproductos.models.dto.ProductDTO;
import com.tatan.apiproductos.models.payload.ResponseMessage;
import com.tatan.apiproductos.service.IBrandService;
import com.tatan.apiproductos.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IBrandService brandService;

    @GetMapping("/products")
    public ResponseEntity<?> getProducts(){
        ResponseMessage responseMessage;

        try {
            List<ProductDTO> productDTOList = productService.getAll();

            if (productDTOList.size() > 0){
                responseMessage = ResponseMessage.builder()
                        .message(productDTOList.size() + " products obtained successfully")
                        .object(productDTOList)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = ResponseMessage.builder()
                        .message("No products available")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exception){
            responseMessage = ResponseMessage.builder()
                    .message(exception.getMessage())
                    .object(null)
                    .build();

            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
        ResponseMessage responseMessage;
        try {
            ProductDTO productDTO = productService.getById(id);

            if (productDTO != null){
                responseMessage = ResponseMessage.builder()
                        .message("Product obtained successfully")
                        .object(productDTO)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = ResponseMessage.builder()
                        .message("Product with id " + id + " not found")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exception){
            responseMessage = ResponseMessage.builder()
                    .message(exception.getMessage())
                    .object(exception)
                    .build();

            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO){
        ResponseMessage responseMessage;
        try {
            // Get the brand class of the product
            BrandDTO brandDTO = brandService.getById(productDTO.getBrandId());

            if (brandDTO != null){
                // Brand id is valid
                ProductDTO new_productDTO = productService.save(productDTO, brandDTO);

                responseMessage = ResponseMessage.builder()
                        .message("Product saved successfully")
                        .object(new_productDTO)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
            } else {
                // Brand id is not valid
                responseMessage = ResponseMessage.builder()
                        .message("Brand id not valid")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exception){
            return null;
        }
    }

    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO){
        ResponseMessage responseMessage;

        try {
            // Test if the brandId is valid
            BrandDTO brandDTO = brandService.getById(productDTO.getId());

            if(brandDTO != null) {
                // BrandId is valid
                ProductDTO new_productDTO = productService.save(productDTO, brandDTO);

                responseMessage = ResponseMessage.builder()
                        .message("Product updated successfully")
                        .object(new_productDTO)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
            } else {
                responseMessage = ResponseMessage.builder()
                        .message("Brand with id " + productDTO.getBrandId() + " not found")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exception){
            responseMessage = ResponseMessage.builder()
                    .message(exception.getMessage())
                    .object(null)
                    .build();

            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        ResponseMessage responseMessage;

        try {
            ProductDTO productDTO = productService.getById(id);

            if (productDTO != null){
                productService.delete(id);

                responseMessage = ResponseMessage.builder()
                        .message("Product deleted successfully")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = ResponseMessage.builder()
                        .message("Product with id " + id + " not found")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exception){
            responseMessage = ResponseMessage.builder()
                    .message(exception.getMessage())
                    .object(null)
                    .build();

            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        }
    }
}
