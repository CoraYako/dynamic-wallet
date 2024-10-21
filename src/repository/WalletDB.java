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
    private final List<Account> savedAccounts;

    /*
    * Al iniciar una instancia de la clase WalletDB,
    * se inicializa una lista nueva.
    * */
    public WalletDB() {
        savedAccounts = new ArrayList<>();
    }

    public void save(Account account) {
        // todo: validar antes de guardar la cuenta
        savedAccounts.add(account);
    }

    public Account findByAccountNumber(int accountNumber) {
        // todo: validar antes de obtener la cuenta
        Account account = null;
        for (var savedAccount : this.savedAccounts) {
            if (savedAccount.getAccountNumber() == accountNumber) {
                account = savedAccount;
            }
        }
        return account;
    }

    public void removeByAccountNumber(int accountNumber) {
        Account accountToRemove = savedAccounts.get(accountNumber);
        accountToRemove.setEnabled(false); // borramos lógicamente y no físicamente
        savedAccounts.add(accountToRemove); // todo: verificar que el borrado funciona
    }

    public List<Account> findAll() {
        return savedAccounts;
    }
}
