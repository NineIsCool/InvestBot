package com.example.investbot.adapter.repository;

import com.example.investbot.domain.SubscribeEntity;
import com.example.investbot.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Long> {
    Optional<SubscribeEntity> findByKeySearch(String keySearch);
    List<SubscribeEntity> findByUserEntities(UserEntity userEntity);
}
