package domain;

import java.util.ArrayList;
import java.util.List;

/*
 * Clase madre que contiene los atributos y métodos
 * necesarios para que las clases hijas hereden
 * todas las funcionalidades necesarias
 * */
public class Account {
    private static int idGenerator = 0; // generador de IDs

    protected int accountNumber;
    protected double balance;
    protected List<Movement> movements; // lista de movimientos
    protected boolean enabled;

    /*
     * El constructor no recibe parámetros, por
     * lo que inicializa cada atributo de manera interna.
     * */
    public Account() {
        idGenerator++; // incrementamos el contador
        this.accountNumber = idGenerator; // se le asigna el ID único
        this.balance = 0.0; // toda cuenta nueva comienza con balance en cero
        this.movements = new ArrayList<>(); // toda cuenta tiene una lista vacía de movimientos
        this.enabled = true; // cuenta habilitada por defecto
    }

    // Getters
    public int getAccountNumber() {
        return this.accountNumber;
    }

    public double getBalance() {
        return this.balance;
    }

    /*
     * Devuelve una lista de movimientos asociados a la cuenta
     * que lo solicita.
     * */
    public List<Movement> getMovements() {
        return this.movements;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    // Setters
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /*
     * Al establecer el valor del nuevo balance se debe
     * validar que sea mayor o igual a cero, ya que no debe
     * ser posible tener un balance de cuenta en negativo
     * */
    public void setBalance(double balance) {
        // todo: validar antes de establecer el balance
        this.balance = balance;
    }

    /*
     * Permite guardar un registro de cada operación realizada
     *
     * Criterios de aceptación:
     * - Como argumento se pasa el movimiento que se realizó creado con anterioridad
     * - El movimiento a registrar no debe ser nulo
     * - Guardarlo en la lista de la cuenta junto con el resto de movimientos
     * - El movimiento a registrar no debe estar registrado
     *   con anterioridad, es decir, debe ser único.
     * */
    public void recordMovement(Movement movement) {
        // todo: lógica pertinente al método
        this.movements.add(movement);
    }

    /*
     * Transferir desde una cuenta de la misma moneda
     * a otra cuenta de la misma moneda.
     *
     * Criterios de aceptación:
     * - Crear dos movimientos: uno asociado a la cuenta de destino y otro
     *   asociado a la cuenta de origen. Devolver este último al usuario.
     * - Para hacer transferencias se debe restar el monto
     *   de la cuenta de origen
     * - Verificar que halla balance suficiente para realizar la transferencia
     * - Sumar el monto a la cuenta de destino.
     * - El monto a transferir debe ser mayor o igual a 1.
     * - Ambas cuentas deben existir en la base de datos, caso
     *   contrario cancelar la operación.
     * */
    public Movement transfer(Account destinationAccount, double amount) {
        // todo: mejorar lógica para transferir
        this.balance = this.balance - amount; // resta a cuenta origen
//        double newDestinationAccountBalance = destinationAccount.getBalance();
//        newDestinationAccountBalance = newDestinationAccountBalance + amount;
//        destinationAccount.setBalance(newDestinationAccountBalance);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount); // suma a cuenta destino
        Movement movement = new Movement(this.accountNumber, destinationAccount.accountNumber, amount);
        this.recordMovement(movement);
        // todo: crear movimiento para la cuenta de destino
        return movement;
    }

    /*
     * Permite realizar depósitos a cuentas propias de la misma moneda.
     *
     * Criterios de aceptación:
     * - El monto a depositar debe ser mayor o igual a 1
     * - Devolver el movimiento que refleja el detalle del depósito.
     * */
    public Movement deposit(double amount) {
        // todo: mejorar lógica para hacer depósitos
        this.balance = this.balance + amount;
        Movement movement = new Movement(this.accountNumber, this.accountNumber, amount);
        this.recordMovement(movement);
        return movement;
    }

    // todo: transferToForeignCurrency()
}
