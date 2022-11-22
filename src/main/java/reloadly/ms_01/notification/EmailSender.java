package reloadly.ms_01.notification;

import org.springframework.scheduling.annotation.Async;
import reloadly.ms_01.model.Account;

public interface EmailSender {

    @Async
    void send(Account acc);
}
