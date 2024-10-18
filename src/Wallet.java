import domain.*;
// import repository.WalletDB;

import javax.swing.*;

public class Wallet {
    // iniciamos una única instancia de la base de datos
    // static WalletDB walletDB = new WalletDB(); // DB cuentas bancarias

    // declaramos un cliente para usarlo como usuario registrado
    static Customer appCustomer;
    // declaramos una cuenta para usarla en la app
    static Account account;
    // declaramos un movimiento para usarlo en la app
    static Movement movement;

    public static void main(String[] args) {
        welcomeMessage(); // bienvenida a la app
        registration(); // registro de usuario
        createAnAccount(); // elección de creación de cuenta bancaria
        walletApp(); // menú principal de la app
    }

    public static void welcomeMessage() {
        String welcomeMessage = "Bienvenido a Dynamic Wallet\n" +
                "Su billetera virtual dinámica\n" +
                "\nFavor de registrarse para operar";
        JOptionPane.showMessageDialog(null, welcomeMessage);
    }

    public static void registration() {
        // todo: validar todo antes de registrar al cliente
        JOptionPane.showMessageDialog(null, "Para registrarse, ingrese los siguientes datos (presione OK para continuar)");
        String fullName = JOptionPane.showInputDialog("Ingrese su nombre completo");
        int dni = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su DNI sin puntos ni espacios"));
        appCustomer = new Customer(fullName, dni);
    }

    public static void createAnAccount() {
        String menu = "Seleccióne una opción:\n" +
                "1. Crear cuenta en Pesos\n" +
                "2. Crear cuenta en Dólares\n";
        int option = Integer.parseInt(JOptionPane.showInputDialog(menu));
        while (option < 1 || option > 2) {
            JOptionPane.showMessageDialog(null, "Opción incorrecta");
            option = Integer.parseInt(JOptionPane.showInputDialog(menu));
        }
        if (option == 1) {
            createPesosAccount();
        } else {
            createUsdAccount();
        }
    }

    public static void walletApp() {
        String menu = "Bienvenido " + appCustomer.getFullName() + "\n" +
                "¿Qué le gustaría realizar?\n" +
                "1. Depositar\n" +
                "2. Transferir\n" +
                "3. Ver movimientos\n" +
                "4. Ver estado de cuenta\n" +
                "5. Crear nueva cuenta en Pesos o en Dólares\n" +
                "6. Finalizar";
        int option;
        do {
            option = Integer.parseInt(JOptionPane.showInputDialog(menu));
            while (option < 1 || option > 6) {
                JOptionPane.showMessageDialog(null, "Opción incorrecta");
                option = Integer.parseInt(JOptionPane.showInputDialog(menu));
            }
            switch (option) {
                case 1:
                    selectCurrencyToDeposit();
                    break;
                case 2:
                    break;
                /*case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;*/
                case 6:
                    break; // finaliza el programa
            }
        } while (option != 6);
    }

    public static void selectCurrencyToDeposit() {
        String menu = "Seleccione qué tipo de depósito quiere hacer:\n" +
                "\n1. Pesos" +
                "\n2. Dólares";
        double amount;
        int option = Integer.parseInt(JOptionPane.showInputDialog(menu));
        while (option < 1 || option > 2) {
            JOptionPane.showMessageDialog(null, "Opción incorrecta");
            option = Integer.parseInt(JOptionPane.showInputDialog(menu));
        }
        if (option == 1) {
            for (var account : appCustomer.getAccounts()) {
                if (account instanceof PesosAccount) {
                    amount = Double.parseDouble(JOptionPane.showInputDialog("Monto a depositar en Pesos"));
                    movement = account.deposit(amount);
                    JOptionPane.showInputDialog(null, "Transacción exitosa (depósito)\n \n" + movement.getDetail());
                }
            }
        }
        if (option == 2) {
            for (var account : appCustomer.getAccounts()) {
                if (account instanceof USDAccount) {
                    amount = Double.parseDouble(JOptionPane.showInputDialog("Monto a depositar en Dólares"));
                    movement = account.deposit(amount);
                    JOptionPane.showInputDialog(null, "Transacción exitosa (depósito)\n \n" + movement.getDetail());
                }
            }
        }
    }

    public static void createPesosAccount() {
        account = new PesosAccount();
        appCustomer.addAccount(account);
        JOptionPane.showMessageDialog(null, "Cuenta en Pesos creada con éxito");
    }

    public static void createUsdAccount() {
        account = new USDAccount();
        appCustomer.addAccount(account);
        JOptionPane.showMessageDialog(null, "Cuenta en Dólares creada con éxito");
    }
}