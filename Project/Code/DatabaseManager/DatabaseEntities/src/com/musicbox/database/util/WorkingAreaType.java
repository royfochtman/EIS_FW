package com.musicbox.database.util;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 07.12.13
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public enum WorkingAreaType implements Serializable{
    PRIVATE{
        public String toString(){
            return "private";
        }
    },
    PUBLIC{
        public String toString(){
            return "public";
        }
    };
}
