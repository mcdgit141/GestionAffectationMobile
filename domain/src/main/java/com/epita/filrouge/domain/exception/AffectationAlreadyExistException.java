package com.epita.filrouge.domain.exception;

public class AffectationAlreadyExistException extends BusinessException{

    public AffectationAlreadyExistException(String collaborateurUid) {
        super(ALREADY_EXIST_CODE, "l'affectation pour l'uid de ce collaborateur existe déjà, veuillez la clôturer au préalable : " + collaborateurUid);
    }
}
