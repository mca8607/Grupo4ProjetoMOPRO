package org.example.model;

/**
 * Representa um administrador da plataforma.
 * O administrador tem acesso total ao sistema: pode gerir recursos, atores, utilizadores e classificações.
 * Herda de {@link UtilizadorRegistado}.
 */
public class Admin extends UtilizadorRegistado {


    /**
     * Cria um novo administrador.
     * @param email    endereço de email
     * @param nome     nome de utilizador
     * @param password palavra-passe
     */
    public Admin(String email, String nome, String password) {
        super(email, nome, password);
    }



    /**
     * Devolve uma representação textual do administrador,
     * indicando o nome, email e o papel de administrador.
     * @return string com os dados do administrador
     */
    @Override
    public String toString() {
        return super.toString() + " [Admin]";
    }
}
