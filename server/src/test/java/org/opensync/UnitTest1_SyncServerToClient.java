package org.opensync;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnitTest1_SyncServerToClient {
	
	
	static Server server;
	static Client client1;
	
	@BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code   
    	System.out.println("@BeforeClass - oneTimeSetUp");
    	server = new Server("server");
    	client1 = new Client("client1",server);
    	client1.setConflictHandling(Common.ConflictHandling.TIMESTAMPPRIORITY.getValue());
    	
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
    	System.out.println("*** START Do sync on client 1");
        client1.doSync();
        System.out.println("*** END   Do sync on client 1");
        client1.display();
        server.display();
        System.out.println("*** START Sync again on client 1 (no data should be synced)");
        client1.doSync();
        System.out.println("*** END Sync again on client 1 (no data should be synced)");
        client1.display();
        server.display();
        
        System.out.println("*** START Updating apples on server");
        server.updateObject("2014-05-10", "5");
        System.out.println("*** END   Updating apples on server");
        server.display();

        
    }

}
