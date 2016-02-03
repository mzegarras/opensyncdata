package net.msonic.sync.dao.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import net.msonic.sync.dao.ProductDao;
import net.msonic.sync.domain.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	
	@Autowired JdbcTemplate jdbcTemplate;

	public void insert(Product product) {
		// TODO Auto-generated method stub
		
		
		String SQL_INSERT = "INSERT INTO `Product` (`id`,`guid`,`name`,`value`,`counterLastUpdate`,`delete`) VALUES (?,?,?,?,?,?);";
		
		jdbcTemplate.update(SQL_INSERT, new Object[] { 
				product.getPk(),
				product.getGuid(),
				product.getName(),
				product.getValue(),
				product.getCounter_lastupdate(),
				(product.isDelete()?1:0)
			});
		
	}

	public void update(Product product) {
		// TODO Auto-generated method stub
		
		String SQL_UPDATE = "UPDATE `Product` SET `name` = ?, `value` = ?, `counterLastUpdate` = ?, `delete` = ? where id=?;";
		
		int rows = jdbcTemplate.update(SQL_UPDATE, new Object[] { 
				product.getName(),
				product.getValue(),
				product.getCounter_lastupdate(),
				(product.isDelete()?1:0),
				product.getPk()
			});
		
		System.out.println(product.toString());
		System.out.println(String.format("Filas afectadas: %s",rows));
		
	}

	public List<Product> list() {
		// TODO Auto-generated method stub 
		String SQL = "SELECT `id`,`guid`,`name`,`value`,`counterLastUpdate`,`delete` FROM Product;";
		
		
		List<Product> products = jdbcTemplate.query(SQL,new ProductRowMapper()); 
		
		return products;
	}
	
	
	private class ProductRowMapper implements RowMapper<Product>
	{

		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

			
			Product product = new Product(rs.getString("guid"), 
										 rs.getString("id"), 
										 rs.getString("name"), 
										 rs.getString("value"));
			
			product.setCounter_lastupdate(rs.getInt("counterLastUpdate"));
			
			product.setDelete(rs.getInt("delete")==1?true:false);	
			return product;
		}
		
	}


	public Product byId(String pk) {
		// TODO Auto-generated method stub
		String SQL = "SELECT `id`,`guid`,`name`,`value`,`counterLastUpdate`,`delete` FROM Product where id=?;";
		
		
		Product product = jdbcTemplate.queryForObject(SQL,new String[]{pk},new ProductRowMapper()); 
		
		return product;
	}

}
