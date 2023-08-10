package net.skhu.codingFriends.service;

import lombok.RequiredArgsConstructor;
import net.skhu.codingFriends.entity.User;
import net.skhu.codingFriends.service.event.EventFacade;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class test {
    private final EventFacade eventFacade;

    public void createEventTicketTest(Long eventId, User user) {

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(100);


        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    try {
                        eventFacade.createEventTicketBroker(eventId, user);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }  finally {
                    countDownLatch.countDown();
                }
            });
        }

    }

}
