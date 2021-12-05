package com.example.server.resource;

import com.example.server.enumerations.Status;
import com.example.server.models.Response;
import com.example.server.models.Server;
import com.example.server.services.implementation.ServerServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor // For dependency injection of the ServerService
public class ServerResource {

    private final ServerServiceImplementation serverService;

    /**
     * Get the list of Servers
     * @return List of Servers
     */
    @GetMapping("/list")
    public ResponseEntity<Response> getListServers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("servers",
                                this.serverService.list(30)))
                        .message("Server retrived")
                        .statusCode(OK)
                        .build()
        );
    }


    /**
     * Get a server by id
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server",
                                this.serverService.get(id)))
                        .message("server retrived")
                        .status(HttpStatus.OK)
                        .statusCode(OK)
                        .build()
        );
    }


    /**
     * Ping the Server
     * @param ipAddress
     * @return
     * @throws IOException
     */
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String
                                               ipAddress) throws IOException {

        Server server = this.serverService.ping(ipAddress);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", server))
                        .status(HttpStatus.OK)
                        .message(server.getStatus() == Status.SERVER_UP ?
                                "Ping success" : "Ping failed")
                        .statusCode(OK)
                        .build()
        );
    }


    /**
     * Saved the server received from the body request (Forms)
     * @param server
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("server", this.serverService.create(server)))
                        .message("Server created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }


    /**
     * Delete a server by id
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("deleted",
                                this.serverService.delete(id)))
                        .message("server deleted")
                        .status(HttpStatus.OK)
                        .statusCode(OK)
                        .build()
        );
    }


    /**
     * Return the image of the server
     * @param fileName
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName)
            throws IOException {

        return Files.readAllBytes(Paths.get(System.getProperty("user.home") +
                "/Desktop/server_img/" + fileName));
    }


}
