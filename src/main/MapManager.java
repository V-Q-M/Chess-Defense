package main;

import mapObjects.Ice;
import mapObjects.Rock;

public class MapManager {
    GamePanel gamePanel;
    public MapManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    protected void pickMap(){
        gamePanel.map = Map.SNOW;
    }

    protected void spawnMapObjects(){
        if (gamePanel.map == Map.SNOW){
            gamePanel.mapObjects.add(new Ice(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, 6*128, 6*128, 256, 256));
        } else {
            gamePanel.mapObjects.add(new Rock(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, 6*128, 6*128, 128, 128));
        }

    }



}
