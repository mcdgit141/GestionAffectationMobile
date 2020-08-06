package com.epita.filrouge.expositions.utilisateur;

import com.epita.filrouge.domain.utilisateur.UtilisateurRoleEnum;

public class UtilisateurDTO {

    private String uid;
    private String roleUtilisateur;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRoleUtilisateur() {
        return roleUtilisateur;
    }

    public void setRoleUtilisateur(String roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }
}
