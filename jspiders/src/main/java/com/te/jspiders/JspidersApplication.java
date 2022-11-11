package com.te.jspiders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.collect.Lists;
import com.te.jspiders.entity.Admin;
import com.te.jspiders.entity.AppUser;
import com.te.jspiders.entity.Roles;
import com.te.jspiders.repository.AdminRepository;
import com.te.jspiders.repository.AppUserRepository;
import com.te.jspiders.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class JspidersApplication {

	private final AdminRepository adminRepository;
	private final RoleRepository roleRepository;
	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(JspidersApplication.class, args);
	}

	/*
	 * CommandLineRunner is used to execute the code when the application started to
	 * execute. And here we are storing the roles so that the are readily available
	 * in the database. Also store admin data in Admin table and AppUser table.
	 */
	@Bean
	public CommandLineRunner runner() {
		return args -> {
			Roles student = Roles.builder().roleName("ROLE_STUDENT").build();
			Roles employee = Roles.builder().roleName("ROLE_EMPLOYEE").build();
			Roles trainer = Roles.builder().roleName("ROLE_TRAINER").build();
			Roles admin = Roles.builder().roleName("ROLE_ADMIN").appUsers(Lists.newArrayList()).build();

			Admin admin01 = Admin.builder().adminId("ADMIN01").adminName("admin01").build();

			AppUser adminCredentials = AppUser.builder().username(admin01.getAdminId())
					.password(passwordEncoder.encode("qwerty")).roles(Lists.newArrayList()).build();

			roleRepository.save(student);
			roleRepository.save(employee);
			roleRepository.save(trainer);

			adminRepository.save(admin01);

			adminCredentials.getRoles().add(admin);
			admin.getAppUsers().add(adminCredentials);

			roleRepository.save(admin);
			appUserRepository.save(adminCredentials);
		};
	}

}
