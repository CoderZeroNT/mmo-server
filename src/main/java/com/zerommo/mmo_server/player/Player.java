package com.zerommo.mmo_server.player;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "players")
@JsonPropertyOrder({ "id", "userId", "name", "level", "experience", "currentHp", "maxHp", "currentMp", "maxMp", "x", "y", "mapId" })
public class Player {
    @Id
    private String id;
    private String userId; 
    private String name;
    
    private int level = 1;
    private long experience = 0;
    
    private double currentHp = 100;
    private double maxHp = 100;
    private double currentMp = 50; 
    private double maxMp = 50;

    private double x = 0;
    private double y = 0;
    private String mapId = "main_town";


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; } 

    public String getName() { return name; }
    public void setName(String name) { this.name = name; } 

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public long getExperience() { return experience; }
    public void setExperience(long experience) { this.experience = experience; }

    public double getCurrentHp() { return currentHp; }
    public void setCurrentHp(double currentHp) { this.currentHp = currentHp; }

    public double getMaxHp() { return maxHp; }
    public void setMaxHp(double maxHp) { this.maxHp = maxHp; }

    public double getCurrentMp() { return currentMp; }
    public void setCurrentMp(double currentMp) { this.currentMp = currentMp; }

    public double getMaxMp() { return maxMp; }
    public void setMaxMp(double maxMp) { this.maxMp = maxMp; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public String getMapId() { return mapId; }
    public void setMapId(String mapId) { this.mapId = mapId; }
}