package com.online.electronic.shopping.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.online.electronic.shopping.model.Product;
import com.online.electronic.shopping.util.HibernateUtil;


public class SellerDao {
    
    protected final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    public Product get(Long id){
    	Product entity = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Product e where e.id = :ID");
            query.setParameter("ID", id);
            entity = (Product) query.uniqueResult();
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
    
    public boolean create(Product entity) {
        Boolean success = false;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            success = true;
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

        return success;
    }
    
    public boolean update(Product entity) {
        Boolean success = false;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
            success = true;
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

        return success;
    }
    
    public boolean delete(Product entity) {
        Boolean success = false;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            success = true;
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

        return success;
    }

	public List<Product> getProductsForSelectedIds(String[] idArraay) {
    	List<Product> entity = null;
        Session session = null;
        String in = "";
        StringBuilder idsBuilder = new StringBuilder();
        
        for(String id : idArraay)
        {
        	idsBuilder.append(id).append(",");
        }
        idsBuilder.deleteCharAt(idsBuilder.length() - 1);
        in = idsBuilder.toString();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Product e where e.id in ("+in+")");
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
