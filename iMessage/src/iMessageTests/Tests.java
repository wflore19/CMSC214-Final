package iMessageTests;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import iMessageCore.MessageService;
import iMessageCore.Server;

public class Tests {
	Server server;
	
	@Before
	public void setUp() {
		this.server = new Server(MessageService.getInstance());
	}
	
    @Test
    public void testServerInitialization() {
        assertNotNull("Server should be initialized", server);
    }
}
