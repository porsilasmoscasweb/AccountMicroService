package reloadly.ms_01.dao;

import reloadly.ms_01.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDAOImp implements AccountDAO {

    @Autowired
    AccountJpaSpring account;

    @Override
    public List<Account> all() {
        return account.findAll();
    }

    @Override
    public Account find(Long id) {
        if(id == null) {
            return null;
        }

        return account.findById(id).orElse(null);
    }

    @Override
    public Account findByName(String name) {
        return account.findByName(name);
    }

    @Override
    public void create(Account ac) {
        account.save(ac);
    }

    @Override
    public void update(Account ac) {
        account.save(ac);
    }

    @Override
    public void delete(Long id) {
        account.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        account.deleteByName(name);
    }
}
