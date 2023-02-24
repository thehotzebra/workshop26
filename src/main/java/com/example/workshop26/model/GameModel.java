package com.example.workshop26.model;

import java.time.LocalDateTime;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import static com.example.workshop26.utils.Queries.*;

public class GameModel {

    private String id;
    private String name;
    private Integer gid;
    private Integer year;
    private Integer ranking;
    private String average;
    private Integer usersRated;
    private String url;
    private String thumbnail;
    private LocalDateTime timeStamp;
    
    //get the data from mongodb and set it a model object
    public static GameModel fromMongoDocument(Document doc) {
        GameModel game = new GameModel();
        game.setId(doc.getObjectId(FIELD_ID).toString());
        game.setGid(doc.getInteger(FIELD_GAME_ID));
        game.setName(doc.getString(FIELD_NAME));
        game.setYear(doc.getInteger(FIELD_YEAR));
        game.setRanking(doc.getInteger(FIELD_RANKING));
        game.setUsersRated(doc.getInteger(FIELD_USERS_RATED));
        game.setUrl(doc.getString(FIELD_URL));
        game.setThumbnail(doc.getString(FIELD_THUMBNAIL));
        return game;

    }

    public JsonObject toJsonShort() {
        return Json.createObjectBuilder()
                .add("_id", this.getId())
                .add("name", this.getName())
                // .add("year", this.getYear()) //if wish to do add, FIELD_YEAR has to be included in query.fields()
                .build();
    }

    public JsonObject toJsonShortRanking() {
        return Json.createObjectBuilder()
                .add("_id", this.getId())
                .add("name", this.getName())
                .add("ranking", this.getRanking())
                // .add("year", this.getYear()) //if wish to do add, FIELD_YEAR has to be included in query.fields()
                .build();
    }
    
    public JsonObject toJsonLong() {
        return Json.createObjectBuilder()
               .add("_id", this.getId())
               .add("gid", this.getGid())
               .add("name", this.getName())
               .add("year", this.getYear())
               .add("ranking", this.getRanking())
               .add("usersRated", this.getUsersRated())
               .add("url", this.getUrl())
               .add("thumbnail", this.getThumbnail())
               .build();

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public String getAverage() {
        return average;
    }
    public void setAverage(String average) {
        this.average = average;
    }
    public Integer getUsersRated() {
        return usersRated;
    }
    public void setUsersRated(Integer usersRated) {
        this.usersRated = usersRated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public LocalDateTime getTimestamp() {
        return timeStamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timeStamp = timestamp;
    }
    @Override
    public String toString() {
        return "GameModel [id=" + id + ", name=" + name + ", gid=" + gid + ", year=" + year + ", ranking=" + ranking
                + ", average=" + average + ", usersRated=" + usersRated + ", url=" + url + ", thumbnail=" + thumbnail
                + ", timestamp=" + timeStamp + "]";
    }

        
    
}
