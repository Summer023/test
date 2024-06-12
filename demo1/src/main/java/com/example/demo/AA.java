package com.example.demo;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AA {

    @Autowired
    @Lazy
    CC cc;
    public final LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            // 缓存加载逻辑,可以通过查询数据库，获取经常访问且固定不变的数据
            return null;
        }
    });
}
