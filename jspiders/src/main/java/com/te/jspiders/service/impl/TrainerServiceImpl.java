package com.te.jspiders.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.te.jspiders.dto.TrainerDto;
import com.te.jspiders.entity.AppUser;
import com.te.jspiders.entity.Roles;
import com.te.jspiders.entity.Trainer;
import com.te.jspiders.repository.AppUserRepository;
import com.te.jspiders.repository.RoleRepository;
import com.te.jspiders.repository.TrainerRepository;
import com.te.jspiders.service.TrainerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrainerServiceImpl implements TrainerService {
	private final TrainerRepository trainerRepository;
	private final RoleRepository roleRepository;
	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Optional<String> registerTrainer(TrainerDto trainerDto) {
		log.info("TrainerServiceImpl:registerTrainer execution start, {}", trainerDto);
		Trainer trainer = new Trainer();
		BeanUtils.copyProperties(trainerDto, trainer);
		log.debug("TrainerServiceImpl:registerTrainer, student entity object created {}", trainer);

		Optional<Roles> employeeRole = roleRepository.findByRoleName("ROLE_TRAINER");
		if (employeeRole.isPresent()) {
			Roles roles = employeeRole.get();
			AppUser appUser = AppUser.builder().username(trainer.getTrainerId())
					.password(passwordEncoder.encode(trainerDto.getPassword())).roles(Lists.newArrayList()).build();
			roles.getAppUsers().add(appUser);
			appUser.getRoles().add(roles);
			appUserRepository.save(appUser);
		}
		return Optional.ofNullable(trainerRepository.save(trainer).getTrainerId());
	}
}
