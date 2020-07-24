package com.epita.filrouge.infrastructure.utilisateur;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class UserRoleEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeRole;
    private Boolean consultation;
    private Boolean modification;
    private Boolean suppression;
    private Boolean admin;

//    @OneToMany(mappedBy = "userRole")
    private List<UtilisateurEntity> users = new ArrayList<>();

    public UserRoleEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(String typeRole) {
        this.typeRole = typeRole;
    }

    public Boolean getConsultation() {
        return consultation;
    }

    public void setConsultation(Boolean consultation) {
        this.consultation = consultation;
    }

    public Boolean getModification() {
        return modification;
    }

    public void setModification(Boolean modification) {
        this.modification = modification;
    }

    public Boolean getSuppression() {
        return suppression;
    }

    public void setSuppression(Boolean suppression) {
        this.suppression = suppression;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<UtilisateurEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UtilisateurEntity> users) {
        this.users = users;
    }
}
