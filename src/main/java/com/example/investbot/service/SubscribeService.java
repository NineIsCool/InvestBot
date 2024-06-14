package com.example.investbot.service;

import com.example.investbot.adapter.repository.SubscribeRepository;
import com.example.investbot.adapter.repository.UserRepository;
import com.example.investbot.domain.SubscribeEntity;
import com.example.investbot.domain.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
@Transactional
public class SubscribeService {
    SubscribeRepository subscribeRepository;
    UserRepository userRepository;
    public void subscribeItem(Long chatId, String type, String keySearch){
        Optional<SubscribeEntity> subscribe = subscribeRepository.findByKeySearch(keySearch);
        UserEntity userEntity = userRepository.findByChatId(chatId);
        if (!subscribe.isEmpty()){
            subscribe.get().getUserEntities().add(userEntity);
            userEntity.getSubscribeItems().add(subscribe.get());
        }else {
            SubscribeEntity subscribeEntity = new SubscribeEntity();
            subscribeEntity.setType(type);
            subscribeEntity.setKeySearch(keySearch);
            Set<UserEntity> userEntitySet = new HashSet<>();
            userEntitySet.add(userEntity);
            subscribeEntity.setUserEntities(userEntitySet);
            userEntity.getSubscribeItems().add(subscribeEntity);
            subscribeRepository.save(subscribeEntity);
        }
    }
}
