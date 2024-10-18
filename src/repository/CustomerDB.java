package repository;

import domain.Customer;

import java.util.ArrayList;
import java.util.List;

/*
* Clase que actúa como una base de datos de los
* clientes que se registren en la app,
* permitiendo guardar, buscar y listar los clientes
* registrados.
* */
public class CustomerDB {
    private final List<Customer> savedCustomers;

    public CustomerDB() {
        this.savedCustomers = new ArrayList<>();
    }

    public void save(Customer customer) {
        // todo: validar antes de guardar un cleinte en la DB
        savedCustomers.add(customer);
    }
}
