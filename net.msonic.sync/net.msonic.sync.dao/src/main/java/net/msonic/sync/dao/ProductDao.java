package net.msonic.sync.dao;

import java.util.List;

import net.msonic.sync.domain.Product;

public interface ProductDao {
	
	void insert(Product product);
	void update(Product product);
	List<Product> list();
	Product byId(String pk);
}
