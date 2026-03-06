package br.com.edras.picpaysimplificado.listener;

import br.com.edras.picpaysimplificado.event.TransactionCompletedEvent;
import org.springframework.transaction.event.TransactionalEventListener;
import br.com.edras.picpaysimplificado.service.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @TransactionalEventListener
    public void handleTransactionCompleted(TransactionCompletedEvent event) {
        notificationService.sendTransactionNotification(event.getTransaction());
    }

}