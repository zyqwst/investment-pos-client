/**
* @{#} ParamsEntity.java Created on 2016-6-22 下午3:06:52
*
* Copyright (c) 2016 by SHUANGYI software.
*/
package com.sy.investment.domain.entity;

public class ParamsEntity {
    
    public ParamsEntity(int type, Object value) {
        super();
        this.type = type;
        this.value = value;
    }

    private int type;
    
    private Object value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
