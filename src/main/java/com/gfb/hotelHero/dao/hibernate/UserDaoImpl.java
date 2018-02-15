package com.gfb.hotelHero.dao.hibernate;

import com.gfb.hotelHero.dao.UserDao;
import com.gfb.hotelHero.domain.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository(value = "userDao")
public class UserDaoImpl extends BaseDao<User, Long> implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public User findBy(String username) {
        /*Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Contact c = (Contact) session.createQuery("FROM Contact WHERE value = :contact")
                    .setParameter("contact", username).setMaxResults(1).getSingleResult();


            Contact lc = (Contact) session
                    .createQuery("SELECT c1 FROM Contact AS c1 INNER JOIN c1.user AS u2 " +
                            "WHERE u2.id = :uid AND c1.type = :type " +
                            "ORDER BY c1.id DESC")
                    .setParameter("uid", c.getUser().getId())
                    .setParameter("type", c.getType())
                    .setMaxResults(1).getSingleResult();

            if (!c.getId().equals(lc.getId())) return null;

            tx.commit();
            return c.getUser();
        } catch (NoResultException e) {
            if (tx != null) {
                tx.rollback();
            }
            return null;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }*/

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User u = (User) session.createQuery("FROM User AS u2 " +
                    "WHERE u2.email = :username OR u2.mobilePhone = :username")
                    .setParameter("username", username)
                    .setMaxResults(1)
                    .getSingleResult();
            tx.commit();
            return u;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return null;
    }

}
