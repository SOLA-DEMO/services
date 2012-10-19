package org.sola.services.ejbs.external.repository.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.sola.services.common.repository.entities.AbstractVersionedEntity;

/**
 * Represents Request object, made by the client.
 */
@Table(name = "request", schema = "external")
public class Request extends AbstractVersionedEntity {
    
    public static final String QUERY_PARAMETER_CLIENTID = "clientId";
    public static final String QUERY_WHERE_BYCLIENTID = "client_id = "
            + "#{" + QUERY_PARAMETER_CLIENTID + "}";
    
    public Request(){
        super();
    }
    
    @Id
    @Column
    private String id;
    
    @Column(name="client_id")
    String clientId;
    
    @Column(name="name_firstpart")
    String nameFirstPart;
    
    @Column(name="name_lastpart")
    String nameLastPart;
    
    @Column
    Date date;
    
    @Column
    BigDecimal cost;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        id = id == null ? generateId() : id;
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameFirstPart() {
        return nameFirstPart;
    }

    public void setNameFirstPart(String nameFirstPart) {
        this.nameFirstPart = nameFirstPart;
    }

    public String getNameLastPart() {
        return nameLastPart;
    }

    public void setNameLastPart(String nameLastPart) {
        this.nameLastPart = nameLastPart;
    }
}

