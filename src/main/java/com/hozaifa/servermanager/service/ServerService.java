package com.hozaifa.servermanager.service;

import com.hozaifa.servermanager.Model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server server);
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
    Server ping(String ipAddress) throws IOException;
}
