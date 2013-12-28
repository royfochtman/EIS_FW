package com.musicbox.util.globalobject;

import com.musicbox.util.EntityClass;

import java.io.Serializable;

/**
 * A global-object can be sent as data over the websocket-protocol from server to client
 * or from client to server. It can be a instance of one of the database-entity-classes.
 *
 * @author David Wachs
 */
public abstract class GlobalObject implements Serializable {
    /**
     * id of the database-entity-object
     */
    private int id;
    /**
     * Is needed to find out later which database-entity-type is stored in the GlobalObject-variable
     */
    private EntityClass entityClass;

    public EntityClass getEntityClass() {
        return entityClass;
    }

    protected final void setEntityClass(EntityClass entityClass)
    {
        this.entityClass = entityClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /**
     * Checks if the attributes of the specific object have correct values
     * @return true, if all attributes have correct values
     */
    public abstract boolean isValid();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
