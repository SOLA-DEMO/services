package org.sola.services.ejbs.external.repository.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.sola.services.common.repository.ChildEntityList;
import org.sola.services.common.repository.entities.AbstractVersionedEntity;

/**
 * Represents Client object.
 */
@Table(name = "client", schema = "external")
public class Client extends AbstractVersionedEntity {
    
    public static final String QUERY_PARAMETER_USERID = "userId";
    public static final String QUERY_WHERE_BYUSERID = "user_id = "
            + "#{" + QUERY_PARAMETER_USERID + "}";
    
    public Client(){
        super();
    }
    
    @Id
    @Column
    private String id;
    
    @Column(name="user_id")
    String userId;
    
    @Column
    String name;
    
    @Column(name="type_code")
    String typeCode;
    
    @Column
    BigDecimal balance;
    
    @ChildEntityList(parentIdField = "clientId")
    List<Contact> contacts;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Contact> getContacts() {
        if(contacts == null){
            contacts = new ArrayList<Contact>();
        }
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
} 

