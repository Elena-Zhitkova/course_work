package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseDriver implements IDatabaseDriver {
    private SessionFactory sessionFactory = null;


    public DatabaseDriver(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public void create(Object obj) {
        Session session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.save(obj);
        transaction.commit();
        session.close();
    }
@Override
    public <T> T read(Class<T> cls, Object primKey) {
        Session session = this.sessionFactory.openSession();
        var transaction = session.beginTransaction();
        var readiedAccess = session.get(cls, (Serializable) primKey);
        transaction.commit();
        session.close();
        return readiedAccess;
    }
@Override
    public void update(Object obj) {
        Session session = this.sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.update(obj);
        transaction.commit();
        session.close();
    }
@Override
    public void delete(Object obj) {
        Session session = this.sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.delete(obj);
        transaction.commit();
        session.close();
    }
    @Override
    public <T> List<T> getByCriteria(Class<T> cls, HashMap<String,Object> dict)
    {
        var session = this.sessionFactory.openSession();
        var criteria = session.createCriteria(cls);
        for (var item : dict.entrySet()) {
            criteria.add(Restrictions.eq(item.getKey(), item.getValue()));
        }

        return criteria.list();
    }

}
