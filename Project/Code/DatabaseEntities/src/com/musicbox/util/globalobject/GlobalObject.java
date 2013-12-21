package com.musicbox.util.globalobject;

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
    private Class dataClass;

    public Class getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class objectClass)
    {
        dataClass = objectClass;
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
