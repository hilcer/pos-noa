package com.noa.pos.imp.security;

import com.noa.pos.dto.UserDto;
import com.noa.pos.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
//        return UserDto.UserDtoBuilder().userId(user.getUserId())
//                .email(user.getEmail())
//                .user(user.getUser())
//                .address(user.getAddress())
//                .enabled(user.getEnabled())
//                .profileId(user.getProfileId())
//                .city(user.getCity()).build();
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUser();
            }
        };
    }
}
