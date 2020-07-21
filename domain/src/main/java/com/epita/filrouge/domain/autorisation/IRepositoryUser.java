package com.epita.filrouge.domain.autorisation;

import java.util.List;

public interface IRepositoryUser {
    User creerUser(User user);

     User rechercherUser(String login);

    List<User> findAllUser();

    List<User> rechercherParUserRole(UserRoleEnum userRole);

    void upgradeUser(User user);

    void setAdmin(User user);

}
