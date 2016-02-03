package net.msonic.sync.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.msonic.sync.domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "/Spring-Module.xml" })
public class ProductoDaoTest {
	
	@Autowired
	ProductDao productDao;
	
	@BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code   
    	System.out.println("@BeforeClass - oneTimeSetUp");
    }

    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
    	System.out.println("@AfterClass - oneTimeTearDown");
    }
    
    @Before
    public void setUp() {
        System.out.println("@Before - setUp");
    }

    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }
    
    @Test
    public void serviceDaoNotNull() {
    	assertNotNull(productDao);
    }
    
    @Test
    public void insertTest() {
    	
    	String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	
    	Product newObject = new Product(null, fecha, "Apples","1");
		newObject.setDelete(false);
    	productDao.insert(newObject);
    	
    }
    
    
    @Test
    public void updateTest() {
    	
    	Product newObject = new Product("03317e34-9351-4709-9204-b138101cc13f", "2016-01-30", "Apples","3");
		//newObject.setDelete(false);
    	productDao.update(newObject);
    	
    }
    
    
    
    @Test
    public void listTest() {
    	
    	List<Product> list = productDao.list();
		assertNotNull(list);
    	
    }
    
    @Test
    public void queryById() {
    	
    	Product p = productDao.byId("2016-01-30");
		assertNotNull(p);
    	
    }
    
	
}
