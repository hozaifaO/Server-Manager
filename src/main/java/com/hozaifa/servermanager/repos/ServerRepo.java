package com.hozaifa.servermanager.repos;

import com.hozaifa.servermanager.Model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);
}
