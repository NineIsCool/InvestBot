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
    InvestService investService;

    public List<SubscribeEntity> getAllSubscribes(long chatId){
        UserEntity userEntity = userRepository.findByChatId(chatId).get();
        return subscribeRepository.findByUserEntities(userEntity);
    }
    public void subscribeItem(Long chatId, String type, String keySearch, String name){
        Optional<SubscribeEntity> subscribe = subscribeRepository.findByKeySearch(keySearch);
        UserEntity userEntity = userRepository.findByChatId(chatId).get();
        if (!subscribe.isEmpty()){
            subscribe.get().getUserEntities().add(userEntity);
            userEntity.getSubscribeItems().add(subscribe.get());
        }else {
            SubscribeEntity subscribeEntity = new SubscribeEntity();
            subscribeEntity.setType(type);
            subscribeEntity.setKeySearch(keySearch);
            subscribeEntity.setName(name);
            Set<UserEntity> userEntitySet = new HashSet<>();
            userEntitySet.add(userEntity);
            subscribeEntity.setUserEntities(userEntitySet);
            userEntity.getSubscribeItems().add(subscribeEntity);
            subscribeRepository.save(subscribeEntity);
        }
    }
    public String findSubscribeItem(Long chatId, int indexItem){
        SubscribeEntity subscribeEntity = getAllSubscribes(chatId).get(indexItem-1);
        if (subscribeEntity.getType().equals("currency")){
            return investService.findCurrency(subscribeEntity.getKeySearch());
        }else {
            return investService.getStockByUID(subscribeEntity.getKeySearch());
        }
    }

}
