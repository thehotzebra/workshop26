package com.example.workshop26.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.workshop26.service.GameService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping
public class GameController {
    
    @Autowired
    GameService gameSvc;

    @GetMapping(path="/games",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllGame(@RequestParam(defaultValue= "25") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {

        JsonObject payload = gameSvc.findGamesLimitOffset(limit, offset);

        return ResponseEntity
               .status(HttpStatus.OK)
               .contentType(MediaType.APPLICATION_JSON)
               .body(payload.toString());

    }

    @GetMapping(path="/rank", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGamesByRanking(@RequestParam(defaultValue = "25") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {

        JsonObject payload = gameSvc.findGamesByRanking(limit, offset);

        return ResponseEntity
               .status(HttpStatus.OK)
               .contentType(MediaType.APPLICATION_JSON)
               .body(payload.toString());
    }

    @GetMapping(path="/game/{id}")
    public ResponseEntity<String> getGamesByRank(@PathVariable String id) {

        Optional<JsonObject> payload = gameSvc.findGameById(id);
        System.out.println("payload ->" + payload);
        
        // System.out.println(">>> \n\n\n\n\n\n" + payload == null);

        if(payload.isEmpty()) {
            return ResponseEntity
                   .status(HttpStatus.NOT_FOUND)
                   .contentType(MediaType.APPLICATION_JSON)
                   .body(Json.createObjectBuilder()
                   .add("status", HttpStatus.NOT_FOUND.value())
                   .add("message", "Game with id %s not found".formatted(id))
                   .build().toString()
                   );
        }
        
        return ResponseEntity
               .status(HttpStatus.OK)
               .contentType(MediaType.APPLICATION_JSON)
               .body(payload.get().toString());
    }
}
