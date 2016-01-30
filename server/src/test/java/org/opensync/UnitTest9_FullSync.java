package org.opensync;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnitTest9_FullSync {
	
	static Server server;
	static Client client1;
	static Client client2;
	
	@BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code   
    	System.out.println("@BeforeClass - oneTimeSetUp");
    	server = new Server("server");
    	
    	long value=System.currentTimeMillis();
    	System.out.println(value);
    	System.out.println(value/1000L);
    	
    	client1 = new Client("client1",server);
    	client1.setConflictHandling(Common.ConflictHandling.TIMESTAMPPRIORITY.getValue());
    	
    	client2 = new Client("client2",server);
    	client2.setConflictHandling(Common.ConflictHandling.TIMESTAMPPRIORITY.getValue());
    	
    	client1.display();
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
    public void syncClient1() throws InterruptedException {	
    	System.out.println("*** START Creating apples with PK 2014-06-20 on client 1");
    	client1.addObject("2014-05-10", "apples", "3");
    	System.out.println("*** END   Creating cherries on client 1");
    	client1.display();
    	
    	
    	System.out.println("*** START Do sync on client 1");
    	client1.doSync();
    	System.out.println("*** END   Do sync on client 1");
    	client1.display();
    	server.display();
    	
    	
    	System.out.println("*** START Do sync on client 2");
    	client2.doSync();
    	System.out.println("*** END   Do sync on client 2");
    	client2.display();
    	server.display();
    	
    	
    	
    	System.out.println("*** START Updating apples on client 1");
    	client1.updateObject("2014-05-10", "5");
    	System.out.println("*** END   Updating apples on client 1");
    	client1.display();
    	
    	
    	System.out.println("*** START Do sync on client 1");
    	client1.doSync();
    	System.out.println("*** END   Do sync on client 1");
    	client1.display();
    	server.display();
    	
    	
    	System.out.println("*** START Do sync on client 2");
    	client2.doSync();
    	System.out.println("*** END   Do sync on client 2");
    	client2.display();
    	server.display();
    	
    	
    	System.out.println("*** START Updating apples on client 2");
    	client2.updateObject("2014-05-10", "7");
    	System.out.println("*** END   Updating apples on client 2");
    	client2.display();
    	
    	
    	System.out.println("*** START Do sync on client 2");
    	client2.doSync();
    	System.out.println("*** END   Do sync on client 2");
    	client2.display();
    	server.display();
    	
    	System.out.println("*** START Do sync on client 1");
    	client1.doSync();
    	System.out.println("*** END   Do sync on client 1");
    	client1.display();
    	server.display();
    	
    	System.out.println("*** START Updating apples on server");
    	server.updateObject("2014-05-10", "9");
    	System.out.println("*** END   Updating apples on server");
    	server.display();
    	

    	System.out.println("*** START Do sync on client 1");
    	client1.doSync();
    	System.out.println("*** END   Do sync on client 1");
    	client1.display();
    	server.display();
    	
    	
    	System.out.println("*** START Creating oranges on client 1");
    	client1.addObject("2014-05-11", "oranges", "4");
    	System.out.println("*** END Creating oranges on client 1");
    	client1.display();
    	
    	
    	System.out.println("*** START Full sync on client 1 (all data should be synced) ");
    	client1.doFullSync();
    	System.out.println("*** END Full sync on client 1 (all data should be synced) ");
    	client1.display();
    	server.display();
    	
    	
    	System.out.println("*** START Sync again on client 1 (no data should be synced)");
    	client1.doSync();
    	System.out.println("*** END Sync again on client 1 (no data should be synced)");
    	client1.display();
    	server.display();
    	
    }

}
