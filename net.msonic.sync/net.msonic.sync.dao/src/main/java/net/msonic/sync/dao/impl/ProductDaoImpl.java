package net.msonic.sync.dao.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import net.msonic.sync.dao.ProductDao;
import net.msonic.sync.domain.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	
	@Autowired JdbcTemplate jdbcTemplate;

	public void insert(Product product) {
		// TODO Auto-generated method stub
		
		
		String SQL_INSERT = "INSERT INTO Product (id,guid,name,value,counterLastUpdate,delete) VALUES (?, ?, ?,?,?,?)";
		
		jdbcTemplate.update(SQL_INSERT, new Object[] { 
				product.getPk(),
				product.getGuid(),
				product.getName(),
				product.getValue(),
				product.getCounter_lastupdate(),
				product.isDelete()
			});
		
	}

}
