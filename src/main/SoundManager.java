package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundManager {
    JPanel gamePanel;
    GameMode gameMode;

    public Clip shootClip;
    public Clip sliceClip;
    public Clip hitClip;
    public Clip castleHitClip;
    public Clip smashClip;
    public Clip holyClip;
    public Clip deathClip;
    public Clip swapClip;
    public Clip buttonHoverClip;
    public Clip buttonClickClip;
    public Clip summonClip;
    public Clip healClip;
    public Clip kingSpawnClip;
    public Clip ghostSpawnClip;
    public Clip zombieSpawnClip;


    public Clip menuTheme;
    public Clip mainTheme;
    public Clip spookyTheme;
    public Clip eerieTheme;

    public SoundManager(JPanel gamePanel, GameMode gameMode){
        this.gamePanel = gamePanel;
        this.gameMode = gameMode;
    }

    public void playClip(Clip clip) {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();  // Stop current sound if needed
        clip.setFramePosition(0);           // Rewind to the beginning
        clip.start();
    }

    public void startMusic(){
       if (mainTheme == null) return;
       if (menuTheme.isRunning()) menuTheme.stop();
       if (mainTheme.isRunning()) mainTheme.stop();
       if (spookyTheme.isRunning()) spookyTheme.stop();
        if (eerieTheme.isRunning()) eerieTheme.stop();
       if (gameMode == GameMode.SPOOKY){
           spookyTheme.setFramePosition(0);
           spookyTheme.loop(Clip.LOOP_CONTINUOUSLY);
           spookyTheme.start();
       } else {
           mainTheme.setFramePosition(0);
           mainTheme.loop(Clip.LOOP_CONTINUOUSLY);
           mainTheme.start();
       }
    }

    public void startMenuMusic(){
        if(menuTheme == null) return;
        if (menuTheme.isRunning()) menuTheme.stop();
        if (mainTheme.isRunning()) mainTheme.stop();
        if (spookyTheme.isRunning()) spookyTheme.stop();
        if (eerieTheme.isRunning()) eerieTheme.stop();
        menuTheme.setFramePosition(0);
        menuTheme.loop(Clip.LOOP_CONTINUOUSLY);
        menuTheme.start();
    }

    public void startEerieMenuMusic(){
        if (menuTheme == null) return;
        if (menuTheme.isRunning()) menuTheme.stop();
        if (mainTheme.isRunning()) mainTheme.stop();
        if (spookyTheme.isRunning()) spookyTheme.stop();
        if (eerieTheme.isRunning()) eerieTheme.stop();
        eerieTheme.setFramePosition(0);
        eerieTheme.loop(Clip.LOOP_CONTINUOUSLY);
        eerieTheme.start();
    }
    public void stopMusic(){
        if (mainTheme == null) return;
        if (menuTheme.isRunning()) menuTheme.stop();
        if (mainTheme.isRunning()) mainTheme.stop();
        if (spookyTheme.isRunning()) spookyTheme.stop();
        if (eerieTheme.isRunning()) eerieTheme.stop();
    }

    public void loadSounds() {
        shootClip = loadClip("/sounds/shoot.wav");
        sliceClip = loadClip("/sounds/slice1.wav");
        hitClip   = loadClip("/sounds/hit.wav");
        castleHitClip = loadClip("/sounds/castleHit.wav");
        smashClip = loadClip("/sounds/smash.wav");
        holyClip = loadClip("/sounds/holyLance.wav");
        deathClip = loadClip("/sounds/death.wav");
        swapClip  = loadClip("/sounds/swap2.wav");
        summonClip = loadClip("/sounds/summon.wav");
        menuTheme = loadClip("/sounds/menuTheme.wav");
        mainTheme = loadClip("/sounds/mainTheme2.wav");
        spookyTheme = loadClip("/sounds/spookyTheme.wav");
        eerieTheme = loadClip("/sounds/eerie.wav");
        buttonHoverClip = loadClip("/sounds/buttonHover.wav");
        buttonClickClip = loadClip("/sounds/buttonClick.wav");
        healClip = loadClip("/sounds/heal.wav");
        kingSpawnClip = loadClip("/sounds/kingSpawn.wav");
        ghostSpawnClip = loadClip("/sounds/ghost.wav");
        zombieSpawnClip = loadClip("/sounds/zombie.wav");
    }

    private Clip loadClip(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Audio resource not found: " + path);
            }
            try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(is))) {
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                return clip;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
