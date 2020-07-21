package com.epita.filrouge.infrastructure.autorisation;

import com.epita.filrouge.domain.autorisation.User;

public class UserMapper {

    public static UserEntity mapToInfra(User user){
        UserEntity monUserEntity = new UserEntity();
        monUserEntity.setLogin(user.getLogin());
        monUserEntity.setNom(user.getNom());
        monUserEntity.setPrenom(user.getPrenom());
        monUserEntity.setPassword(user.getPassword());

        return monUserEntity;
    }

    public static User mapToDomain(UserEntity userEntity) {
        User monUser = new User(userEntity.getUid(), userEntity.getLogin());
        monUser.setNom(userEntity.getNom());
        monUser.setPrenom(userEntity.getPrenom());

        return monUser;
    }
}
