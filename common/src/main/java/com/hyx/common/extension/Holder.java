package com.hyx.common.extension;

import lombok.Data;

/**
 * SPI机制持有类.
 *
 * @author hyx
 **/

@Data
public class Holder<T> {
    
    /**
     * 持有实例.
     */
    private volatile T value;
    
    public Holder() {}
    
    public Holder(T val) {
        this.value = val;
    }
}
