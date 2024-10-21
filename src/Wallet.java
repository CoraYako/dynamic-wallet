import domain.*;
import repository.WalletDB;

import javax.swing.*;

public class Wallet {
    // instancia única de la clase para ser usada como base de datos
    static WalletDB walletDB = new WalletDB();

    // se declaran las variables necesarias para ser reutilizadas en la app
    static Customer appCustomer;
    static Account globalAccount;
    static Movement movement;

    static String WRONG_OPTION_MESSAGE = "Opción incorrecta";
    static final String PESOS_CURRENCY = "PESOS";
    static final String USD_CURRENCY = "USD";
    static final String MENU = "Seleccióne el tipo de cuenta para operar:\n" +
            "1. Cuenta en Pesos\n" +
            "2. Cuenta en Dólares\n";

    public static void main(String[] args) {
        welcomeMessage(); // mensaje de bienvenida
        registration(); // registro de usuario
        createAccount(); // crear cuenta en pesos o usd
        walletApp(); // menú principal de la app
    }


    /**
     * Muestra un mensaje de bienvenida al inicio de la ejecución
     */
    public static void welcomeMessage() {
        String welcomeMessage = "Bienvenido a Dynamic Wallet\nSu billetera virtual dinámica" +
                "\n\nFavor de registrarse para operar";
        JOptionPane.showMessageDialog(null, welcomeMessage);
    }

    /**
     * Permite hacer un registro de un cliente. Esto permitirá que el usuario pueda usar la app.
     * */
    public static void registration() {
        // todo: validar todo antes de registrar al cliente
        JOptionPane.showMessageDialog(null, "Para registrarse, ingrese los siguientes datos (presione OK para continuar)");

        String firstName = JOptionPane.showInputDialog("Nombre");
        String lastName = JOptionPane.showInputDialog("Apellido");
        appCustomer = new Customer(firstName, lastName);

        JOptionPane.showMessageDialog(null, "Registro exitoso");
    }

    /**
     * Se presenta un menú que permite crear una cuenta bancaria dependiendo de la opción que se elija.
     * Si el usuario elije una de las opciones pero ya posée una cuenta de ese tipo, se muestra un
     * mensaje detallando el error.
     */
    public static void createAccount() {
        int option = Integer.parseInt(JOptionPane.showInputDialog(MENU)); // ingreso de opción
        validateInput(option, 1, 2, WRONG_OPTION_MESSAGE, MENU); // validación de opción seleccionada

        String accountType = (option == 1) ? PESOS_CURRENCY : USD_CURRENCY; // tipo de cuenta según elección

        openAccount(accountType); // apertura de cuenta del tipo especificado
    }

    /**
     * Permite abrir una cuenta al usuario indicando el tipo de cuenta que desea abrir.
     * Si la cuenta ya existe, se muestra el mensaje de error.
     *
     * @param accountType Tipo de cuenta (Pesos o USD)
     */
    public static void openAccount(String accountType) {
        // mensaje de error si ya posee una cuenta del tipo indicado
        String accountExist = "Ya posee una cuenta de este tipo, presione OK para volver al menú principal";

        globalAccount = appCustomer.getAccountByType(accountType); // obtención de cuenta según el tipo

        if (globalAccount != null) { // si ya tiene una cuenta del tipo, se muestra mensaje pertinente
            JOptionPane.showMessageDialog(null, accountExist);
            return;
        }

        createAccount(accountType); // se crea una cuenta del tipo indicado
    }

    public static void walletApp() {
        // menú principal de la app
        String menu = "Bienvenido " + appCustomer.getFullName() + "\n" +
                "¿Qué le gustaría realizar?\n" +
                "1. Depositar\n" +
                "2. Transferir\n" +
                "3. Ver movimientos\n" +
                "4. Ver estado de cuenta\n" +
                "5. Crear una nueva cuenta bancaria\n" +
                "6. Ver mis datos\n" +
                "7. Finalizar";

        int option; // se usará para que el usuario ingrese una opción

        do {
            option = Integer.parseInt(JOptionPane.showInputDialog(menu)); // esperamos a que ingrese la opción
            validateInput(option, 1, 7, WRONG_OPTION_MESSAGE, menu); // se valida la opción

            // acción a realizar según opción
            switch (option) {
                case 1:
                    doDeposit();
                    break;
                case 2:
                    doTransfer();
                    break;
                case 3:
                    viewMovements();
                    break;
                case 4:
                    viewAccountStatus();
                    break;
                case 5:
                    createAccount();
                    break;
                case 6:
                    displayUserInfo();
                    break;
                default: // cuando se elije la opción n°7
                    JOptionPane.showMessageDialog(null, "Sesión cerrada con éxito");
                    break;
            }
        } while (option != 7);
    }

    /**
     * Muestra la información asociada al cliente.
     */
    private static void displayUserInfo() {
        JOptionPane.showMessageDialog(null, appCustomer.toString());
    }

