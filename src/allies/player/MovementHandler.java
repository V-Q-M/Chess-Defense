package allies.player;

import main.GamePanel;

public class MovementHandler {
    GamePanel gamePanel;
    Player player;

    public MovementHandler(GamePanel gamePanel, Player player){

        this.gamePanel = gamePanel;
        this.player = player;
    }
}
