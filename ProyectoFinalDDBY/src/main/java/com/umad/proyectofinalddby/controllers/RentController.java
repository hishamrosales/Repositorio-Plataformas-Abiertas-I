/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umad.proyectofinalddby.controllers;

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
public class RentController extends BaseController<Rent, Integer> {

    public List<Rent> GetAll() {
        List<Rent> rents = null;
        try {
            rents = DataContext.getOpenSession().createQuery("from Rent order by Id", Rent.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (rents != null) {
            for (Rent rent : rents) {
                System.out.println(rent);
            }
        }

        return rents;
    }

    @Override
    public Rent GetById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Rent Save(Rent NEWrEGISTRY) {
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
    public Rent Update(Rent updateRegistry) {
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
    public Rent Delete(Rent deletedRegistry) {
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

    public Rent GetRentByClient(String email) {
        Rent rent = null;

        try {
            rent = DataContext.getOpenSession()
                    .createQuery("from Rent r where r.client.email = :email", Rent.class)
                    .setParameter("email", email).getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (rent != null) {
            return rent;
        } else {
            return null;
        }
    }

}
