package com.gojo.microservices.product.Service;

import com.gojo.microservices.product.Dto.ProductRequest;
import com.gojo.microservices.product.Dto.ProductResponse;
import com.gojo.microservices.product.Model.Product;
import com.gojo.microservices.product.Repository.ProdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdService {

    @Autowired
    private ProdRepo prodRepo;

    public Product toProduct(ProductRequest prodRequest)
    {
        Product prod=new Product();
        prod.setName(prodRequest.getName());
        prod.setDescription(prodRequest.getDescription());
        prod.setPrice(prodRequest.getPrice());

        System.out.println(prod.getDescription()+" "+prod.getName());
        return prod;
    }

    public ProductResponse toProductresponse(Product product)
    {
         ProductResponse prodResponse=new ProductResponse();

         prodResponse.setId(product.getId());
         prodResponse.setName(product.getName());
         prodResponse.setDescription(product.getDescription());
         prodResponse.setPrice(product.getPrice());

        System.out.println(prodResponse.getDescription()+" "+prodResponse.getName());

        return prodResponse;
    }


    public Product createProduct(ProductRequest productRequest)
    {
        Product product=toProduct(productRequest);
        return prodRepo.save(product);
    }

    public List<ProductResponse> getProducts()
    {
        List<Product> allProducts=prodRepo.findAll();

        return allProducts.stream().map(this::toProductresponse).toList();
    }
}
