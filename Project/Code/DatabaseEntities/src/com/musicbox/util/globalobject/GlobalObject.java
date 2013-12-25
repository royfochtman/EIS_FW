package com.musicbox.util.globalobject;

import com.musicbox.util.EntityClass;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class GlobalObject implements Serializable {
    private int id;

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
     * Checks if the attributes of the Object have correct values
     * @return true, if all attributes have correct values
     */
    public abstract boolean isValid();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
