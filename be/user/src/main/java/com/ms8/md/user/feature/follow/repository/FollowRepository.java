package com.ms8.md.user.feature.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms8.md.user.feature.follow.entity.Follow;
import com.ms8.md.user.feature.follow.repository.custom.FollowCustomRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer>, FollowCustomRepository {


}
