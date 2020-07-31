package com.epita.filrouge.domain.uo;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositoryUo {

    public Uo findByCodeUo (String codeUo) throws NotFoundException;

    public Uo findByNomUsage (String nomUsage) throws NotFoundException;
}
