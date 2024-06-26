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
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
@Transactional
public class UserService {
    UserRepository userRepository;
    SubscribeRepository subscribeRepository;
    public void registerUser(long chatId){
        if (userRepository.findByChatId(chatId).isEmpty()){
            UserEntity userEntity = new UserEntity();
            userEntity.setChatId(chatId);
            userEntity.setSubscribeItems(new HashSet<>());
            userRepository.save(userEntity);
        }
    }
}
