package com.epita.filrouge.domain.site;

import com.epita.filrouge.domain.exception.NotFoundException;

public interface IRepositorySiteExercice {

    public SiteExercice findByNomSite(String nomSite) throws NotFoundException;
    public SiteExercice findByCodeSite(String codeSite) throws NotFoundException;

}
