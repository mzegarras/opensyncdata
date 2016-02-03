package net.msonic.sync.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.msonic.sync.dao.ProductDao;
import net.msonic.sync.domain.Product;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "/Spring-Module.xml" })
public class ProductServiceTest {
	
	@Autowired
	ProductService productService;
	
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
    	assertNotNull(productService);
    }
    
    @Test
    public void listTest() {
    	
    	List<Product> list = productService.list();
		assertNotNull(list);
    	
    }

}
