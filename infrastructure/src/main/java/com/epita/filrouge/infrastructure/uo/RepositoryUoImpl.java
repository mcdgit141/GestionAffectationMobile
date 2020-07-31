package com.epita.filrouge.infrastructure.uo;

import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.IRepositoryUo;
import com.epita.filrouge.domain.uo.Uo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryUoImpl implements IRepositoryUo{

    @Autowired
    private IRepositoryJpaUo repositoryJpaUo;

    @Autowired
    private UoEntityMapper uoEntityMapper;

    @Override
    public Uo findByCodeUo(final String codeUo){

        final UoEntity uoEntity = repositoryJpaUo.findByCodeUo(codeUo);
        if (uoEntity != null) {
            return uoEntityMapper.mapToDomain(uoEntity);
        }
        throw new NotFoundException("UO000001","Ce code UO n'existe pas : " + codeUo);
    }

    @Override
    public Uo findByNomUsage(String nomUsage) {
        final UoEntity uoEntity = repositoryJpaUo.findByNomUsage(nomUsage);
        if (uoEntity != null) {
            return uoEntityMapper.mapToDomain(uoEntity);
        }
        throw new NotFoundException("UO000002","Ce nom d'UO n'existe pas : " + nomUsage);
    }

}
