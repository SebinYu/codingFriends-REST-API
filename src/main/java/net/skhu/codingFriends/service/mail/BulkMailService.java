package net.skhu.codingFriends.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.skhu.codingFriends.entity.user;
import net.skhu.codingFriends.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BulkMailService {
    private final MailService mailService;
    private final JavaMailSender emailSender;
    private final UserRepository userRepository;

    @Async
    @Scheduled(fixedRate = 10000) // 단위: ms
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(1000))
    public CompletableFuture<String> sendMailToNoticeEventBulkUsers() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(10);

        CompletableFuture<String> msFuture = CompletableFuture.supplyAsync(() -> {
            List<user> users = userRepository.findByAddressLike("서울");

            SimpleMailMessage message = new SimpleMailMessage();

            for (user oneUser : users) {
                mailService.sendmailTo(oneUser,"notice", null);
            }
            return "이메일 전송 성공";
        }, executor).exceptionally(ex -> {
            System.out.println(ex);
            return "전송 실패";
        });
        msFuture.get();
        return null;
    }
}
