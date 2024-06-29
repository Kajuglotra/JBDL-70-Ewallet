package org.gfg.NotificationServiceApplication.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.Utilities.CommonConstants;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Autowired
    private JavaMailSender sender;
    private static Logger logger = LoggerFactory.getLogger(UserCreatedConsumer.class);

    @KafkaListener(topics = {CommonConstants.USER_CREATED_TOPIC}, groupId = "notification-group")
    public void sendNotification(String msg) throws JsonProcessingException {
        JSONObject jsonObject = objectMapper.readValue(msg, JSONObject.class);
        String name = (String) jsonObject.get(CommonConstants.USER_NAME);
        String email = (String) jsonObject.get(CommonConstants.USER_EMAIl);

        // send a mail
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText("Welcome "+ name + " to the platform!!");
        simpleMailMessage.setSubject("EWallet User Created | "+ name);
        simpleMailMessage.setFrom("ewalletjbdl70@gmail.com");
        sender.send(simpleMailMessage);

        logger.info("Mail has been sent to the user");
    }
}
