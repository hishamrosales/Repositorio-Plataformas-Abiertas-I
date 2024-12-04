/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umad.proyectofinalddby.controllers;

import com.umad.proyectofinalddby.data.Client;
import com.umad.proyectofinalddby.dataContext.DataContext;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author deban
 */
public class ClientsController extends BaseController<Client, Integer> {

    @Override
    public List<Client> GetAll() {
        List<Client> clients = null;
        try {
            clients = DataContext.getOpenSession().createQuery("from Client order by Id", Client.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (clients != null) {
            for (Client client : clients) {
                System.out.println(client);
            }
        }

        return clients;
    }

    @Override
    public Client GetById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Client Save(Client NEWrEGISTRY) {
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
    public Client Update(Client updateRegistry) {
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
    public Client Delete(Client deletedRegistry) {
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
    
    public Client GetClientByEmail (String email){
        Client client = null;
        
        try {
            client = DataContext.getOpenSession().createQuery("from Client where email = :email", Client.class).setParameter("email", email).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (client != null) {
            return client;
        } else {
            return null;
        }
    }


}
