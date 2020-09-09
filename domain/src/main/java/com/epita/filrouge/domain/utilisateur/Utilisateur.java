package com.epita.filrouge.domain.utilisateur;

public class Utilisateur {

    private Long id;
    private String uid;
    private String nom;
    private String prenom;
    private String login;
    private String password;
    private UtilisateurRoleEnum userRole;

    public Utilisateur(String uid,String nom, String prenom, UtilisateurRoleEnum userRole) {
        this.uid = uid;
        this.nom= nom;
        this.prenom = prenom;
        password = "$2a$10$ix2v00b5v0E.Ro3ZM0/Vv.cK704O4N1w/.yQeNq46KIVKmDanaHBi";
        this.userRole = userRole;
        this.login = remplacementCaractereSpec(prenom) + "." + remplacementCaractereSpec(nom) + "@entreprise.com";
    }

    public Utilisateur(Long id, String uid, String nom, String prenom, String login
            , String password, UtilisateurRoleEnum userRole) {
        this.id = id;
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
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

    public Long getId() {
        return id;
    }

    public void modifierMdpUtilisateur(String newPassword){
        this.password = newPassword;
    }

    private String remplacementCaractereSpec(String string){
        String caractereSpeciaux = "àâäéêèëîïôöûüùç";
        String remplacmeent = "aaaeeeeiioouuuc";
        String result = string.toLowerCase();
        for (int i = 0; i <caractereSpeciaux.length() ; i++) {
            result = result.replace(caractereSpeciaux.charAt(i), remplacmeent.charAt(i));
        }
        return result;
    }
}
