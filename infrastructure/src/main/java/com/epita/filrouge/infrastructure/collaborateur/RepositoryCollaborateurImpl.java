package com.epita.filrouge.infrastructure.collaborateur;

import com.epita.filrouge.domain.collaborateur.Collaborateur;
import com.epita.filrouge.domain.collaborateur.IRepositoryCollaborateur;
import com.epita.filrouge.domain.exception.NotFoundException;
import com.epita.filrouge.domain.site.SiteExercice;
import com.epita.filrouge.domain.uo.Uo;
import com.epita.filrouge.infrastructure.site.IRepositoryJpaSiteExercice;
import com.epita.filrouge.infrastructure.site.SiteExerciceEntity;
import com.epita.filrouge.infrastructure.uo.UoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryCollaborateurImpl implements IRepositoryCollaborateur {

    @Autowired
    private IRepositoryJpaCollaborateur repositoryJpaCollaborateur;

    @Autowired
    private IRepositoryJpaSiteExercice repositoryJpaSiteExercice;

    @Override
    public Collaborateur findByUid(String uid) {
        System.out.println("RepositoryCollaborateurImpl--uid--:" + uid );
        CollaborateurEntity collaborateurEntity =  repositoryJpaCollaborateur.findByUid(uid);
        System.out.println("RepositoryCollaborateurImpl--collaborateurEntity.getUid()--après recherche:" + collaborateurEntity.getUid() );
        if (collaborateurEntity != null) {

            UoEntity uoEntity = collaborateurEntity.getUo();
            SiteExerciceEntity siteExerciceEntity = uoEntity.getSiteExercice();

            SiteExercice siteExercice = new SiteExercice(siteExerciceEntity.getCodeSite(),siteExerciceEntity.getNomSite(),
                                        siteExerciceEntity.getAdressePostale1(),siteExerciceEntity.getCodePostal(),
                                        siteExerciceEntity.getVille(), siteExerciceEntity.getPays(), siteExerciceEntity.getDateCreation());

            siteExercice.setDateCloture(siteExerciceEntity.getDateCloture());

            Uo uo = new Uo(uoEntity.getCodeUo(),uoEntity.getFonctionRattachement(),uoEntity.getCodeUoParent(),
                        uoEntity.getNomUsageUo(),uoEntity.getNomResponsableUo());

            uo.setSiteExercice(siteExercice);

            Collaborateur collaborateur = new Collaborateur(collaborateurEntity.getUid(), collaborateurEntity.getNom(), collaborateurEntity.getPrenom(),
                                            collaborateurEntity.getNumeroLigne(),uo);
            collaborateur.setNumeroLigne(collaborateurEntity.getNumeroLigne());
//            collaborateur.setId(collaborateurEntity.getCollaborateurId());

            return collaborateur;
        } else {
            throw new NotFoundException("Le collaborateur par recherche sur l'UID suivant est non trouvé = " + uid);
        }

    }

    @Override
    public Collaborateur findByNumeroLigne(String numeroLigne) {

        CollaborateurEntity collaborateurEntity = repositoryJpaCollaborateur.findByNumeroLigne(numeroLigne);
        if (collaborateurEntity != null) {

            UoEntity uoEntity = collaborateurEntity.getUo();
            SiteExerciceEntity siteExerciceEntity = uoEntity.getSiteExercice();

            SiteExercice siteExercice = new SiteExercice(siteExerciceEntity.getCodeSite(),siteExerciceEntity.getNomSite(),
                    siteExerciceEntity.getAdressePostale1(),siteExerciceEntity.getCodePostal(),
                    siteExerciceEntity.getVille(), siteExerciceEntity.getPays(), siteExerciceEntity.getDateCreation());

            siteExercice.setDateCloture(siteExerciceEntity.getDateCloture());

            Uo uo = new Uo(uoEntity.getCodeUo(),uoEntity.getFonctionRattachement(),uoEntity.getCodeUoParent(),
                    uoEntity.getNomUsageUo(),uoEntity.getNomResponsableUo());

            uo.setSiteExercice(siteExercice);
            return new Collaborateur(collaborateurEntity.getUid(), collaborateurEntity.getNom(), collaborateurEntity.getPrenom(),
                    collaborateurEntity.getNumeroLigne(),uo);

        } else {
            throw new NotFoundException("Le collaborateur par recherche du numéro de ligne suivant est non trouvé  = " + numeroLigne);
        }
    }

    @Override
    public void miseAJourCollaborateur(Collaborateur collaborateur, String numLigne) {
        // maj du numero de ligne du collaborateur avec celui fourni
        CollaborateurEntity monCollaborateurEntity = repositoryJpaCollaborateur.findByUid(collaborateur.getUid());

        if (monCollaborateurEntity.getNumeroLigne() != numLigne) {
            monCollaborateurEntity.setNumeroLigne(numLigne);
            repositoryJpaCollaborateur.save(monCollaborateurEntity);
        }

    }

    private SiteExerciceEntity getSiteExerciceEntity(SiteExercice siteExercice) {
        return repositoryJpaSiteExercice.findByCodeSite(siteExercice.getCodeSite());
    }
}
