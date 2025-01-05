package com.gojo.microservices.product.ProductController;

import com.gojo.microservices.product.Dto.ProductRequest;
import com.gojo.microservices.product.Dto.ProductResponse;
import com.gojo.microservices.product.Model.Product;
import com.gojo.microservices.product.Repository.ProdRepo;
import com.gojo.microservices.product.Service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
public class ProdController
{

    @Autowired
    private ProdService prodService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductRequest prodRequest)
    {
        return prodService.createProduct(prodRequest);
    }

    @GetMapping
    public List<ProductResponse> getProducts()
    {
        return prodService.getProducts();
    }


}
