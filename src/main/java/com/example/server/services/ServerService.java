package com.example.server.services;

import com.example.server.models.Server;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;

/**
 * The list of features we want in our Application
 */
public interface ServerService {

    Server create(Server server);
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
    Server ping(String ipAddress) throws IOException;

}
