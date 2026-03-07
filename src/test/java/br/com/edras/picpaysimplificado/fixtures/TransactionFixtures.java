package br.com.edras.picpaysimplificado.fixtures;

import br.com.edras.picpaysimplificado.entity.CommonUser;
import br.com.edras.picpaysimplificado.entity.Transaction;
import br.com.edras.picpaysimplificado.entity.enums.TransactionStatus;

import java.time.LocalDateTime;

public class TransactionFixtures {

    public static Transaction createTransaction() {
        CommonUser payer = CommonUserFixtures.createValidCommonUser();
        CommonUser payee = CommonUserFixtures.createValidCommonUser();
        payer.setId(1L);
        payee.setId(2L);

        Transaction transaction = new Transaction();

        transaction.setAmount(100.0);
        transaction.setPayer(payer);
        transaction.setPayee(payee);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.AUTHORIZED);
        return transaction;
    }

}
