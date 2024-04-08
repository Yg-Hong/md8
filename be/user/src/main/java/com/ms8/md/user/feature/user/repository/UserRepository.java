package com.ms8.md.user.feature.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms8.md.user.feature.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	@Query("select u from User u where u.isWithdrawal = false "
		+ "and u.id != :userId "
		+ "and u.nickName like CONCAT('%', :nickName, '%') ")
	List<User> findByNickName(@Param("userId") Integer userId, @Param("nickName") String nickName);
}
