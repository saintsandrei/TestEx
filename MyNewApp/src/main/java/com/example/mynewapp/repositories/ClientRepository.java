package com.example.mynewapp.repositories;

import com.example.mynewapp.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Settings,String> {
    Settings findByLogin(String login);
}
