package org.gfg.TransactionServiceApplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gfg.TransactionServiceApplication.service.TxnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/initTxn")
    public String initTxn(@RequestParam("receiver") String receiver,
                          @RequestParam("purpose") String purpose,
                          @RequestParam("amount") String amount) throws JsonProcessingException {

        UserDetails details= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return txnService.initTxn(receiver, purpose, amount, details.getUsername());
    }



}
