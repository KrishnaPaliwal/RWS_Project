package com.online.electronic.shopping.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.online.electronic.shopping.model.Product;
import com.online.electronic.shopping.util.HibernateUtil;

public class BuyerDao {
	
    protected final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public List<Product> getProductsByCategory(String category){
    	List<Product> list = new ArrayList<Product>();
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Product e where e.category = :CATEGORY");
            query.setParameter("CATEGORY", category);
            list = (List<Product>)query.list();
            session.getTransaction().commit();
        }
        catch (Exception exception) {
           if (session != null) {
               session.getTransaction().rollback();
           }
        }
        finally {
            if (session != null) {
               session.close();
            }
        }

        return list;
    }

	public List<Product> getProductsByPriceRange(String min, String max) {
    	List<Product> list = new ArrayList<Product>();
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Product e where e.price between :MIN and :MAX");
            query.setParameter("MIN", Long.parseLong(min));
            query.setParameter("MAX", Long.parseLong(max));

            list = (List<Product>)query.list();
            session.getTransaction().commit();
        }
        catch (Exception exception) {
           if (session != null) {
               session.getTransaction().rollback();
           }
        }
        finally {
            if (session != null) {
               session.close();
            }
        }

        return list;
    }
	
	public List<Product> getAllProd() {

		List<Product> entity = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Product");
            entity = (List<Product>)query.list();
            Hibernate.initialize(entity);
            session.getTransaction().commit();
        }
        catch (Exception exception) {
           if (session != null) {
               session.getTransaction().rollback();
           }
        }
        finally {
            if (session != null) {
               session.close();
            }
        }

        return entity;
    
	}
}
