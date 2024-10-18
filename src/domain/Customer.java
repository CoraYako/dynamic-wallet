package domain;

import java.util.ArrayList;
import java.util.List;

/*
* Clase que actúa como cliente/usuario de la app
* Sus atributos consisten en:
* - id único
* - nombre completo
* - documento único de identidad
* - cuentas bancarias asociadas
*
* Sus datos no pueden ser modificados una vez registrado
* */
public class Customer {
    private static int idGenerator = 0;

    private final int customerId;
    private final String fullName;
    private final int dni;
    private final List<Account> accounts;

    public Customer(String fullName, int dni) {
        idGenerator++;
        this.customerId = idGenerator;
        this.fullName = fullName;
        this.dni = dni;
        this.accounts = new ArrayList<>();
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public int getDni() {
        return dni;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    /*
    * Permite guardar las cuentas que el usuario cree.
    * */
    public void addAccount(Account account) {
        // todo: validar antes de guardar la cuenta en la lista
        this.accounts.add(account);
    }
}
