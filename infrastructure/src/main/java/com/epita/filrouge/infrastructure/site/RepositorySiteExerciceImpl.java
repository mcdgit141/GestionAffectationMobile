package com.epita.filrouge.infrastructure.site;

import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.site.IRepositorySiteExercice;
import com.epita.filrouge.domain.site.SiteExercice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorySiteExerciceImpl implements IRepositorySiteExercice {

    @Autowired
    private IRepositoryJpaSiteExercice repositoryJpaSiteExercice;

    @Autowired
    private SiteExerciceEntityMapper siteExerciceEntityMapper;

    @Override
    public SiteExercice findByNomSite(final String nomSite){

        final SiteExerciceEntity siteExerciceEntityEntity = repositoryJpaSiteExercice.findByNomSite(nomSite);
        if (siteExerciceEntityEntity != null) {
            return siteExerciceEntityMapper.mapToDomain(siteExerciceEntityEntity);
        } else {
            throw new NotFoundException("SE000001", "Ce nom de site d'exercice n'existe pas : " + nomSite);
        }
    }
    public SiteExercice findByCodeSite(final String codeSite) {

        final SiteExerciceEntity siteExerciceEntityEntity = repositoryJpaSiteExercice.findByCodeSite(codeSite);
        if (siteExerciceEntityEntity != null) {
            return siteExerciceEntityMapper.mapToDomain(siteExerciceEntityEntity);
        } else {
            throw new NotFoundException("SE000002", "Ce code site d'exercice n'existe pas : " + codeSite);
        }
    }
}

