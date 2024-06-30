package org.gfg.WalletServiceApplication.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.Utilities.CommonConstants;
import org.gfg.WalletServiceApplication.model.Wallet;
import org.gfg.WalletServiceApplication.repository.WalletRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TxnInitiatedConsumer {

    @Autowired
    private ObjectMapper objectMapper;

//    @Value("${wallet-group-id}")
//    private static final String walletGroupId = "";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private WalletRepository walletRepository;

    @KafkaListener(topics = CommonConstants.TXN_INITIATED_TOPIC, groupId = "wallet-group")
    public void updateWallet(String msg) throws JsonProcessingException {
        JSONObject object = objectMapper.readValue(msg, JSONObject.class);
        String sender = (String) object.get(CommonConstants.SENDER);
        String receiver = (String) object.get(CommonConstants.RECEIVER);
        Double amount = (Double) object.get(CommonConstants.AMOUNT);
        String purpose = (String) object.get(CommonConstants.PURPOSE);
        String txnId = (String) object.get(CommonConstants.TXNID);

        // sender has a wallet associated ,  receiver has a wallet associated
        Wallet senderWallet = walletRepository.findByContact(sender);
        Wallet receiverWallet = walletRepository.findByContact(receiver);
        // message, status,
        String message = "txn is initiated";
        String status = "pending";

        if(senderWallet == null){
            message = "sender wallet is not associated with us";
            status = "failed";
        }else if(receiverWallet == null){
            message = "receiver wallet is not associated with us";
            status = "failed";
        }else if(amount > senderWallet.getBalance()){
            message = "sender wallet amount  is less than the amount of txn he wants to make";
            status = "failed";
        }else{
            walletRepository.updateWallet(sender, -amount);
            walletRepository.updateWallet(receiver, amount);
            message = "txn is suceess";
            status = "success";
        }

        JSONObject resp = new JSONObject();
        resp.put(CommonConstants.MESSAGE, message);
        resp.put(CommonConstants.STATUS, status);
        resp.put(CommonConstants.TXNID, txnId);

        kafkaTemplate.send(CommonConstants.TXN_UPDATED_TOPIC, resp);
    }

}
