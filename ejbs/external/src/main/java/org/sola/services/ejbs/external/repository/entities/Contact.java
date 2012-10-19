package org.sola.services.ejbs.external.repository.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.sola.services.common.repository.entities.AbstractVersionedEntity;

/**
 * Represents contact person information, relevant to the client.
 */
@Table(name = "contact", schema = "external")
public class Contact extends AbstractVersionedEntity {

    public Contact(){
        super();
    }
    
    @Id
    @Column
    private String id;
    
    @Column(name="client_id")
    String clientId;
    
    @Column
    String name;
    
    @Column(name="phone_number")
    String phoneNumber;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getId() {
        id = id == null ? generateId() : id;
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

