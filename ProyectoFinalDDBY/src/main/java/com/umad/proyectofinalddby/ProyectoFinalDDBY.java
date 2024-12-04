package com.umad.proyectofinalddby;
import com.umad.proyectofinalddby.controllers.ClientsController;
import com.umad.proyectofinalddby.controllers.DirectorController;
import com.umad.proyectofinalddby.controllers.EmployeeController;
import com.umad.proyectofinalddby.controllers.GenreController;
import com.umad.proyectofinalddby.controllers.MovieController;
import com.umad.proyectofinalddby.controllers.PersonController;
import com.umad.proyectofinalddby.controllers.RentController;
import com.umad.proyectofinalddby.data.Client;
import com.umad.proyectofinalddby.data.Director;
import com.umad.proyectofinalddby.data.Employee;
import com.umad.proyectofinalddby.data.Genre;
import com.umad.proyectofinalddby.data.Movie;
import com.umad.proyectofinalddby.data.Person;
import com.umad.proyectofinalddby.data.Rent;
import com.umad.proyectofinalddby.data.Seeder;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author deban
 */
public class ProyectoFinalDDBY {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Seeder seeder = new Seeder();
        seeder.Seed();

        ClientsController clientsController = new ClientsController();
        DirectorController directorController = new DirectorController();
        EmployeeController employeeController = new EmployeeController();
        GenreController genreController = new GenreController();
        MovieController movieController = new MovieController();
        PersonController personController = new PersonController();
        RentController rentController = new RentController();

        List<Movie> movies = movieController.GetAll();
        List<Director> directors = directorController.GetAll();
        List<Client> clients = clientsController.GetAll();
        List<Employee> employees = employeeController.GetAll();
        List<Genre> genres = genreController.GetAll();
        List<Rent> rents = rentController.GetAll();

        Client cliente = new Client();
        Director directore = new Director();
        Genre genre = new Genre();
        Person person = new Person();
        Rent rentass = new Rent();

        LocalDate rentDay = LocalDate.now();
        LocalDate returnDay = rentDay.plusDays(7);

        boolean loginExitoso = false;
        Employee nombreEmpleado = null;
        do {
            System.out.println("Bienvenido");
            System.out.println("Usuario: ");
            String username = scanner.nextLine();

            System.out.println("Contrasena: ");
            String password = scanner.nextLine();

            for (Employee employee : employees) {
                if (employee.getName().equals(username)) {
                    if (employee.getPassword().equals(password)) {
                        nombreEmpleado = employee;
                        loginExitoso = true;
                        System.out.println("Login Exitoso\nBienvenido " + username + ".");
                    } else {
                        System.out.println("Contrasena incorrecta");

                    }
                    break;
                }
            }

            if (!loginExitoso) {
                System.out.println("El usuario no existe");

            }
        } while (loginExitoso == false);
        int menu;

