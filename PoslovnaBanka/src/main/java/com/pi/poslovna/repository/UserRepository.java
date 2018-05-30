package com.pi.poslovna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.poslovna.model.users.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findOneByEmail(String email);
}
