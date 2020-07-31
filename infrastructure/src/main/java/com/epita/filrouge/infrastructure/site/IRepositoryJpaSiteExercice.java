package com.epita.filrouge.infrastructure.site;

import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.infrastructure.iphone.IphoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryJpaSiteExercice extends JpaRepository<SiteExerciceEntity, Long> {

    SiteExerciceEntity findByNomSite(String nomSite);

    SiteExerciceEntity findByCodeSite(String codeSite);

}
