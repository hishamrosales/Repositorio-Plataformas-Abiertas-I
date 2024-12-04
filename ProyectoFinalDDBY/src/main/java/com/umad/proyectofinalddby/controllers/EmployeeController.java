package com.umad.proyectofinalddby.controllers;

import com.umad.proyectofinalddby.data.Employee;
import com.umad.proyectofinalddby.dataContext.DataContext;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author deban
 */
public class EmployeeController extends BaseController<Employee, Integer> {

    public List<Employee> GetAll() {
        List<Employee> employees = null;
        try {
            employees = DataContext.getOpenSession().createQuery("from Employee order by Id", Employee.class).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        if (employees != null) {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }

        return employees;
    }

    @Override
    public Employee GetById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Employee Save(Employee NEWrEGISTRY) {
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
    public Employee Update(Employee updateRegistry) {
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
    public Employee Delete(Employee deletedRegistry) {
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

}
