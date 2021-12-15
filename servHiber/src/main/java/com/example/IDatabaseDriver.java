package com.example;

import java.util.HashMap;
import java.util.List;

public interface IDatabaseDriver {
    public void create(Object obj);
    public <T> T read(Class<T> cls, Object primKey);
    public void update(Object obj);
    public void delete(Object obj);
    <T> List<T> getByCriteria(Class<T> cls, HashMap<String, Object> dict);
}
