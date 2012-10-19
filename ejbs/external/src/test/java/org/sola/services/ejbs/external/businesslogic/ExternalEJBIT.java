package org.sola.services.ejbs.external.businesslogic;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.UserTransaction;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.sola.services.common.EntityAction;
import org.sola.services.common.test.AbstractEJBTest;
import org.sola.services.ejb.administrative.repository.entities.BaUnit;
import org.sola.services.ejbs.external.repository.entities.Client;
import org.sola.services.ejbs.external.repository.entities.ClientType;
import org.sola.services.ejbs.external.repository.entities.Contact;
import org.sola.services.ejbs.external.repository.entities.Request;

/**
 * Integration test for the ExternalEJB.
 */
public class ExternalEJBIT extends AbstractEJBTest {

    private static String USER_ID = "ext-user-id";
    private static String CLIENT_ID = "waiheke-bank-id";
    private static final String LOGIN_USER = "extuser";
    private static final String LOGIN_PASS = "test";

    public ExternalEJBIT() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        login(LOGIN_USER, LOGIN_PASS);
    }

    @After
    public void tearDown() throws Exception {
        logout();
    }

    /**
     * Test method to get client by the client ID.
     */
    @Test
    public void testGetClient() throws Exception {
        System.out.println(">>> Trying to get external client data by client ID.");

        try {
            ExternalEJBLocal instance = (ExternalEJBLocal) getEJBInstance(ExternalEJB.class.getSimpleName());
            Client result = instance.getClient(CLIENT_ID);

            assertNotNull("Client not found.", result);
            System.out.println(">>> Found client " + result.getName());

            assertTrue("Client contacts not found.", result.getContacts().size() > 0);
            System.out.println(">>> Found " + result.getContacts().size() + " contacts.");

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method to get client by the user ID.
     */
    @Test
    public void testGetClientByUser() throws Exception {
        System.out.println(">>> Trying to get external client data by user ID.");

        try {
            ExternalEJBLocal instance = (ExternalEJBLocal) getEJBInstance(ExternalEJB.class.getSimpleName());
            Client result = instance.getClientByUser(USER_ID);

            assertNotNull("Client not found.", result);
            System.out.println(">>> Found client " + result.getName());

            assertTrue("Client contacts not found.", result.getContacts().size() > 0);
            System.out.println(">>> Found " + result.getContacts().size() + " contacts.");

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method to submit client's request.
     */
    @Test
    public void testMakeRequest() throws Exception {
        System.out.println(">>> Trying to submit request.");

        UserTransaction tx = getUserTransaction();

        try {
            ExternalEJBLocal instance = (ExternalEJBLocal) getEJBInstance(ExternalEJB.class.getSimpleName());
            tx.begin();

            Client client = instance.getClient(CLIENT_ID);

            BaUnit result = instance.makeRequest(CLIENT_ID, "NA34B", "649");
            tx.commit();

            assertNotNull("BA Unit not found.", result);
            System.out.println(">>> Found BA Unit " + result.getNameFirstpart()
                    + "/" + result.getNameLastpart());

            // Check client balance was deducted
            BigDecimal balance = client.getBalance();
            client = instance.getClient(CLIENT_ID);

            assertTrue("Client's balance was not reduced.",
                    client.getBalance().compareTo(balance) == -1);
            System.out.println(">>> Client balance was reduced. Initial balance was "
                    + balance.toPlainString() + ", reduced to " + client.getBalance().toPlainString());

        } catch (Exception e) {
            tx.rollback();
            fail(e.getMessage());
        }
    }

    /**
     * Test method to get client requests.
     */
    @Test
    public void testGetClientRequests() throws Exception {
        System.out.println(">>> Trying to get Client requests.");

        try {
            ExternalEJBLocal instance = (ExternalEJBLocal) getEJBInstance(ExternalEJB.class.getSimpleName());

            List<Request> requests = instance.getClientRequests(CLIENT_ID);

            assertNotNull("No requests were found.", requests);
            System.out.println(">>> Found " + requests.size() + " requests.");

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method to get client types.
     */
    @Test
    public void testGetClientTypes() throws Exception {
        System.out.println(">>> Trying to get client types.");

        try {
            ExternalEJBLocal instance = (ExternalEJBLocal) getEJBInstance(ExternalEJB.class.getSimpleName());
            List<ClientType> result = instance.getClientTypes("en");

            assertNotNull("Client types not found.", result);
            System.out.println(">>> Found " + result.size() + " client types");

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test method to change and save client.
     */
    @Test
    public void testGetSaveClient() throws Exception {
        System.out.println(">>> Trying to make changes and save Client.");
        UserTransaction tx = getUserTransaction();

        try {
            ExternalEJBLocal instance = (ExternalEJBLocal) getEJBInstance(ExternalEJB.class.getSimpleName());
            Client client = instance.getClient(CLIENT_ID);
            
            if (client != null) {
                String newContactId = "new-contact-person";
                String newClientName = "Waiheke Bank Ltd.";
                String oldClientName = client.getName();
                
                Contact contact = new Contact();
                contact.setId(newContactId);
                contact.setName("New contact person");
                contact.setPhoneNumber("222-333");
                
                client.setName(newClientName);
                client.getContacts().add(contact);

                tx.begin();
                instance.saveClient(client);
                tx.commit();
                
                // Retrieve changed data
                client = instance.getClient(CLIENT_ID);
                
                assertNotNull("Client types not found.", client);
                
                assertTrue("Client name was not changed.", client.getName().equals(newClientName));
                System.out.println(">>> Client name was changed.");
                
                boolean newContactCreated = false;
                
                for(Contact cont : client.getContacts()){
                    if(cont.getId().equals(newContactId)){
                        System.out.println(">>> New contact person was added.");
                        newContactCreated = true;
                        break;
                    }
                }
                
                if(!newContactCreated){
                    fail("New contact was not created.");
                }
                
                // Rollback test data changes.
                client.setName(oldClientName);
                
                // Delete contact
                for(Contact cont : client.getContacts()){
                    if(cont.getId().equals(newContactId)){
                        cont.setEntityAction(EntityAction.DELETE);
                        break;
                    }
                }
                
                tx.begin();
                instance.saveClient(client);
                tx.commit();
                
                System.out.println(">>> Client changes rolled back.");
            }

        } catch (Exception e) {
            tx.rollback();
            fail(e.getMessage());
        }
    }
}

