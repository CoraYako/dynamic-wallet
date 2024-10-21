package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que actúa como cliente/usuario de la app.
 * <p>
 * Proporciona métodos para obtener el nombre completo, asociarle cuentas bancarias, obtener
 * el listado de cuentas y obtener una cuenta por moneda.
 * <p>
 * Los datos como el nombre completo y el ID no pueden ser modificados una vez registrados.
 * */
public class Customer {
    private static int idGenerator = 0;

    private final int customerId;
    private final String firstName;
    private final String lastName;
    private final List<Account> accounts;

    /**
     * Permite crear un objeto de la clase Customer. Al crearse el objeto, este posee un ID
     * por defecto y listado de cuentas inicializado (vacío).
     *
     * @param firstName Nombre del usuario.
     * @param lastName Apellido del usuario.
     */
    public Customer(String firstName, String lastName) {
        idGenerator++;
        this.customerId = idGenerator;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    /**
     * Obtener el ID único del usuario/cliente.
     *
     * @return El ID del usuario.
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Permite obtener el nombre completo del usuario en formato mayúsculas
     * concatenando el nombre con el apellido.
     * <p>
     * Ejemplo: JOHN DOE
     *
     * @return El nombre completo del usuario en formato mayúsculas.
     */
    public String getFullName() {
        return (this.firstName + ' ' + this.lastName).toUpperCase();
    }

    /**
     * Obtener el listado completo de cuentas que tiene el usuario.
     *
     * @return Lista de las cuentas que posee el usuario.
     */
    public List<Account> getAccounts() {
        return this.accounts;
    }

    /**
     * Obtener una cuenta del listado de cuentas del usuario especificando el tipo
     * de moneda (Pesos o USD).
     *
     * @param accountType El tipo de cuenta (Pesos o USD)
     *
     * @return La cuenta solicitada según el tipoo de cuenta que se especifique o nulo si no se encuentra.
     */
    public Account getAccountByType(String accountType) {
        for (Account account : this.getAccounts()) {
            if (accountType.equalsIgnoreCase("PESOS") && account instanceof PesosAccount) {
                return account;
            } else if (accountType.equalsIgnoreCase("USD") && account instanceof USDAccount) {
                return account;
            }
        }
        return null; // si no se encuentra la cuenta solicitada, retornar null
    }

    /**
     * Permite agregar y guardar una cuenta al listado de cuentas que posee el usuario.
     *
     * @param account Cuenta a guardar.
     * */
    public void addAccount(Account account) {
        // todo: validar antes de guardar la cuenta en la lista
        this.accounts.add(account);
    }

    /**
     * Permite visualizar por consola toda la data relacionada al cliente.
     *
     * @return Los datos del cliente (id, nombre completo y cuentas)
     */
    @Override
    public String toString() {
        return "ID de cliente: " + this.customerId + "\n" +
                "Nombre completo: " + this.getFullName() + "\n" +
                "Cuentas asociadas: " + this.accounts;
    }
}