    /**
     * Permite mostrar el balance de la cuenta seleccionada. Si el usuario solicitante
     * no posee una cuenta de la opción seleccionada (Pesos o USD), se muestra un mensaje
     * detallando el error.
     */
    private static void viewAccountStatus() {
        int option = Integer.parseInt(JOptionPane.showInputDialog(MENU)); // se espera que se ingrese la opción
        validateInput(option, 1, 2, WRONG_OPTION_MESSAGE, MENU); // se valida la opción

        String accountType = (option == 1) ? PESOS_CURRENCY : USD_CURRENCY; // tipo de cuenta según opción elegida

        getStatusByAccountType(accountType); // se obtiene el estado de cuenta según el tipo de la misma
    }

    /**
     * Permite obtener el estado de cuenta (balance) de forma dinámica según el tipo de cuenta indicado.
     *
     * @param accountType Tipo de cuenta (Pesos o USD)
     */
    public static void getStatusByAccountType(String accountType) {
        globalAccount = appCustomer.getAccountByType(accountType); // obtener cuenta según tipo

        if (globalAccount == null) { // validación de tenencia de cuenta
            JOptionPane.showMessageDialog(null, "Usted no posée una cuenta en " + accountType +
                    "\nCree una de forma rápida y fácil en el menú principal, " +
                    "y comience a utilizarla hoy para operar y ver sus movimientos.\n\n Operación cancelada");
            return;
        }

        // si posée el tipo de cuenta, se obtiene el balance
        JOptionPane.showMessageDialog(null, "Estado de su cuenta en " + accountType + ": \n" +
                globalAccount.getBalance());
    }

    /**
     * Permite realizar transferencias entre cuentas en el cual a travez de un simple menú
     * se puede seleccionar entre hacer una transferencia a una cuenta en Pesos o en Dólares
     * indicando el CBU de la cuenta destino.
     * <br>
     * Si la cuenta destino es de un tipo de moneda distinto, la operación se cancela.
     * <br>
     * Al finalizar la operación, se muestra el comprobante de la transacción.
     */
    private static void doTransfer() {
        int option = Integer.parseInt(JOptionPane.showInputDialog(MENU)); // ingreso de opción
        validateInput(option, 1, 2, WRONG_OPTION_MESSAGE, MENU); // validación dela opción

        String accountType = (option == 1) ? PESOS_CURRENCY : USD_CURRENCY; // tipo de cuenta según opción elegida

        processTransfer(accountType); // procesamiento de transferencia
    }

    /**
     * Procesa las transferencias entre cuentas de forma dinámica indicando el tipo de cuenta (moneda).
     * <p>
     * Ambas cuentas deben ser del mismo tipo para que la operación se realice.
     * <p>
     * Al finalizar se muestra el comprobande de operación.
     *
     * @param accountType Tipo de cuenta (Pesos o USD)
     */
    public static void processTransfer(String accountType) {
        String accountTypeMatchError = "No es posible realizar transferencias a cuentas de moneda extranjera.";

        Account customerAccount = appCustomer.getAccountByType(accountType); // obtener cuenta según el tipo

        if (customerAccount == null) { // si no tiene dicha cuenta, mostrar el mensaje de error
            JOptionPane.showMessageDialog(null, "No posée una cuenta en " + accountType + "\nOperación cancelada");
            return;
        }

        // se indica CBU cuenta destino y se realiza búsqueda en la base de datos
        int destinationAccountNumber = Integer.parseInt(JOptionPane.showInputDialog("Indique CBU de destino"));
        Account destinationAccount = walletDB.findByAccountNumber(destinationAccountNumber);

        if (destinationAccount == null) { // si no existe la cuenta solicitada, mostrar mensaje de error
            JOptionPane.showMessageDialog(null, "La cuenta solicitada no existe");
            return;
        }

        // verificar que ambas cuentas sean del mismo tipo
        if (destinationAccount.getClass() != customerAccount.getClass()) {
            JOptionPane.showMessageDialog(null, accountTypeMatchError); // mensaje de error (Pesos -> USD)
            return;
        }

        // si pasa todas las validaciones, se pide el monto a transferir
        double amount = Double.parseDouble(JOptionPane.showInputDialog("Monto a transferir"));

        // Se realiza la transferencia, se le establece el monto a la cuenta destino y se captura el comprobante
        movement = appCustomer.getAccountByType(accountType)
                .transfer(walletDB.findByAccountNumber(destinationAccountNumber), amount);

        JOptionPane.showMessageDialog(null, "Operación exitósa (Transferencia)\nComprobante de la operación: \n" +
                "\n" + movement.getDetail());
    }

    /**
     * Permite visualizar de forma clara los movimientos registrados de la cuenta seleccionada.
     */
    public static void viewMovements() {
        int option = Integer.parseInt(JOptionPane.showInputDialog(MENU)); // se ingresa la opción
        validateInput(option, 1, 2, WRONG_OPTION_MESSAGE, MENU); // se verifica que la opción sea correcta

        String accountType = (option == 1) ? PESOS_CURRENCY : USD_CURRENCY; // tipo de cuenta según opción elegida
        getMovementsByAccountType(accountType); // se obtienen los movimientos de la cuenta por tipo de cuenta
    }

