package net.msonic.sync.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.msonic.sync.dao.ProductDao;
import net.msonic.sync.domain.Product;
import net.msonic.sync.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	
	@Override
	public List<Product> list() {
		// TODO Auto-generated method stub
		return productDao.list();
	}


	@Override
	public Product byId(String pk) {
		// TODO Auto-generated method stub
		return productDao.byId(pk);
	}


	@Override
	public void update(Product product) {
		// TODO Auto-generated method stub
		productDao.update(product);
	}

}
