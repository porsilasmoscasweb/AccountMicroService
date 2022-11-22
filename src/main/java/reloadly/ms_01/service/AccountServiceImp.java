package reloadly.ms_01.service;

import reloadly.ms_01.dao.AccountDAO;
import reloadly.ms_01.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reloadly.ms_01.notification.EmailService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    AccountDAO account;

    @Autowired
    EmailService emailService;

    @Override
    public boolean register(Account ac) {
        this.create(ac);
        this.emailService.send(ac);
        return true;
    }

    @Override
    public List<Account> all() {
        return account.all();
    }

    @Override
    public Account find(Long id) {
        return account.find(id);
    }

    @Override
    public Account findByName(String name) {
        return account.findByName(name);
    }

    @Override
    public boolean create(Account ac) {
        if (account.find(ac.getId()) == null) {
            ac.setCreated_at(LocalDateTime.now());
            account.create(ac);
            return true;
        }

        return false;
    }

    @Override
    public boolean update(Account ac) {
        Account a = account.find(ac.getId());
        if (a != null) {
            ac.setUpdated_at(LocalDateTime.now());
            account.update(ac);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (account.find(id) != null) {
            account.delete(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteByName(String name) {
        if (account.findByName(name) != null) {
            account.deleteByName(name);
            return true;
        }

        return false;
    }
}
