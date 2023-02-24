package com.example.workshop26.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.workshop26.model.GameModel;
import com.example.workshop26.repository.GameRepo;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Service
public class GameService {
    
    @Autowired
    GameRepo gameRepo;

    public JsonObject findGamesLimitOffset (Integer limit, Integer offset) {

        List<GameModel> games = gameRepo.getAllGames(limit, offset);
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (GameModel game: games) {
            arrBuilder.add(game.toJsonShort());
        }

        return Json.createObjectBuilder()
                .add("games", arrBuilder)
                .add("offset", offset)
                .add("limit", limit)
                .add("total", games.size())
                .add("timeStamp", games.get(0).getTimestamp().toString())
                .build();
    }

    public JsonObject findGamesByRanking (Integer limit, Integer offset) {

        List<GameModel> games = gameRepo.getGamesByRanking(limit, offset);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (GameModel game: games) {
                arrBuilder.add(game.toJsonShortRanking());
        }

        return Json.createObjectBuilder()
                   .add("games", arrBuilder)
                   .add("offset", offset)
                   .add("limit", limit)
                   .add("total", games.size())
                   .add("timeStamp", games.get(0).getTimestamp().toString())
                   .build();
    }

    public Optional<JsonObject> findGameById (String id) {

        Optional<GameModel> games = gameRepo.getGameById(id);

        if(games.isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.of(games.get().toJsonLong());
    
    }

}