    /**
     * Permite obtener un registro detallado según qué tipo de cuenta es.
     *
     * @param accountType Tipo de cuenta (Pesos o USD)
     */
    public static void getMovementsByAccountType(String accountType) {
        globalAccount = appCustomer.getAccountByType(accountType); // obtener cuenta según el tipo

        if (globalAccount == null) { // si la cuenta no existe, se muestra un mensaje
            JOptionPane.showMessageDialog(null, "Usted no posée una cuenta en " + accountType +
                    "\nAbra una para comenzar a operar.");
            return;
        }

        if (globalAccount.getMovements().isEmpty()) { // comprobar que la cuenta tenga movientos
            JOptionPane.showMessageDialog(null, "Su cuenta en " + accountType + " no registra movimientos");
        } else {
            JOptionPane.showMessageDialog(null, "Movimientos de su cuenta en " + accountType + ":\n" +
                    globalAccount.getMovements());
        }
    }

    /**
     * Permite al usuario realizar un depósito,
     * selecionando el tipo de cuenta a la cual desea realizar el depósito (Pesos o USD)
     * */
    public static void doDeposit() {
        // se espera que el usuario elija una opción
        int option = Integer.parseInt(JOptionPane.showInputDialog(MENU));
        validateInput(option, 1, 2, WRONG_OPTION_MESSAGE, MENU);

        // se establece el tipo de cuenta según la opción elegida (Pesos o Dólares)
        String accountType = (option == 1) ? PESOS_CURRENCY : USD_CURRENCY;

        // procesamiento del depósito
        processDeposit(accountType);
    }

    /**
     * Permite procesar de forma sencilla la operación de deósito, validando si el usuario
     * posee la cuenta según el tipo de cuenta que se pase como argumento.
     * <p>
     * Dentro se pide que ingrese el monto a depositar y se genera el comprobante de operación.
     *
     * @param accountType Tipo de cuenta (Pesos o USD)
     */
    public static void processDeposit(String accountType) {
        // comprueba si no posée una cuenta de este tipo y muestra un mensaje de ser verdadero
        if (appCustomer.getAccountByType(accountType) == null) {
            JOptionPane.showMessageDialog(null, "Usted no posée una cuenta en este tipo de moneda.\n" +
                    "Cree una de forma rápida y fácil en el menú principal, " +
                    "y comience a utilizarla hoy para realizar operaciones y ver sus movimientos.\n"
                    + "\n Operación cancelada");
            return;
        }

        // se pide el monto a depositar
        double amount = Double.parseDouble(JOptionPane.showInputDialog("Monto a depositar en Dólares"));

        // se obtiene la cuenta, se llama a la función depositar y se captura el comprobande de operación
        movement = appCustomer.getAccountByType(accountType).deposit(amount);

        JOptionPane.showMessageDialog(null, "Operación exitósa (Depósito)\nComprobante de la operación: \n" +
                "\n" + movement.getDetail());
    }

    /**
     * Crear una cuenta indicando el tipo de de cuenta que será (Pesos o USD).
     * Una vez indicado el tipo de cuenta, esta se crea y se la agrega al listado
     * de cuentas que posee el usuario registrado en la app.
     *
     * @param accountType Tipo de cuenta a crear (Pesos o USD)
     */
    public static void createAccount(String accountType) {
        if (accountType.equals(PESOS_CURRENCY)) {
            globalAccount = new PesosAccount();
            appCustomer.addAccount(globalAccount);
            JOptionPane.showMessageDialog(null, "Cuenta en Pesos creada con éxito");
        }
        if (accountType.equals(USD_CURRENCY)) {
            globalAccount = new USDAccount();
            appCustomer.addAccount(globalAccount);
            JOptionPane.showMessageDialog(null, "Cuenta en Dólares creada con éxito");
        }
    }

    /**
     * Valida las opciones ingresadas por el usuario. Se debe determinar un rango A y B.
     * Mediante un bucle se evalúa si el valor ingresado está fuera de esos rangos.
     * Caso de que el valor esté fuera de los rangos especificados, se vuelve a pedir que ingrese el valor
     * nuevamente hasta que el valor ingresado sea correcto (esté dentro de los parámetros).
     *
     * @param option Valor ingresado por el usuario
     * @param rangeA Rango inicial
     * @param rangeB Rango final
     * @param errorMsg Mensaje de error al ingresar un valor fuera de los rangos
     */
    public static void validateInput(int option, int rangeA, int rangeB, String errorMsg, String menu) {
        // si se pone un valor incorrecto de opción, se vuelve a pedir
        while (option < rangeA || option > rangeB) {
            JOptionPane.showMessageDialog(null, errorMsg);
            option = Integer.parseInt(JOptionPane.showInputDialog(menu));
        }
    }
}