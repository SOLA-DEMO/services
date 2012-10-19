package org.sola.services.ejbs.external.businesslogic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.sola.common.RolesConstants;
import org.sola.services.common.ejbs.AbstractEJB;
import org.sola.services.common.repository.CommonSqlProvider;
import org.sola.services.ejb.administrative.businesslogic.AdministrativeEJBLocal;
import org.sola.services.ejb.administrative.repository.entities.BaUnit;
import org.sola.services.ejbs.external.repository.entities.Client;
import org.sola.services.ejbs.external.repository.entities.ClientType;
import org.sola.services.ejbs.external.repository.entities.Request;

/**
 * External service business logic
 */
@Stateless
@EJB(name = "java:global/SOLA/ExternalEJBLocal", beanInterface = ExternalEJBLocal.class)
public class ExternalEJB extends AbstractEJB implements ExternalEJBLocal {

    @EJB
    AdministrativeEJBLocal administrativeEJB;
    
    @Override
    public Client getClient(String clientId) {
        if (clientId != null) {
            return getRepository().getEntity(Client.class, clientId);
        } else {
            return null;
        }
    }

    @Override
    public Client getClientByUser(String userId) {
        if (userId != null) {
            Map params = new HashMap<String, Object>();
            params.put(CommonSqlProvider.PARAM_WHERE_PART, Client.QUERY_WHERE_BYUSERID);
            params.put(Client.QUERY_PARAMETER_USERID, userId);
            return getRepository().getEntity(Client.class, params);
        } else {
            return null;
        }
    }
    
    @Override
    public List<ClientType> getClientTypes(String lang) {
        return getRepository().getCodeList(ClientType.class, lang);
    }

    @Override
    public List<Request> getClientRequests(String clientId) {
        if (clientId != null) {
            Map params = new HashMap<String, Object>();
            params.put(CommonSqlProvider.PARAM_WHERE_PART, Request.QUERY_WHERE_BYCLIENTID);
            params.put(Request.QUERY_PARAMETER_CLIENTID, clientId);
            return getRepository().getEntityList(Request.class, params);
        } else {
            return null;
        }
    }

    @Override
    public Client saveClient(Client client) {
        return getRepository().saveEntity(client);
    }

    @Override
    public BaUnit makeRequest(String clientId, String nameFirstPart, String nameLastPart) {
        BaUnit baUnit = administrativeEJB.getBaUnitByCode(nameFirstPart, nameLastPart);
        Client client = getClient(clientId);
        
        if(baUnit == null || client == null){
            return null;
        }
        
        BigDecimal cost = calculateCost(client, baUnit);
        
        // Calculate request cost and register
        Request request = new Request();
        request.setClientId(clientId);
        request.setCost(cost);
        request.setNameFirstPart(nameFirstPart);
        request.setNameLastPart(nameLastPart);
        
        getRepository().saveEntity(request);
        
        client.setBalance(client.getBalance().subtract(cost));
        getRepository().saveEntity(client);
        
        return baUnit;
    }

    /** 
     * Emulated calculate function to calculate request cost for the client. 
     * Both client and BaUnit might be involved into calculation formula. 
     */
    private BigDecimal calculateCost(Client client, BaUnit baUnit){
        return BigDecimal.valueOf((Math.random() + 0.1)*10).round(new MathContext(2));
    }
}

