package com.example.investbot.domain.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class HibernateSequence {
    public static final String NAME = "hibernate_sequence";
    public static final int ALLOCATION_SIZE = 30;
}
