package com.epita.filrouge.expositions.utilisateur;

import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;

public class UtilisateurDTO {

    private String uid;
    private UtilisateurRoleEnum roleUtilisateur;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UtilisateurRoleEnum getRoleUtilisateur() {
        return roleUtilisateur;
    }

    public void setRoleUtilisateur(UtilisateurRoleEnum roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }
}
