package org.gfg.UserServiceApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.UserServiceApplication.dtos.UserRequestDTO;
import org.gfg.UserServiceApplication.model.Users;
import org.gfg.UserServiceApplication.repository.UserRepository;
import org.gfg.Utilities.CommonConstants;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.Authority}")
    private String userAuthority;

    @Value("${admin.Authority}")
    private String adminAuthority;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public Users addUpdate(UserRequestDTO dto) throws JsonProcessingException {
        Users user = dto.toUser();
        user.setAuthorities(userAuthority);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user =  userRepository.save(user);
//        wallet service , send a mail that the user has been created
//         i want to push a message to kafka queue that user has been created

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CommonConstants.USER_CONTACT, user.getContact());
        jsonObject.put(CommonConstants.USER_EMAIl, user.getEmail());
        jsonObject.put(CommonConstants.USER_NAME , user.getName());
        jsonObject.put(CommonConstants.USER_IDENTIFIER , user.getIdentifier());
        jsonObject.put(CommonConstants.USER_IDENTIFIER_VALUE , user.getUserIdentifierValue());
        jsonObject.put(CommonConstants.USER_ID, user.getPk());

        logger.info("json object as a string " + jsonObject);
        logger.info("json object as a string by objectMapper.writeValueAsString(jsonObject) " + objectMapper.writeValueAsString(jsonObject));

        kafkaTemplate.send(CommonConstants.USER_CREATED_TOPIC,objectMapper.writeValueAsString(jsonObject));
        return user;
    }

    @Override
    public Users loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByContact(username);
        System.out.println("got the user Details" + users);
        return users;
    }
}
