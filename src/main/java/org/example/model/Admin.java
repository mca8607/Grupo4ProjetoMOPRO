package org.example.model;

public class Admin extends UtilizadorRegistado {
    public Admin(String email, String nome, String password) {
        super(email, nome, password);
    }
    @Override
    public String toString() {
        return super.toString() + " [Admin]";
    }
}
