package com.epita.filrouge.expositions.authentication;

import com.epita.filrouge.application.security.UserDetailServiceImpl;
import com.epita.filrouge.jwt.AuthenticateDTO;
import com.epita.filrouge.jwt.JwtUtils;
import com.epita.filrouge.jwt.TokenContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/gestaffectation")
public class AuthenticationRessource {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl myUserDetailService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createToken(@RequestBody AuthenticateDTO paramsIn) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(paramsIn.getUsername(),paramsIn.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password",e);
        }

        final UserDetails myUser = myUserDetailService.loadUserByUsername(paramsIn.getUsername());

        final String jwt = jwtUtils.generateToken(myUser);
        final Collection<? extends GrantedAuthority> roles = myUser.getAuthorities();

        return new ResponseEntity(new TokenContainer(jwt,roles), HttpStatus.OK);

    }
}
