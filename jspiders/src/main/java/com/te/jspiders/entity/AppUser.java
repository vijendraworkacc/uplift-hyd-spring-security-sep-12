package com.te.jspiders.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * We have created multiple entity classes and  to store the credentials we have created 
 * this class called AppUser. AppUser has two fields username and password.
 * 
 * Any type of ID from any entity class can be stored as username in AppUser.
 * 
 * This class also has a bi-directional ManyToMany relationship with Roles class to 
 * store user roles.
 * 
 * The relationship is ManyToMany because so that a user can have N number of roles 
 * and a role can be belonging to N number of users.
 * 
 * We are using fetch type as EAGER to deal with LazyInstantiationException.
 * */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class AppUser {
	@Id
	private String username;
	private String password;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "map_users_roles", joinColumns = @JoinColumn(name = "user_fk"), inverseJoinColumns = @JoinColumn(name = "role_fk"))
	private List<Roles> roles = Lists.newArrayList();
}
