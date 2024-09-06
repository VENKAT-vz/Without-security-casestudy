package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login,String>{

    Login findByUsername(String username);
    Login findByEmailid(String emailid);

}
