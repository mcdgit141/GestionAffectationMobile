package com.epita.filrouge.infrastructure.site;

import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.infrastructure.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class SiteExerciceEntityMapper extends AbstractMapper<SiteExercice, SiteExerciceEntity> {

    @Override
    public SiteExercice mapToDomain(final SiteExerciceEntity siteExerciceEntity) {
        final SiteExercice siteExercice = new SiteExercice(siteExerciceEntity.getCodeSite(),siteExerciceEntity.getNomSite(),
                siteExerciceEntity.getAdressePostale1(), siteExerciceEntity.getCodePostal(),siteExerciceEntity.getVille(),
                siteExerciceEntity.getPays(),siteExerciceEntity.getDateCreation());

        siteExercice.setDateCloture(siteExerciceEntity.getDateCloture());

        return siteExercice;
    }

    @Override
    public SiteExerciceEntity mapToEntity(final SiteExercice siteExercice) {
        final SiteExerciceEntity siteExerciceEntity = new SiteExerciceEntity();
        siteExerciceEntity.setCodeSite(siteExercice.getCodeSite());
        siteExerciceEntity.setNomSite(siteExercice.getNomSite());
        siteExerciceEntity.setAdressePostale1(siteExercice.getAdressePostale1());
        siteExerciceEntity.setCodePostal(siteExercice.getCodePostal());
        siteExerciceEntity.setVille(siteExercice.getVille());
        siteExerciceEntity.setPays(siteExercice.getPays());
        siteExerciceEntity.setDateCreation(siteExercice.getDateCreation());
        siteExerciceEntity.setDateCloture(siteExercice.getDateCloture());

        return siteExerciceEntity;
    }
}