        do {
            System.out.println("============MENU============\n");
            System.out.println("===========RENTAS===========");
            System.out.println("1. Rentar pelicula");
            System.out.println("2. Regresar pelicula");
            System.out.println("========MAS OPCIONES========");
            System.out.println("3. Clientes");
            System.out.println("4. Directores");
            System.out.println("5. Generos");
            System.out.println("6. Peliculas");
            System.out.println("7. Salir");
            menu = scanner.nextInt();

            switch (menu) {
                case 1:
                    scanner.nextLine();
                    Client nombreCliente = null;

                    System.out.println("Ingrese el correo del cliente");
                    String clientEmail = scanner.nextLine();

                    nombreCliente = clientsController.GetClientByEmail(clientEmail);

                    if (nombreCliente != null) {

                        Rent rentaAsignada = null;

                        rentaAsignada = rentController.GetRentByClient(clientEmail);

                        if (rentaAsignada == null) {

                            boolean loginPeli = false;
                            Movie nombrePeli = null;

                            do {
                                loginPeli = false;
                                System.out.println("Escribe el nombre de la película que desea rentar");

                                if (movies != null) {
                                    for (Movie movie : movies) {
                                        System.out.println("Peliculas disponibles: " + movie.getTitle());
                                    }
                                } else {
                                    System.out.println("No hay peliculas existentes");
                                }

                                String namePeli = scanner.nextLine();
                                nombrePeli = movieController.GetMovieByName(namePeli);

                                if (nombrePeli != null) {
                                    break;
                                } else {
                                    System.out.println("Nombre de película no válida");
                                }

                            } while (loginPeli != true);

                            Rent rentSave = new Rent();
                            rentSave.setMovie(nombrePeli);
                            rentSave.setRentDay(rentDay);
                            rentSave.setReturnDay(returnDay);
                            rentSave.setClient(nombreCliente);
                            rentSave.setEmployee(nombreEmpleado);

                            rentController.Save(rentSave);

                            nombrePeli.decrementQuantity();

                            System.out.println("Renta autorizada.\nFecha de entrega:" + rentDay + "\nFecha de retorno: " + returnDay);
                            scanner.nextLine();

                        } else {
                            System.out.println("El cliente ya tiene asignada una renta de película");
                            break;
                        }

                    } else {
                        System.out.println("El cliente no esta dado de alta");
                    }

                    break;

                case 2:
                    boolean rentEncontrada = false;
                    Rent rentToReturn = null;
                    scanner.nextLine();
                    do {
                        System.out.println("Ingrese el correo del cliente que devolverá la película");
                        String clientEmailDevol = scanner.nextLine();

                        rentToReturn = rentController.GetRentByClient(clientEmailDevol);

                        if (rentToReturn != null) {
                            break;
                        } else {
                            System.out.println("No se encontraron rentas asociadas a este correo");
                        }
                    } while (rentEncontrada == false);

                    System.out.println("Pelicula rentada: " + rentToReturn.getMovie().getTitle());
                    System.out.println("Fecha de entrega: " + rentToReturn.getRentDay());
                    System.out.println("Fecha de retorno esperada: " + rentToReturn.getReturnDay());

                    System.out.println("Desea confirmar el retorno de la pelicula s/n");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("s")) {
                        rentToReturn.getMovie().incrementQuantity();

                        rentController.Delete(rentToReturn);

                        System.out.println("La pelicula ha sido retorna con exito");
                        scanner.nextLine();
                    } else {
                        System.out.println("El retorno de la pelicula no ha sido exitoso");
                        scanner.nextLine();
                    }

                    break;
                case 3:
                    //clientes

                    System.out.println("===========CLIENTES===========");
                    System.out.println("1. Agregar");
                    System.out.println("2. Eliminar");
                    System.out.println("3. Editar");
                    System.out.println("4. Consultar");
                    int menuC = scanner.nextInt();
                    scanner.nextLine();

                    switch (menuC) {
                        case 1:

                            String nuevoNombreCliente;
                            String nuevoApellidoCliente;
                            int nuevoEdadCliente;
                            long nuevoTelefonoCliente;
                            String nuevoEmailCliente;
                            String nuevoDireccionCliente;

                            System.out.println("Ingrese los siguientes datos para dar de alta un nuevo cliente");

                            do {
                                System.out.println("Nombre: ");
                                nuevoNombreCliente = scanner.nextLine();
                            } while (nuevoNombreCliente == null);

                            do {
                                System.out.println("Apellido: ");
                                nuevoApellidoCliente = scanner.nextLine();
                            } while (nuevoApellidoCliente == null);

                            do {
                                System.out.println("Edad: ");
                                nuevoEdadCliente = scanner.nextInt();
                            } while (nuevoEdadCliente < 18);

                            do {
                                System.out.println("Numero de telefono: ");
                                nuevoTelefonoCliente = scanner.nextLong();
                            } while (nuevoTelefonoCliente < 1000000000);
                            scanner.nextLine();
                            do {
                                System.out.println("Email: ");
                                nuevoEmailCliente = scanner.nextLine();
                            } while (nuevoEmailCliente == null);

                            do {
                                System.out.println("Direccion: ");
                                nuevoDireccionCliente = scanner.nextLine();
                            } while (nuevoDireccionCliente == null);

                            Client clientNuevo = new Client();
                            clientNuevo.setEmail(nuevoEmailCliente);
                            clientNuevo.setAddress(nuevoDireccionCliente);
                            clientNuevo.setName(nuevoNombreCliente);
                            clientNuevo.setLastName(nuevoApellidoCliente);
                            clientNuevo.setAge(nuevoEdadCliente);
                            clientNuevo.setPhoneNumber(nuevoTelefonoCliente);

                            clientsController.Save(clientNuevo);
                            System.out.println("Nuevo cliente creado");
                            scanner.nextLine();

                            break;
                        case 2:

                            System.out.println("Escriba el email del cliente que desea eliminar:");

                            clientsController.GetAll();
                            scanner.nextLine();

                            String emailClienteDelete = scanner.nextLine();
                            Client clientEliminar = null;

                            clientEliminar = clientsController.GetClientByEmail(emailClienteDelete);

                            if (clientEliminar == null) {
                                System.out.println("Cliente no encontrado");
                            } else {
                                clientsController.Delete(clientEliminar);
                                System.out.println("Cliente eliminado");
                                scanner.nextLine();
                            }
                            break;

                        case 3:

                            String editNombreCliente;
                            String editApellidoCliente;
                            int editEdadCliente;
                            long editTelefonoCliente;
                            String editEmailCliente;
                            String editDireccionCliente;
                            Client clientToEdit = null;

                            System.out.println("Ingrese el email del cliente que desea editar:");

                            clientsController.GetAll();
                            String emailEditar = scanner.nextLine();

                            clientToEdit = clientsController.GetClientByEmail(emailEditar);

                            if (clientToEdit != null) {
                                do {
                                    System.out.println("Nombre: ");
                                    editNombreCliente = scanner.nextLine();
                                } while (editNombreCliente == null);

                                do {
                                    System.out.println("Apellido: ");
                                    editApellidoCliente = scanner.nextLine();
                                } while (editApellidoCliente == null);

                                do {
                                    System.out.println("Edad: ");
                                    editEdadCliente = scanner.nextInt();
                                } while (editEdadCliente < 18);
                                scanner.nextLine();
                                do {
                                    System.out.println("Numero de telefono: ");
                                    editTelefonoCliente = scanner.nextLong();
                                } while (editTelefonoCliente < 1000000000);
                                scanner.nextLine();
                                do {
                                    System.out.println("Email: ");
                                    editEmailCliente = scanner.nextLine();
                                } while (editEmailCliente == null);

                                do {
                                    System.out.println("Direccion: ");
                                    editDireccionCliente = scanner.nextLine();
                                } while (editDireccionCliente == null);

                                clientToEdit.setName(editNombreCliente);
                                clientToEdit.setLastName(editApellidoCliente);
                                clientToEdit.setAge(editEdadCliente);
                                clientToEdit.setPhoneNumber(editTelefonoCliente);
                                clientToEdit.setEmail(editEmailCliente);
                                clientToEdit.setAddress(editDireccionCliente);
                                clientsController.Update(clientToEdit);
                                System.out.println("Cliente actualizado");
                                scanner.nextLine();
                            } else {
                                System.out.println("Cliente no encontrado");
                                scanner.nextLine();
                            }

                            break;

                        case 4:
                            clientsController.GetAll();
                            break;
                    }
                    break;

                case 4:
                    //directores

                    System.out.println("===========DIRECTORES===========");
                    System.out.println("1. Agregar");
                    System.out.println("2. Eliminar");
                    System.out.println("3. Editar");
                    System.out.println("4. Consultar");
                    int menuD = scanner.nextInt();
                    scanner.nextLine();
                    switch (menuD) {
                        case 1:

                            String nuevoNombreDirector;
                            String nuevoApellidoDirector;

                            System.out.println("Ingrese los siguientes datos para dar de alta un nuevo director");

                            do {
                                System.out.println("Nombre: ");
                                nuevoNombreDirector = scanner.nextLine();
                            } while (nuevoNombreDirector == null);

                            do {
                                System.out.println("Apellido: ");
                                nuevoApellidoDirector = scanner.nextLine();
                            } while (nuevoApellidoDirector == null);

                            Director directorNuevo = new Director();
                            directorNuevo.setFirstName(nuevoNombreDirector);
                            directorNuevo.setLastName(nuevoApellidoDirector);
                            directorController.Save(directorNuevo);

                            System.out.println("Nuevo director creado");
                            scanner.nextLine();

                            break;
                        case 2:
                            Director directorEliminar = null;
                            boolean directorEliminado = false;
                            do {
                                System.out.println("Escriba el primer nombre del director que desea eliminar:");

                                directorController.GetAll();
                                String nombreDirector = scanner.nextLine();

                                directorEliminar = directorController.GetDirectorByName(nombreDirector);

                                if (directorEliminar != null) {
                                    break;
                                } else {
                                    System.out.println("No se ha encontrado el director");
                                }

                            } while (directorEliminado == false);

                            System.out.println("Desea confirmar la eliminacion del director? s/n");
                            String confirmDirector = scanner.nextLine();

                            if (confirmDirector.equalsIgnoreCase("s")) {
                                directorController.Delete(directorEliminar);

                                System.out.println("Director eliminado");
                                scanner.nextLine();
                            } else {
                                System.out.println("No se ha eliminado el director");
                                scanner.nextLine();
                            }

                            break;

                        case 3:

                            String editNombreDirector;
                            String editApellidoDirector;

                            System.out.println("Ingrese el primer nombre del director que desea editar:");

                            directorController.GetAll();

                            String nombreEditar = scanner.nextLine();
                            Director directorToEdit = null;
                            directorToEdit = directorController.GetDirectorByName(nombreEditar);

                            if (directorToEdit != null) {
                                do {
                                    System.out.println("Nombre: ");
                                    editNombreDirector = scanner.nextLine();
                                } while (editNombreDirector == null);

                                do {
                                    System.out.println("Apellido: ");
                                    editApellidoDirector = scanner.nextLine();
                                } while (editApellidoDirector == null);

                                directorToEdit.setFirstName(editNombreDirector);
                                directorToEdit.setLastName(editApellidoDirector);

                                directorController.Update(directorToEdit);
                                System.out.println("Director actualizado");
                                scanner.nextLine();
                            } else {
                                System.out.println("Director no encontrado");
                                scanner.nextLine();
                            }

                            break;

                        case 4:
                            directorController.GetAll();
                            break;
                    }

                    break;
                case 5:
                    //generos

                    System.out.println("===========GENEROS===========");
                    System.out.println("1. Agregar");
                    System.out.println("2. Eliminar");
                    System.out.println("3. Editar");
                    System.out.println("4. Consultar");
                    int menuG = scanner.nextInt();
                    scanner.nextLine();
                    switch (menuG) {
                        case 1:

                            String nuevoDescripcionGenero;

                            System.out.println("Ingrese los siguientes datos para dar de alta un nuevo genero");

                            do {
                                System.out.println("Nombre: ");
                                nuevoDescripcionGenero = scanner.nextLine();
                            } while (nuevoDescripcionGenero == null);

                            Genre genreNuevo = new Genre();
                            genreNuevo.setDescription(nuevoDescripcionGenero);
                            genreController.Save(genreNuevo);

                            System.out.println("Nuevo genero creado");
                            scanner.nextLine();

                            break;
                        case 2:
                            System.out.println("Escriba el genero que desea eliminar:");

                            genreController.GetAll();

                            String nombreGenero = scanner.nextLine();
                            Genre genreEliminar = null;

                            genreEliminar = genreController.GetGenreByDescription(nombreGenero);

                            if (genreEliminar == null) {
                                System.out.println("Genero no encontrado");
                                scanner.nextLine();
                            } else {
                                genreController.Delete(genreEliminar);
                                System.out.println("Genero eliminado");
                                scanner.nextLine();
                            }
                            break;

                        case 3:

                            String editDescripcionGenero;

                            System.out.println("Ingrese el genero que desea editar:");

                            genreController.GetAll();
                            String generoEditar = scanner.nextLine();
                            Genre genreToEdit = null;
                            genreToEdit = genreController.GetGenreByDescription(generoEditar);

                            if (genreToEdit != null) {
                                do {
                                    System.out.println("Nombre: ");
                                    editDescripcionGenero = scanner.nextLine();
                                } while (editDescripcionGenero == null);

                                genreToEdit.setDescription(editDescripcionGenero);

                                genreController.Update(genreToEdit);
                                System.out.println("Genero actualizado");
                                scanner.nextLine();
                            } else {
                                System.out.println("Genero no encontrado");
                                scanner.nextLine();
                            }

                            break;

                        case 4:
                            genreController.GetAll();
                            break;
                    }

                    break;
                case 6:
                    //peliculas

                    System.out.println("===========PELICULAS===========");
                    System.out.println("1. Agregar");
                    System.out.println("2. Eliminar");
                    System.out.println("3. Editar");
                    System.out.println("4. Consultar");
                    int menuP = scanner.nextInt();
                    scanner.nextLine();
                    switch (menuP) {
                        case 1:

                            String nuevoTituloPelicula;
                            int nuevoReleaseYearPelicula;
                            int nuevoDuracionPelicula;
                            int nuevoExistenciaPelicula;
                            Director directorPeli = null;
                            Genre genrePeli = null;

                            String directorPelicula;
                            String genrePelicula;

                            System.out.println("Ingrese los siguientes datos para dar de alta una nueva pelicula");

                            do {
                                System.out.println("Titulo: ");
                                nuevoTituloPelicula = scanner.nextLine();
                            } while (nuevoTituloPelicula == null);

                            do {
                                System.out.println("Año de lanzamiento: ");
                                nuevoReleaseYearPelicula = scanner.nextInt();
                            } while (nuevoReleaseYearPelicula < 1980);

                            do {
                                System.out.println("Durecion (en minutos): ");
                                nuevoDuracionPelicula = scanner.nextInt();
                            } while (nuevoDuracionPelicula < 60);
                            scanner.nextLine();

                            do {
                                System.out.println("Numero de existencia: ");
                                nuevoExistenciaPelicula = scanner.nextInt();
                            } while (nuevoExistenciaPelicula < 1);
                            scanner.nextLine();

                            do {
                                System.out.println("Escriba el primer nombre del director: ");
                                System.out.println("Directores creados:");
                                directorController.GetAll();

                                directorPelicula = scanner.nextLine();
                                directorPeli = directorController.GetDirectorByName(directorPelicula);

                                if (directorPeli != null) {
                                    break;
                                } else {
                                    System.out.println("Director no encontrado");
                                }

                            } while (directorPeli == null);

                            boolean generoAgregado = false;
                            do {
                                System.out.println("Genero: ");
                                System.out.println("Generos creados");

                                genreController.GetAll();

                                genrePelicula = scanner.nextLine();
                                genrePeli = genreController.GetGenreByDescription(genrePelicula);

                                if (genrePeli != null) {
                                    break;
                                } else {
                                    System.out.println("Genero no encontrado");
                                }

                            } while (genrePelicula == null && generoAgregado == false);

                            Movie movieNueva = new Movie();
                            movieNueva.setTitle(nuevoTituloPelicula);
                            movieNueva.setReleaseYear(nuevoReleaseYearPelicula);
                            movieNueva.setDuration(nuevoDuracionPelicula);
                            movieNueva.setQuantityAvailable(nuevoExistenciaPelicula);
                            movieNueva.setDirector(directorPeli);
                            movieNueva.setGenre(genrePeli);
                            movieController.Save(movieNueva);

                            System.out.println("Nueva pelicula creada");
                            scanner.nextLine();

                            break;
                        case 2:
                            Movie movieEliminar = null;

                            System.out.println("Escriba el nombre de la pelicula que desea eliminar:");

                            movieController.GetAll();

                            String nombrePelicula = scanner.nextLine();

                            movieEliminar = movieController.GetMovieByName(nombrePelicula);

                            if (movieEliminar == null) {
                                System.out.println("No se ha encontrado el nombre de esa pelicula");
                                scanner.nextLine();
                            } else {
                                movieController.GetMovieByName(nombrePelicula);
                                System.out.println("Pelicula eliminada");
                                scanner.nextLine();
                            }

                            break;
                        case 3:
                            String editTituloPelicula;
                            int editReleaseYearPelicula;
                            int editDuracionPelicula;
                            int editExistenciaPelicula;
                            Director editDirectorPeli = null;
                            Genre editGenrePeli = null;

                            String editDirectorPelicula;
                            String editGenrePelicula;

                            System.out.println("Ingrese el titulo de la pelicula que desea editar: ");

                            movieController.GetAll();

                            String tituloEditar = scanner.nextLine();
                            Movie movieToEdit = null;

                            movieToEdit = movieController.GetMovieByName(tituloEditar);

                            if (movieToEdit != null) {

                                System.out.println("Ingrese los siguientes datos para editar la pelicula");

                                do {
                                    System.out.println("Titulo: ");
                                    editTituloPelicula = scanner.nextLine();
                                } while (editTituloPelicula == null);

                                do {
                                    System.out.println("Año de lanzamiento: ");
                                    editReleaseYearPelicula = scanner.nextInt();
                                } while (editReleaseYearPelicula < 1980);
                                scanner.nextLine();

                                do {
                                    System.out.println("Durecion (en minutos): ");
                                    editDuracionPelicula = scanner.nextInt();
                                } while (editDuracionPelicula < 60);
                                scanner.nextLine();

                                do {
                                    System.out.println("Numero de existencia: ");
                                    editExistenciaPelicula = scanner.nextInt();
                                } while (editExistenciaPelicula < 1);
                                scanner.nextLine();

                                boolean directorEditado = false;
                                do {
                                    System.out.println("Escriba el primer nombre del director: ");
                                    System.out.println("Directores creados");

                                    directorController.GetAll();

                                    editDirectorPelicula = scanner.nextLine();
                                    editDirectorPeli = directorController.GetDirectorByName(editDirectorPelicula);

                                    if (editDirectorPeli != null) {
                                        System.out.println("Director editado");
                                        break;
                                    } else {
                                        System.out.println("Director no encontrado");
                                    }

                                } while (editDirectorPelicula == null && directorEditado == false);

                                boolean generoEditado = false;
                                do {
                                    System.out.println("Genero: ");
                                    System.out.println("Generos creados");

                                    genreController.GetAll();

                                    editGenrePelicula = scanner.nextLine();
                                    editGenrePeli = genreController.GetGenreByDescription(editGenrePelicula);

                                    if (editGenrePeli != null) {
                                        System.out.println("Genero editado");
                                        break;
                                    } else {
                                        System.out.println("Genero no encontrado");
                                    }

                                } while (editGenrePelicula == null && generoEditado == false);

                                movieToEdit.setTitle(editTituloPelicula);
                                movieToEdit.setReleaseYear(editReleaseYearPelicula);
                                movieToEdit.setDuration(editDuracionPelicula);
                                movieToEdit.setQuantityAvailable(editExistenciaPelicula);
                                movieToEdit.setDirector(editDirectorPeli);
                                movieToEdit.setGenre(editGenrePeli);

                                movieController.Update(movieToEdit);
                                System.out.println("Pelicula editada exitosamente");
                                scanner.nextLine();
                            } else {
                                System.out.println("Pelicula no encontrada");
                                scanner.nextLine();
                            }

                            break;

                        case 4:
                            movieController.GetAll();
                            break;
                    }

                    break;
            }

        } while (menu != 7);

        System.out.println("Hasta Luego");

    }
}
