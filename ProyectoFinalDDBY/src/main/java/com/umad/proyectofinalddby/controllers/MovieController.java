/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umad.proyectofinalddby.controllers;

import com.umad.proyectofinalddby.data.Movie;
import com.umad.proyectofinalddby.dataContext.DataContext;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author deban
 */
public class MovieController extends BaseController<Movie, Integer> {

    public List<Movie> GetAll() {
        List<Movie> movies = null;
        try {
            movies = DataContext.getOpenSession().createQuery("from Movie order by Id", Movie.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (movies != null) {
            for (Movie movie : movies) {
                System.out.println(movie);
            }
        }
        return movies;
    }

    @Override
    public Movie GetById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Movie Save(Movie NEWrEGISTRY) {
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
    public Movie Update(Movie updateRegistry) {
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
    public Movie Delete(Movie deletedRegistry) {
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

    public Movie GetMovieByName(String name) {
        Movie movie = null;
        try {
            movie = DataContext.getOpenSession().createQuery("from Movie where title = :title", Movie.class).setParameter("title", name).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (movie != null) {
            return movie;
        } else {
            return null;
        }
    }
}
