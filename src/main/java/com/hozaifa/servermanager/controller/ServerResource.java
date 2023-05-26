package com.hozaifa.servermanager.controller;

import com.hozaifa.servermanager.enums.Status;
import com.hozaifa.servermanager.model.Response;
import com.hozaifa.servermanager.model.Server;
import com.hozaifa.servermanager.service.implimentation.ServerServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static java.time.LocalTime.now;

@RequiredArgsConstructor
@RestController
@RequestMapping("/server")
public class ServerResource {
    private final ServerServiceImp serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(LocalDateTime.from(now()))
                .data(Map.of("servers", serverService.list(30)))
                .message("server retrieved")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
    @GetMapping("/ping{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.from(now()))
                        .data(Map.of("servers", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "ping success":"ping fail")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

}
