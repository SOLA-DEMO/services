package org.sola.services.ejbs.external.repository.entities;

import javax.persistence.Table;
import org.sola.services.common.repository.DefaultSorter;
import org.sola.services.common.repository.entities.AbstractCodeEntity;

/**
 * This entity represents client type reference data
 */
@Table(name = "client_type", schema = "external")
@DefaultSorter(sortString="display_value")
public class ClientType extends AbstractCodeEntity {
    public ClientType(){
        super();
    }
}

