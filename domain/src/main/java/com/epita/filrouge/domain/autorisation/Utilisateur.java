package com.epita.filrouge.domain.autorisation;

public class Utilisateur {

    private String uid;
    private String nom;
    private String prenom;
    private String login;
    private String password;

    private UtilisateurRoleEnum userRole;

    public Utilisateur(String uid, String login) {
        this.uid = uid;
        this.login = login;
        password = "password";
        userRole= UtilisateurRoleEnum.ROLE_TYPE1;
    }

    public String getUid() {
        return uid;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UtilisateurRoleEnum getUserRole() {
        return userRole;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void modifierPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UtilisateurRoleEnum userRole) {
        this.userRole = userRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
