package com.example.workshop26.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.workshop26.model.GameModel;


//IMPORT STATIC REMEMBER!!
import static com.example.workshop26.utils.Queries.*;

@Repository
public class GameRepo {
    
    @Autowired
    MongoTemplate template;

    public List<GameModel> getAllGames(Integer limit, Integer offset) {

        //THIS METHOD retrieves all data, then show the selected fields
        // Criteria criteria1 = Criteria.where("");
        // Query query = Query.query(criteria1).limit(limit).skip(offset);

        //ALTERNATIVE METHOD: retrieves all game names, then match its ID and show.
        //Query.query(new Criteria()).limit(limit).skip(offset) works too
        Query query = Query.query(Criteria.where("")).limit(limit).skip(offset);
        query.fields().include(FIELD_NAME);
        
        List<Document> docs = template.find(query, Document.class, COLLECTION_GAMES);
        LocalDateTime now = LocalDateTime.now();
        List<GameModel> games = docs.stream()
                                .map( x -> GameModel.fromMongoDocument(x))
                                .toList();
                                
        games.forEach(x -> x.setTimestamp(now)); //still don't know why

        return games;
    }

    public List<GameModel> getGamesByRanking(Integer limit, Integer offset) {

        Query query = Query.query(new Criteria()).limit(limit).skip(offset).with(Sort.by(Sort.Direction.ASC, FIELD_RANKING));
        query.fields().include(FIELD_NAME,FIELD_RANKING);

        List<Document> docs = template.find(query, Document.class, COLLECTION_GAMES);
        LocalDateTime now = LocalDateTime.now();

        List<GameModel> games = docs.stream()
                                    .map(x -> GameModel.fromMongoDocument(x))
                                    .toList();

        games.forEach(x -> x.setTimestamp(now));
        
        return games;
    }

    public Optional<GameModel> getGameById(String id) {

        try{

        Query query = Query.query(Criteria.where(FIELD_ID).is(id));

        Document docs = template.findOne(query, Document.class, COLLECTION_GAMES);

            // GameModel game = (null == docs) ? null : GameModel.fromMongoDocument(docs);
            
            // return Optional.ofNullable(game);

            return (null == docs) ? Optional.empty() : Optional.of(GameModel.fromMongoDocument(docs));
        
        
        // GameModel games = docs.isEmpty() ? null : GameModel.fromMongoDocument(docs);

        // return (rs.next()) ? Optional.of(Model.fromSQL(rs)) : Optional.empty();     NOT RELATED, USED IN SQL        

        // return Optional.ofNullable(games);

        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
        
    }
}
