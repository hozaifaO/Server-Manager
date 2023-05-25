package com.hozaifa.servermanager.service.implimentation;

import com.hozaifa.servermanager.Model.Server;
import com.hozaifa.servermanager.enums.Status;
import com.hozaifa.servermanager.repos.ServerRepo;
import com.hozaifa.servermanager.service.ServerService;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;



@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImp implements ServerService {
    private final ServerRepo serverRepo;
    @Override
    public Server create(Server server) {
        log.info("Saving Server: {}",server.getName());
        server.setImgUrl(setServerImgUrl());
        return serverRepo.save(server);
    }



    @Override
    public Collection<Server> list(int limit) {
        log.info("Getting All Servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Getting Server By ID: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating Server: {}",server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting Server by ID: {}",id.toString());
        serverRepo.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging Server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        server.setStatus(inetAddress.isReachable(10000) ? Status.SERVER_UP: Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    private String setServerImgUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/src/Images/server.png").toUriString();
    }
}
