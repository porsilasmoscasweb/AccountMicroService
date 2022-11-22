package reloadly.ms_01.service;

import reloadly.ms_01.model.Account;

import java.util.List;

public interface AccountService {

    boolean register(Account ac);

    List<Account> all();

    Account find(Long id);

    Account findByName(String name);

    boolean create(Account ac);

    boolean update(Account ac);

    boolean delete(Long id);

    boolean deleteByName(String name);

}
