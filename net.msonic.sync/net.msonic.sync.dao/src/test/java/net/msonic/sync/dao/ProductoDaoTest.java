package net.msonic.sync.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
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
    public void test1() {
    	assertNotNull(productDao);
    }
    
    @Test
    public void test2() {
    	
    	Product newObject = new Product(null, "2016-01-30", "Apples","1");
		newObject.setDelete(true);
    	productDao.insert(newObject);
    	
    }
	
}
