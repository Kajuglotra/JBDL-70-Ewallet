package org.gfg.WalletServiceApplication.repository;

import org.gfg.WalletServiceApplication.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
}
