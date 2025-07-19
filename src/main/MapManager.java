package main;

import mapObjects.Ice;
import mapObjects.Rock;

public class MapManager {
    GamePanel gamePanel;
    public MapManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    protected void pickMap(){
        gamePanel.map = Map.EARTH;
    }

    protected void spawnMapObjects() {
        int minObjects = 1;
        int maxObjects = 4;
        int count = (int)(Math.random() * maxObjects) + minObjects; // Random between 1 and 4

        for (int i = 0; i < count; i++) {
            int x = ((int)(Math.random() * (10 - 3 + 1)) + 3) * 128; // from tile 3 to 13
            int y = ((int)(Math.random() * 9)) * 128; // Adjust 10 to your map height in tiles

            if (gamePanel.map == Map.SNOW) {
                    gamePanel.mapObjects.add(new Ice(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, 256, 256));
            } else {
                gamePanel.mapObjects.add(new Rock(gamePanel, gamePanel.soundManager, gamePanel.textureManager, gamePanel.collisionHandler, x, y, 128, 128));
            }
        }
    }



}
