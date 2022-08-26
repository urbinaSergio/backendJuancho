package com.juancho.urbina.web.controller;

import com.juancho.urbina.domain.Product;
import com.juancho.urbina.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @CrossOrigin(origins = "*",allowedHeaders = "*")
    @GetMapping("/all")
    @ApiOperation("Traer todos los productos de mercado")
    @ApiResponse(code = 200, message = "Ok")
    public ResponseEntity<List<Product>> getAll(){

        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }
    @CrossOrigin(origins = "*",allowedHeaders = "*")
    @GetMapping("/{id}")
    @ApiOperation("Busqueda de producto por id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Producto no encontrado")
    })
    public ResponseEntity<Product> getProduct(@ApiParam(value = "El id del prodcuto", required = true, example = "7") @PathVariable("id") int productId){
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @CrossOrigin(origins = "*",allowedHeaders = "*")
    @ApiOperation("Busqueda de categoria por id")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId){
        return productService.getByCategory(categoryId).map(products -> new ResponseEntity<>(products,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @CrossOrigin(origins = "*",allowedHeaders = "*")
    @ApiOperation("Guardado de productos")
    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product){

        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED) ;
    }
    @CrossOrigin(origins = "*",allowedHeaders = "*")
    @ApiOperation("Borrado de producto")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") int productId){
        if(productService.delete(productId)){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

}
