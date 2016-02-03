package net.msonic.sync.dao;

import net.msonic.sync.domain.Product;

public interface ProductDao {
	
	void insert(Product product);
	void update(Product product);
	
}
