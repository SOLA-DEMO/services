/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sola.services.ejb.administrative.repository.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.sola.services.common.repository.entities.AbstractReadOnlyEntity;

@Table(schema = "administrative", name = "ba_unit")
public class BaUnitBasic extends AbstractReadOnlyEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "type_code")
    private String typeCode;
    @Column(name = "name")
    private String name;
    @Column(name = "name_firstpart")
    private String nameFirstpart;
    @Column(name = "name_lastpart")
    private String nameLastpart;
    @Column(name = "status_code", updatable = false)
    private String statusCode;
    @Column(name = "transaction_id", updatable = false)
    private String transactionId;
    
    public BaUnitBasic(){
        super();
    }
    
    public String getId() {
        id = id == null ? generateId() : id;
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        // Prevent changes to the status code if the value has been loaded from the database. 
        // Updates to the status code are made via the BaUnitStatusChanger
        if (isNew()) {
            this.statusCode = statusCode;
        }
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        // Prevent changes to the transaction id if the value has been loaded from the database.
        if (isNew()) {
            this.transactionId = transactionId;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFirstpart() {
        return nameFirstpart;
    }

    public void setNameFirstpart(String nameFirstpart) {
        this.nameFirstpart = nameFirstpart;
    }

    public String getNameLastpart() {
        return nameLastpart;
    }

    public void setNameLastpart(String nameLastpart) {
        this.nameLastpart = nameLastpart;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}