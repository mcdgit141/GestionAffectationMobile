package com.epita.filrouge.domain.autorisation;

public enum UserRoleEnum {
    USER_TYPE_1 (true,false,false,false),
    USER_TYPE_2 (true, true, true, false),
    ADMIN (true, true, true, true);

    private Boolean consultation;
    private Boolean modification;
    private Boolean suppression;
    private Boolean admin;

    UserRoleEnum(Boolean consultation, Boolean modification, Boolean suppression, Boolean admin) {
        this.consultation = consultation;
        this.modification = modification;
        this.suppression = suppression;
        this.admin = admin;
    }

    public Boolean getConsultation() {
        return consultation;
    }

    public Boolean getModification() {
        return modification;
    }

    public Boolean getSuppression() {
        return suppression;
    }

    public Boolean getAdmin() {
        return admin;
    }
}
