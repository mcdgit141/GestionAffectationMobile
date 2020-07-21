package com.epita.filrouge.domain.autorisation;

public class User {

    private String uid;
    private String nom;
    private String prenom;
    private String login;
    private String password;
    private UserRoleEnum userRole;

    public User(String uid, String login) {
        this.uid = uid;
        this.login = login;
        password = "defaultPassword";
        userRole=UserRoleEnum.USER_TYPE_1;
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

    public UserRoleEnum getUserRole() {
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

    public void setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
    }
}
