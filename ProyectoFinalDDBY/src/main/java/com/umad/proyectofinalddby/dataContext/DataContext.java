
package com.umad.proyectofinalddby.dataContext;

import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author deban
 */
public class DataContext {
    
    private static final SessionFactory sessionFactory;
    private static Session session;

    static {
        //Manejo Excepciones        
        try {
            // Create the SessionFactory from the configuration file
            sessionFactory = new Configuration().configure("database/hibernate.cfg.xml").buildSessionFactory();
            session = sessionFactory.openSession();
            
        } catch (HibernateException ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    public static Session getOpenSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        }

        return session;
    }
}

    
