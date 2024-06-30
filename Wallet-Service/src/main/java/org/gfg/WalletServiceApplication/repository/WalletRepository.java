package org.gfg.WalletServiceApplication.repository;

import jakarta.transaction.Transactional;
import org.gfg.WalletServiceApplication.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Wallet findByContact(String contact);


    @Transactional
    @Modifying
    @Query("update Wallet w set w.balance = w.balance + :amount where w.contact= :contact")
    void updateWallet(String contact, double amount);
}
