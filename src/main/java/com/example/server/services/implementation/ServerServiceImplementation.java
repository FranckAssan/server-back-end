package com.example.server.services.implementation;

import com.example.server.enumerations.Status;
import com.example.server.models.Server;
import com.example.server.repository.ServerRepository;
import com.example.server.services.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

/**
 * Implement all the feature of the ServerService
 */
@RequiredArgsConstructor // Lombok - add the constructor for serverRepository
@Service
@Transactional
@Slf4j                  // Print info in the console
public class ServerServiceImplementation implements ServerService {

    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Saving a new Server: {}", server.getName());
        server.setImageUrl(setImageUrl());
        return serverRepository.save(server);
    }

    /**
     * Ping a Server
     * @param ipAddress
     * @return
     * @throws IOException
     */
    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);

        server.setStatus(address.isReachable(10000) ? Status.SERVER_UP :
                Status.SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    /**
     * Get the all the server but just the number set by the limit
     * @param limit The number of server to return
     * @return list of servers
     */
    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating the server: {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server: {}", id);
        serverRepository.deleteById(id);
        return Boolean.TRUE;
    }

    /**
     * Set the image of the server
     * @return the image URL
     */
    private String setImageUrl() {
        String [] imgArray = {"server1.png", "server2.png", "server3.png"};
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/server/image/" +
                        imgArray[new Random().nextInt(3)]).toUriString();
    }

}
