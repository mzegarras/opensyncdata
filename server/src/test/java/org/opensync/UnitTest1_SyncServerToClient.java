package org.opensync;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnitTest1_SyncServerToClient {
	
	
	static Server server;
	static Client client1;
	static Client client2;
	
	@BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code   
    	System.out.println("@BeforeClass - oneTimeSetUp");
    	server = new Server("server");
    	client1 = new Client("client1",server);
    	client2 = new Client("client2",server);
    	
    	System.out.println("*** START Creating apples on server");
    	server.addObject("2014-05-10", "apples", "3");
    	System.out.println("*** END   Creating apples on server");
    	server.display();
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
    public void syncClient1() {
        System.out.println("@Test - syncClient1");
        client1.doSync();
        client1.display();
        server.display();
        
        client1.doSync();
    }

}
