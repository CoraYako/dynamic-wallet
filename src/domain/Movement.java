package domain;

import java.time.LocalDateTime;

/*
 * Clase que permite hacer registro detallado
 * de cada movimiento que realice una cuenta.
 * Entre los detalles se encuentran:
 * - número de transacción (único)
 * - fecha y hora
 * - CBU cuenta de origen
 * - CBU cuenta destino
 * - monto transferido
 * */
public class Movement {
    private static int idGenerator = 0; // generador de IDs

    private final int transactionId;
    private final LocalDateTime date; // registro de fecha y hora
    private final int originAccount;
    private final int destinationAccount;
    private final double amount;

    /*
     * Para crear un movimiento se necesitan de 3 datos:
     * - CBU origen
     * - CBU destino
     * - monto
     * En el momento que se invoca la creación de un nuevo movimiento entre
     * cuentas, se graba la fecha y hora actual, el n° de transacción (único)
     * y se incrementa el generador de IDs en 1
     * */
    public Movement(int originAccount, int destinationAccount, double amount) {
        idGenerator++; // pos incremento del ID
        this.transactionId = idGenerator;
        this.date = LocalDateTime.now();
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    // Getters
    public int getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getOriginAccount() {
        return originAccount;
    }

    public int getDestinationAccount() {
        return destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    /*
     * Obtener el detalle del movimiento en formato toString()
     * */
    public String getDetail() {
        StringBuilder sb = new StringBuilder();
        sb.append("DETALLES DEL COMPROBANTE\n");
        sb.append("Número de transacción: ");
        sb.append(transactionId);
        sb.append("\nFecha y hora: ");
        sb.append(date);
        sb.append("\nCuenta de origen: ");
        sb.append(originAccount);
        sb.append("\nCuenta de destino: ");
        sb.append(destinationAccount);
        sb.append("\nMonto: ");
        sb.append(amount);
        return sb.toString();
    }
}
