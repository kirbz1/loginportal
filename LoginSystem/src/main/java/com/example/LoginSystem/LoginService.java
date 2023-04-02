package com.example.LoginSystem;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    @Autowired
    private LoginRepository repo;

    public List<Account> listAll() {
        return repo.findAll();
    }

    public void save(Account account) {
        repo.save(account);
    }

    public Account get(int id) {
        return repo.findById(id).get();
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

}
