package com.example.server.repository;

import com.example.server.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long > {

    /**
     * Find a Server by IP address
     * @param ipAddress the ip address
     * @return the server
     */
    Server findByIpAddress(String ipAddress);
}
