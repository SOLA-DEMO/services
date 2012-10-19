package org.sola.services.ejbs.external.businesslogic;

import java.util.List;
import javax.ejb.Local;
import org.sola.services.common.ejbs.AbstractEJBLocal;
import org.sola.services.ejb.administrative.repository.entities.BaUnit;
import org.sola.services.ejbs.external.repository.entities.Client;
import org.sola.services.ejbs.external.repository.entities.ClientType;
import org.sola.services.ejbs.external.repository.entities.Request;

/**
 * External service business logic EJB interface
 */
@Local
public interface ExternalEJBLocal extends AbstractEJBLocal {
    /** 
     * Returns Client instance by ID. 
     * @param clientId The ID of the client.
     */
    Client getClient(String clientId);
    
    /** 
     * Returns Client instance by user ID. 
     * @param userId ID of user, associated with client.
     */
    Client getClientByUser(String userId);
    
    /** 
     * Returns client types reference data. 
     * @param lang Language code to use for getting translated display 
     * value and description.
     */
    List<ClientType> getClientTypes(String lang);
    
    /**
     * Returns List of requests, made by the client.
     * @param clientId The ID of the client.
     */
    List<Request> getClientRequests(String clientId);
    
    /** 
     * Saves Client instance. 
     * @param client Client instance to save.
     */
    Client saveClient(Client client);
    
    /**
     * Registers Client request and returns property object if any found.
     * @param nameFirstPart First part of property object identifier.
     * @param nameLastPart Last part of property object identifier.
     * @param clientId The ID of the client.
     */
    BaUnit makeRequest(String clientId, String nameFirstPart, String nameLastPart);
}

