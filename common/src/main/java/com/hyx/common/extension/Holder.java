package com.hyx.common.extension;

import lombok.Data;

/**
 * SPI机制持有类.
 *
 * @author hyx
 **/

@Data
public class Holder<T> {
    private volatile T value;
    
    public void set(T value) {
        this.value = value;
    }
    
    public T get() {
        return value;
    }
}
