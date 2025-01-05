package com.gojo.microservices.product.Repository;

import com.gojo.microservices.product.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdRepo extends JpaRepository<Product,Long>
{

}
