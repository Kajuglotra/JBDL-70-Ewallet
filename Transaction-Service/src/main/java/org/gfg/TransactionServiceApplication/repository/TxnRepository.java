package org.gfg.TransactionServiceApplication.repository;

import org.gfg.TransactionServiceApplication.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn, Integer> {
}
