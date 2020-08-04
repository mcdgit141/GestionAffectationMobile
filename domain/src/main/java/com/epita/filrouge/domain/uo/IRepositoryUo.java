package com.epita.filrouge.domain.uo;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositoryUo {

    Uo findByCodeUo (String codeUo) throws NotFoundException;

    Uo findByNomUsageUo (String nomUsageUo) throws NotFoundException;
}
