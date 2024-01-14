package com.tatan.apiproductos.controller;

import com.tatan.apiproductos.models.dto.BrandDTO;
import com.tatan.apiproductos.models.payload.ResponseMessage;
import com.tatan.apiproductos.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BrandController {
    @Autowired
    private IBrandService brandService;

    @GetMapping("/brands")
    public ResponseEntity<?> getBrands(){
        ResponseMessage responseMessage;

        try{
            List<BrandDTO> brandDTOList = brandService.getAll();

            if (brandDTOList.size() > 0){
                responseMessage = ResponseMessage.builder()
                        .message(brandDTOList.size() + " brands obtained successfully")
                        .object(brandDTOList)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = ResponseMessage.builder()
                        .message("No brands obtained")
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

    @GetMapping("/brand/{id}")
    public ResponseEntity<?> getBrand(@PathVariable Long id){
        ResponseMessage responseMessage;

        try{
            BrandDTO brandDTO = brandService.getById(id);

            if (brandDTO != null){
                responseMessage = ResponseMessage.builder()
                        .message("Brand obtained successfully")
                        .object(brandDTO)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = ResponseMessage.builder()
                        .message("Brand with id " + id + " doesn't exist")
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

    @PostMapping("/brand")
    public ResponseEntity<?> createBrand(@RequestBody BrandDTO brandDTO){
        ResponseMessage responseMessage;

        try{
            BrandDTO brandDTO_BBDD = brandService.save(brandDTO);

            if (brandDTO_BBDD != null){
                responseMessage = ResponseMessage.builder()
                        .message("Brand created successfully")
                        .object(brandDTO_BBDD)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
            } else {
                responseMessage = ResponseMessage.builder()
                        .message("An error occurred")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException exception){
            responseMessage = ResponseMessage.builder()
                    .message(exception.getMessage())
                    .object(null)
                    .build();

            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/brand")
    public ResponseEntity<?> updateBrand(@RequestBody BrandDTO brandDTO){
        ResponseMessage responseMessage;

        try {
            BrandDTO brandDTO_BBDD = brandService.save(brandDTO);

            if (brandDTO_BBDD != null){
                responseMessage = ResponseMessage.builder()
                        .message("Brand updated successfully")
                        .object(brandDTO_BBDD)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = ResponseMessage.builder()
                        .message("An error occurred")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException exception){
            responseMessage = ResponseMessage.builder()
                    .message(exception.getMessage())
                    .object(null)
                    .build();

            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id){
        ResponseMessage responseMessage;

        try {
            // Try to delete the brand
            boolean deleted = brandService.delete(id);

            if (deleted){
                // Deleted successfully
                responseMessage = ResponseMessage.builder()
                        .message("Brand deleted successfully")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            } else {
                // Brand was not deleted successfully
                responseMessage = ResponseMessage.builder()
                        .message("Brand can't be deleted successfully")
                        .object(null)
                        .build();

                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }

        } catch (DataAccessException exception){
            responseMessage = ResponseMessage.builder()
                    .message(exception.getMessage())
                    .object(null)
                    .build();

            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
