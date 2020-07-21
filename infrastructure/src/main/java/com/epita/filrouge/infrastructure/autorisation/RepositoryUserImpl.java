package com.epita.filrouge.infrastructure.autorisation;

import com.epita.filrouge.domain.autorisation.IRepositoryUser;
import com.epita.filrouge.domain.autorisation.User;
import com.epita.filrouge.domain.autorisation.UserRoleEnum;
import com.epita.filrouge.infrastructure.collaborateur.CollaborateurEntity;
import com.epita.filrouge.infrastructure.collaborateur.IRepositoryJpaCollaborateur;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositoryUserImpl implements IRepositoryUser {

    private IRepositoryJpaUser userJpaRepository;

    private IRepositoryJpaCollaborateur collaborateurJpaRepository;

    @Override
    public User creerUser(User user) {

        CollaborateurEntity collabo = collaborateurJpaRepository.findByUid(user.getUid());
        User userEnrichi = new User(user.getUid(), user.getLogin());
        userEnrichi.setNom(collabo.getNom());
        userEnrichi.setPrenom(collabo.getPrenom());
        UserEntity monUserEntity = UserMapper.mapToInfra(userEnrichi);

        userJpaRepository.save(monUserEntity);

        return UserMapper.mapToDomain(monUserEntity);
    }

    @Override
    public User rechercherUser(String login) {
        UserEntity monUserEntity = userJpaRepository.findByLogin(login);
        return UserMapper.mapToDomain(monUserEntity);
    }

    @Override
    public List<User> findAllUser() {
        return null;
    }

    @Override
    public List<User> rechercherParUserRole(UserRoleEnum userRole) {
        return null;
    }

    @Override
    public void upgradeUser(User user) {
        UserEntity monUserEntity = userJpaRepository.findByLogin(user.getLogin());
        monUserEntity.setUserRole(UserRoleEnum.USER_TYPE_2);
    }

    @Override
    public void setAdmin(User user) {
        UserEntity monUserEntity = userJpaRepository.findByLogin(user.getLogin());
        monUserEntity.setUserRole(UserRoleEnum.ADMIN);
    }
}
