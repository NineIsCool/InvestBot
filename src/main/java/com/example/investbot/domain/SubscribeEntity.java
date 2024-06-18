package com.example.investbot.domain;

import com.example.investbot.domain.common.HibernateSequence;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "subscribes")
public class SubscribeEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = HibernateSequence.NAME)
    @SequenceGenerator(name = HibernateSequence.NAME, allocationSize = HibernateSequence.ALLOCATION_SIZE)
    Long id;
    String name;
    String type;
    String keySearch;
    @ManyToMany(mappedBy = "subscribeItems")
    Set<UserEntity> userEntities;
}
