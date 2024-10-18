package repository;

import domain.Account;

import java.util.ArrayList;
import java.util.List;

/*
* Clase que actúa como base de datos
* para crear, editar, obtener, listar y eliminar
* cuentas en general.
* */
public class WalletDB {
    private static List<Account> savedAccount;

    /*
    * Al iniciar una instancia de la clase WalletDB,
    * se inicializa una lista nueva.
    * */
    public WalletDB() {
        savedAccount = new ArrayList<>();
    }

    public void save(Account account) {
        // todo: validar antes de guardar la cuenta
        savedAccount.add(account);
    }

    public Account findByAccountNumber(int accountNumber) {
        // todo: validar antes de obtener la cuenta
        return savedAccount.get(accountNumber);
    }

    public void removeByAccountNumber(int accountNumber) {
        Account accountToRemove = savedAccount.get(accountNumber);
        accountToRemove.setEnabled(false); // borramos lógicamente y no físicamente
        savedAccount.add(accountToRemove); // todo: verificar que el borrado funciona
    }

    public List<Account> findAll() {
        return savedAccount;
    }
}
