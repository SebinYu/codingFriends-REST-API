package net.skhu.codingFriends.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.skhu.codingFriends.DesignPattern.Strategy.mailMessage.MailInfo;
import net.skhu.codingFriends.entity.studygroup;
import net.skhu.codingFriends.entity.user;
import net.skhu.codingFriends.exception.studygroup.StudygroupIdNotFound;
import net.skhu.codingFriends.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BulkMailService {

    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    //특정 쿼리조건 만족 사용자 -> 메일 보내기
    //메일은 대용량 데이터 관리 → @Scheduled/@Async 적용

    // api 만들기 (0)
    //쿼리 적용 메일전송 메서드 (0)
    // @Scheduled/@Async 적용 (0)
    @Async
    @Scheduled(fixedRate=100000000) // 단위: ms
    @Transactional(readOnly = true)
    public void sendMailToNoticeEventBulkUsers() {


        List<user> users = userRepository.findByAddressLike("서울");

            SimpleMailMessage message = new SimpleMailMessage();

        for (user oneUser : users) {
            String to = oneUser.getEmail();
            message.setTo(to);
            message.setFrom("codingfriend7@gmail.com");
            message.setSubject("서울 거주자 이벤트!");
            message.setText("서울 거주자 안내 메일입니다.");

            emailSender.send(message);
        }


    }
}