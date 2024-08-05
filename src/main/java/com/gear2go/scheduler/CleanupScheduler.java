package com.gear2go.scheduler;

import com.gear2go.entity.enums.Role;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CleanupScheduler {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredGuestUsers() {

        userRepository.deleteAll(userRepository.findUserByRoleAndExpirationDateIsGreaterThan(Role.GUEST, new Date()));
    }

}
