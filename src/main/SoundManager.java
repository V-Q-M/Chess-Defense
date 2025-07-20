package main;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    JPanel gamePanel;
    GameMode gameMode;

    // Background music clips (persistent)
    public Clip menuTheme;
    public Clip mainTheme;
    public Clip spookyTheme;
    public Clip eerieTheme;

    // Optional: map of paths for convenience
    private final Map<String, String> soundPaths = new HashMap<>();

    public SoundManager(JPanel gamePanel, GameMode gameMode){
        this.gamePanel = gamePanel;
        this.gameMode = gameMode;
        loadSoundPaths();
    }

    private void loadSoundPaths() {
        soundPaths.put("shoot", "/sounds/shoot.wav");
        soundPaths.put("slice", "/sounds/slice1.wav");
        soundPaths.put("hit", "/sounds/hit.wav");
        soundPaths.put("castleHit", "/sounds/castleHit.wav");
        soundPaths.put("smash", "/sounds/smash.wav");
        soundPaths.put("holy", "/sounds/holyLance.wav");
        soundPaths.put("death", "/sounds/death.wav");
        soundPaths.put("swap", "/sounds/swap2.wav");
        soundPaths.put("summon", "/sounds/summon.wav");
        soundPaths.put("buttonHover", "/sounds/buttonHover.wav");
        soundPaths.put("buttonClick", "/sounds/buttonClick.wav");
        soundPaths.put("heal", "/sounds/heal.wav");
        soundPaths.put("kingSpawn", "/sounds/kingSpawn.wav");
        soundPaths.put("ghostSpawn", "/sounds/ghost.wav");
        soundPaths.put("zombieSpawn", "/sounds/zombie.wav");
    }


    private int globalVolume = 100; // Default: full volume

    public void setGlobalVolume(int volumePercent) {
        this.globalVolume = Math.max(0, Math.min(100, volumePercent));
    }


    private void applyVolume(Clip clip) {
        if (clip == null) return;
        if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) return;

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        if (globalVolume == 0) {
            gainControl.setValue(gainControl.getMinimum()); // Silence
            return;
        }

        float gain = globalVolume / 100f;
        float dB = (float)(20.0 * Math.log10(gain));
        dB = Math.max(dB, gainControl.getMinimum()); // Prevent out-of-range
        gainControl.setValue(dB);
    }

    public void playClip(String soundKey) {
        String path = soundPaths.get(soundKey);
        if (path == null) {
            System.err.println("Sound not found: " + soundKey);
            return;
        }

        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedInputStream bis = new BufferedInputStream(is);
             AudioInputStream audioIn = AudioSystem.getAudioInputStream(bis)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            applyVolume(clip);

            clip.start();

            // Auto-close when done
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

        } catch (Exception e) {
            System.err.println("Failed to play sound: " + path);
            e.printStackTrace();
        }
    }

    public void loadMusic() {
        menuTheme = loadPersistentClip("/sounds/menuTheme.wav");
        mainTheme = loadPersistentClip("/sounds/mainTheme2.wav");
        spookyTheme = loadPersistentClip("/sounds/spookyTheme.wav");
        eerieTheme = loadPersistentClip("/sounds/eerie.wav");
    }

    private Clip loadPersistentClip(String path) {
        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedInputStream bis = new BufferedInputStream(is);
             AudioInputStream audioIn = AudioSystem.getAudioInputStream(bis)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            applyVolume(clip);
            return clip;

        } catch (Exception e) {
            System.err.println("Failed to load music clip: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public void startMusic(){
        if (gameMode == GameMode.SPOOKY && spookyTheme != null) {
            stopMusic();
            spookyTheme.setFramePosition(0);
            spookyTheme.loop(Clip.LOOP_CONTINUOUSLY);
        } else if (mainTheme != null) {
            stopMusic();
            mainTheme.setFramePosition(0);
            mainTheme.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void startMenuMusic(){
        if (menuTheme != null) {
            stopMusic();
            menuTheme.setFramePosition(0);
            menuTheme.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void startEerieMenuMusic(){
        if (eerieTheme != null) {
            stopMusic();
            eerieTheme.setFramePosition(0);
            eerieTheme.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopMusic(){
        stopClip(menuTheme);
        stopClip(mainTheme);
        stopClip(spookyTheme);
        stopClip(eerieTheme);
    }

    private void stopClip(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
