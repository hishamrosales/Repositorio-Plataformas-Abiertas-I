package com.umad.proyectofinalddby.controllers;

import com.umad.proyectofinalddby.data.Client;
import com.umad.proyectofinalddby.data.Director;
import com.umad.proyectofinalddby.data.Employee;
import com.umad.proyectofinalddby.data.Genre;
import com.umad.proyectofinalddby.data.Movie;
import com.umad.proyectofinalddby.data.Person;
import com.umad.proyectofinalddby.data.Rent;
import com.umad.proyectofinalddby.dataContext.DataContext;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author deban
 */
public class DirectorController extends BaseController<Director, Integer> {

    @Override
    public List<Director> GetAll() {
        List<Director> directors = null;

        try {
            directors = DataContext.getOpenSession().createQuery("from Director order by Id", Director.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (directors != null) {
            for (Director director : directors) {
                System.out.println(director);
            }
        }

        return directors;
    }

    @Override
    public Director GetById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Director Save(Director NEWrEGISTRY) {
        Transaction transaction = null;
        try {
            transaction = DataContext.getOpenSession().beginTransaction();
            DataContext.getOpenSession().persist(NEWrEGISTRY);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return NEWrEGISTRY;
    }

    @Override
    public Director Update(Director updateRegistry) {
        Transaction transaction = null;
        try {
            transaction = DataContext.getOpenSession().beginTransaction();
            DataContext.getOpenSession().merge(updateRegistry);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return updateRegistry;
    }

    @Override
    public Director Delete(Director deletedRegistry) {
        Transaction transaction = null;
        try {
            transaction = DataContext.getOpenSession().beginTransaction();
            DataContext.getOpenSession().remove(deletedRegistry);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return deletedRegistry;
    }
    
    public Director GetDirectorByName (String firstName){
        Director director = null;
        
        try {
            director = DataContext.getOpenSession().createQuery("from Director where firstName = :firstName", Director.class).setParameter("firstName", firstName).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (director != null) {
            return director;
        } else {
            return null;
        }
    }

}
