package com.gfb.hotelHero.dao.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseDao<ENTITY_CLASS, PK_CLASS> {

    private static final Logger LOGGER = Logger.getLogger(BaseDao.class);

    private static int suffix;

    @Autowired
    protected SessionFactory sessionFactory;

    private Class getModelClass() throws IllegalAccessException, InstantiationException {
        return (
                (Class) (
                        (ParameterizedType) this.getClass().getGenericSuperclass()
                ).getActualTypeArguments()[0]
        ).newInstance().getClass();
    }

    @SuppressWarnings("unchecked")
    public List<ENTITY_CLASS> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String fullName = null;
            fullName = getModelClass().getName();

            String prefix = "_" + fullName.replaceAll("[\\W]", "_") + (++suffix);
            return session
                    .createQuery("SELECT " + prefix + " FROM " + fullName + " " + prefix)
                    .list();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public ENTITY_CLASS find(PK_CLASS primaryKey) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return (ENTITY_CLASS) session.get(getModelClass(), (Serializable) primaryKey);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return null;
    }

    public void add(ENTITY_CLASS entity) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(entity);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void edit(ENTITY_CLASS entity) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(entity);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void delete(ENTITY_CLASS entity) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.delete(entity);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void deleteById(PK_CLASS primaryKey) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            ENTITY_CLASS entity = find(primaryKey);
            session.delete(entity);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

}
