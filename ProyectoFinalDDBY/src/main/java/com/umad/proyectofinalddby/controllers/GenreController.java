/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umad.proyectofinalddby.controllers;

import com.umad.proyectofinalddby.data.Genre;
import com.umad.proyectofinalddby.dataContext.DataContext;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author deban
 */
public class GenreController extends BaseController<Genre, Integer> {

    public List<Genre> GetAll() {
        List<Genre> genres = null;
        try {
            genres = DataContext.getOpenSession().createQuery("from Genre order by Id", Genre.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (genres != null) {
            for (Genre genre : genres) {
                System.out.println(genre);
            }
        }

        return genres;
    }

    @Override
    public Genre GetById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Genre Save(Genre NEWrEGISTRY) {
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
    public Genre Update(Genre updateRegistry) {
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
    public Genre Delete(Genre deletedRegistry) {
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
    
    public Genre GetGenreByDescription (String description){
        Genre genre = null;
        
        try {
            genre = DataContext.getOpenSession().createQuery("from Genre where description = :description", Genre.class).setParameter("description", description).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (genre != null) {
            return genre;
        } else {
            return null;
        }
    }

}
