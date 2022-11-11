package com.te.jspiders.service.impl;

import java.util.Optional;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.te.jspiders.entity.AppUser;
import com.te.jspiders.entity.Roles;
import com.te.jspiders.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

/*
 * This class is an implementation class to a service interface called
 * UserDetailsService. The interface is UserDetailsService and it is a path of
 * spring security module. It is an interface having only single abstract method
 * but not considered as functional interface. The method present should be
 * overridden and must be given the capability to read the data from the
 * database and create UserDetails type object.
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AppUserRepository appUserRepository;

	/*
	 * Locates the user based on the username. In the actual implementation, the
	 * search may possibly be case sensitive, or case insensitive depending on how
	 * the implementation instance is configured. In this case, the UserDetails
	 * object that comes back may have a username that is of a different case than
	 * what was actually requested. In our implementation after fetching the data
	 * from the database we receive AppUser object, which has to be converted to
	 * UserDetails type. But UserDeails being an interface cannot be used to create
	 * the object so we create the object on one of its implementing class called
	 * User. User object also needs GrantedAuthorities collection object hence we
	 * convert roles list into SimpleGrantedAuthorities collection object. This
	 * method is internally used by AuthenticationManager. JpaAuthentication cannot
	 * be achieved without this method implementation.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AppUser> optionalAU = appUserRepository.findByUsername(username);
		if (optionalAU.isPresent()) {
			AppUser appUser = optionalAU.get();
			Function<Roles, SimpleGrantedAuthority> function = r -> {
				return new SimpleGrantedAuthority(r.getRoleName());
			};
			Set<SimpleGrantedAuthority> authorities = appUser.getRoles().stream().map(function)
					.collect(Collectors.toSet());

			return new User(username, appUser.getPassword(), authorities);
		}
		throw new UsernameNotFoundException("User with username '" + username + "' does not exist!");
	}

}
