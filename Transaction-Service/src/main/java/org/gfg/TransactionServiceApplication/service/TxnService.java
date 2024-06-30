package org.gfg.TransactionServiceApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.TransactionServiceApplication.model.Txn;
import org.gfg.TransactionServiceApplication.model.TxnStatus;
import org.gfg.TransactionServiceApplication.repository.TxnRepository;
import org.gfg.Utilities.CommonConstants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TxnService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String userContact) throws UsernameNotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("txn-service", "txn-service");
        HttpEntity reqEntity = new HttpEntity(httpHeaders);

        JSONObject object = restTemplate.exchange("http://localhost:8081/user/userDetails?contact="+userContact,
                            HttpMethod.GET, reqEntity, JSONObject.class).getBody();

        List<LinkedHashMap<String, String>> list = (List<LinkedHashMap<String, String>>)(object.get("authorities"));
        List<GrantedAuthority> reqAuthorities = list.stream().map(x-> x.get("authority")).map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList());
        User user = new User((String) object.get("username"), (String) object.get("password"), reqAuthorities);
        System.out.println(object);
        return user;
    }

    public String initTxn(String receiver, String purpose, String amount, String sender) throws JsonProcessingException {
       Txn txn = Txn.builder().
               txnId(UUID.randomUUID().toString()).
               amount(Double.valueOf(amount)).
               receiver(receiver).sender(sender).purpose(purpose).
               status(TxnStatus.INITIATED).
               build();
        txnRepository.save(txn);

        JSONObject object  = new JSONObject();
        object.put(CommonConstants.AMOUNT, txn.getAmount());
        object.put(CommonConstants.SENDER, txn.getSender());
        object.put(CommonConstants.RECEIVER, txn.getReceiver());
        object.put(CommonConstants.TXNID, txn.getTxnId());
        object.put(CommonConstants.STATUS, txn.getStatus());
        object.put(CommonConstants.PURPOSE, txn.getPurpose());
        kafkaTemplate.send(CommonConstants.TXN_INITIATED_TOPIC, mapper.writeValueAsString(object));

        return txn.getTxnId();
    }
}
