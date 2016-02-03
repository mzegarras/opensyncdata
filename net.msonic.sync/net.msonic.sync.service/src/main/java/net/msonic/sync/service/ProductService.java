package net.msonic.sync.service;

import java.util.List;

import net.msonic.sync.domain.Product;

public interface ProductService {
	
	List<Product> list();
	Product byId(String pk);
	void update(Product product);
}
