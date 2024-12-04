/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umad.proyectofinalddby.controllers;

import com.umad.proyectofinalddby.data.Person;
import com.umad.proyectofinalddby.dataContext.DataContext;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author deban
 */
public class PersonController {
    public List<Person> GetAll() {
        List<Person> persons = null;
        try {
            persons = DataContext.getOpenSession().createQuery("from Person order by Id", Person.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return persons;
    }

    
}
