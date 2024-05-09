package com.anikatelearning.productservice.Repository;

import com.anikatelearning.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
