package com.te.jspiders.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.te.jspiders.utils.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;

/*
 * After successfully implementation registration and login api, and also
 * generation token after successful login. We created a custom filter using
 * OncePerRequestFilter. The filter has method called doFilterInternal() which
 * executes whenever a request hits the application before going to the controller.
 */
@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final UserDetailsService userDetailsService;

	/*
	 * This method first extracts the bearer token from the request header. The
	 * header is of type Authorization. Then we checked if the token is not null and
	 * starts with the word "Bearer ". When above condition was true we again
	 * extracted the actual token using substring. We then used JwtUtils
	 * getUsername() method to generate the username out of the token and checked
	 * whether there is a user with the same username in the database or not. It is
	 * also very important to check the SecurityContextHolder having SecurityContext
	 * information, it should be null in the beginning i.e. no user has got
	 * authenticated.
	 * 
	 * The SecurityContext and SecurityContextHolder are two fundamental classes of
	 * Spring Security. The SecurityContext is used to store the details of the
	 * currently authenticated user, also known as a principle.
	 * 
	 * The SecurityContextHolder is a helper class, which provides access to the
	 * security context.
	 * 
	 * After this we validate the token against the username stored in the database
	 * using JwtUtils validateToken method. Then we create
	 * UsernamePasswordAuthenticationToken type object and set the details because
	 * to set the information about the currently authenticated user in the
	 * SecurityContext we need Authentication type object and
	 * UsernamePasswordAuthenticationToken is Authentication type.
	 * 
	 * But if the token is not received we call another filter through the
	 * filterChain object which will take the control forward.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			String token = bearerToken.substring(7);
			String usernameFromToken = jwtUtils.getUsername(token);
			UserDetails userFromDb = userDetailsService.loadUserByUsername(usernameFromToken);
			if (usernameFromToken != null && userFromDb.getUsername() != null
					&& SecurityContextHolder.getContext().getAuthentication() == null) {
				if (jwtUtils.validateToken(token, userFromDb.getUsername())) {

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userFromDb.getUsername(), userFromDb.getPassword(), userFromDb.getAuthorities());

					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
