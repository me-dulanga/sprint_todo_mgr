package com.example.todo_mgr.Security;

import java.util.HashSet;
import java.util.Set;

import com.example.todo_mgr.Model.User;
import com.example.todo_mgr.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    private User loggedUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        loggedUser = userRepository.findUserByName(username);
        if(loggedUser == null){
            throw new UsernameNotFoundException("Invalid user: "+ username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        return new org.springframework.security.core.userdetails.User(loggedUser.getName(), loggedUser.getPassword(), grantedAuthorities);
    }

    // @Bean
    public User getLoggedUser(){
        return this.loggedUser;
    }

}