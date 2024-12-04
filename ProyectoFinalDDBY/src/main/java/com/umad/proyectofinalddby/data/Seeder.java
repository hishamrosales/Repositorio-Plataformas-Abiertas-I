package com.umad.proyectofinalddby.data;

import com.umad.proyectofinalddby.dataContext.DataContext;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;

/**
 * Seeder class to populate initial data for testing.
 */
public class Seeder {

    private final Session session;
    private Transaction transaction;

    public Seeder() {
        session = DataContext.getOpenSession();
    }

    public void Seed() {
        try {
            transaction = session.beginTransaction();

            if (session.createQuery("from Director", Director.class).list().isEmpty()) {
                AddNewDirector("Woody", "Allen");
                AddNewDirector("Guillermo", "del Toro");
                AddNewDirector("Alfonso", "Cuarón");
            }

            if (session.createQuery("from Genre", Genre.class).list().isEmpty()) {
                AddNewGenre("Horror");
                AddNewGenre("Action");
                AddNewGenre("Science Fiction");
            }

            if (session.createQuery("from Movie", Movie.class).list().isEmpty()) {
                Director director = session.createQuery("from Director where id = :id", Director.class)
                        .setParameter("id", 1)
                        .uniqueResult();
                Genre genre = session.createQuery("from Genre where id = :id", Genre.class)
                        .setParameter("id", 1)
                        .uniqueResult();

                if (director != null && genre != null) {
                    AddNewMovie("Pinocho", director, genre, 2003, 120, 6);
                    AddNewMovie("Avengers", director, genre, 2005, 124, 3);
                }
            }

            if (session.createQuery("from Rent", Rent.class).list().isEmpty()) {
                Movie movie = session.createQuery("from Movie where id = :id", Movie.class)
                        .setParameter("id", 1)
                        .uniqueResult();
                Client client = session.createQuery("from Client where id = :id", Client.class)
                        .setParameter("id", 1)
                        .uniqueResult();
                Employee employee = session.createQuery("from Employee where id = :id", Employee.class)
                        .setParameter("id", 1)
                        .uniqueResult();
                if (movie != null && client != null && employee != null) {
                    AddNewRent(movie, client, employee);
                }
            }

            // Insert Clients if not already present
            if (session.createQuery("from Client", Client.class).list().isEmpty()) {
                AddNewClient("juan@gmail.com", "2 Pte #1000 Col Hogar", "Francisco", "Reyes", 30, 1234567890);
            }

            if (session.createQuery("from Employee", Employee.class).list().isEmpty()) {
                AddNewEmployee("Cajero", "josue1234", "josue@gmail.com", "Josué", "Salazar", 30, 1234567890);
                AddNewEmployee("Cajero", "1234", "yael@gmail.com", "Yael", "Rosales", 19, 1234567890);
            }

            transaction.commit();
            System.out.println("Data seeding completed successfully.");

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }


    private void AddNewDirector(String firstName, String lastName) {
        Director director = new Director();
        director.setFirstName(firstName);
        director.setLastName(lastName);
        session.persist(director);
    }

    private void AddNewGenre(String description) {
        Genre genre = new Genre();
        genre.setDescription(description);
        session.persist(genre);
    }

    private void AddNewMovie(String title, Director director, Genre genre, int releaseYear, int duration, int quantityAvailable) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseYear(releaseYear);
        movie.setDuration(duration);
        movie.setQuantityAvailable(quantityAvailable);
        movie.setDirector(director);
        movie.setGenre(genre);
        session.persist(movie);
    }

    private void AddNewRent(Movie movie, Client nameClient, Employee nameEmployee) {
        LocalDate rentDay = LocalDate.now();
        LocalDate returnDay = rentDay.plusDays(7);

        Rent rent = new Rent();
        rent.setMovie(movie);
        rent.setRentDay(rentDay);
        rent.setReturnDay(returnDay);
        rent.setClient(nameClient);
        rent.setEmployee(nameEmployee);
        session.persist(rent);
    }

    private void AddNewClient(String email, String address, String name, String lastName, int age, int phoneNumber) {
        Client client = new Client();
        client.setEmail(email);
        client.setAddress(address);
        client.setName(name);
        client.setLastName(lastName);
        client.setAge(age);
        client.setPhoneNumber(phoneNumber);
        session.persist(client);
    }

    private void AddNewEmployee(String jobPosition, String password, String email, String name, String lastName, int age, int phoneNumber) {
        Employee employee = new Employee();
        employee.setJobPosition(jobPosition);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setName(name);
        employee.setLastName(lastName);
        employee.setAge(age);
        employee.setPhoneNumber(phoneNumber);
        session.persist(employee);
    }
}
