package com.epita.filrouge.infrastructure.uo;

import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.infrastructure.AbstractMapper;
import com.epita.filrouge.infrastructure.site.IRepositoryJpaSiteExercice;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntity;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UoEntityMapper extends AbstractMapper<Uo, UoEntity> {

    @Autowired
    private SiteExerciceEntityMapper siteExerciceMapper;

    @Autowired
    private IRepositoryJpaSiteExercice repositoryJpaSiteExercice;

    @Override
    public Uo mapToDomain(final UoEntity uoEntity) {
        final Uo uo = new Uo(uoEntity.getCodeUo(),uoEntity.getFonctionRattachement(),uoEntity.getCodeUoParent(),
                            uoEntity.getNomUsageUo(),uoEntity.getNomResponsableUo());
        siteExerciceMapper.mapToDomain(uoEntity.getSiteExercice());

        return uo;
    }

    @Override
    public UoEntity mapToEntity(final Uo uo) {
        final UoEntity uoEntity = new UoEntity();
        uoEntity.setCodeUo(uo.getCodeUo());
        uoEntity.setFonctionRattachement(uo.getFonctionRattachement());
        uoEntity.setCodeUoParent(uo.getCodeUoParent());
        uoEntity.setNomUsageUo(uo.getNomUsageUo());
        uoEntity.setNomResponsableUo(uo.getNomResponsableUo());
           
        uoEntity.setSiteExercice(getSiteExerciceEntity(uo.getSiteExercice()));

        return uoEntity;
    }

    private SiteExerciceEntity getSiteExerciceEntity(SiteExercice siteExercice) {
        return repositoryJpaSiteExercice.findByCodeSite(siteExercice.getCodeSite());
    }

 }
