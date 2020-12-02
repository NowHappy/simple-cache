package com.example.simplecache.domain.cache;

import lombok.Data;

@Data
public class Cache {

    Integer howManyUsed;

    Object value;

    public Cache(Object value) {
        this.howManyUsed = 1;
        this.value = value;
    }

}
