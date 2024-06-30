package org.gfg.TransactionServiceApplication.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
//@RequiredArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Txn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    private String txnId;

    private Double amount;

    private String sender;

    private String receiver;

    private String purpose;

    @Enumerated(value = EnumType.STRING)
    private TxnStatus status;

}
