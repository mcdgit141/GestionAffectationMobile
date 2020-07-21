package com.epita.filrouge.security;

import com.epita.filrouge.domain.autorisation.IRepositoryUtilisateur;
import com.epita.filrouge.domain.autorisation.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    IRepositoryUtilisateur myUtilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Utilisateur myUtilisateur = myUtilisateurRepository.rechercherUser(login);
        User monUser = new User(myUtilisateur.getLogin(),myUtilisateur.getPassword(),getAuthorities(myUtilisateur));
        return monUser;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Utilisateur myUtilisateur) {
//        final String[] userRoles = customer.getRoles().stream().map((role) -> role.name()).toArray(String[]::new);
        String[] userRoles = new String[1];
        userRoles[1] = myUtilisateur.getUserRole().toString();
        final Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
