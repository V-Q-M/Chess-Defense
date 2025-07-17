package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontManager {
    public static Font gameFont80;
    public static Font gameFont70;
    public static Font gameFont55;
    public static Font gameFont40;
    public static Font gameFont25;
    public static Font gameFont20;

    public static Font gameFontTiny;


    public static void loadFonts() {

        try {
            InputStream fontStream = FontManager.class.getResourceAsStream("/fonts/PressStart2P.ttf");
            if (fontStream == null) {
                throw new IOException("Font file not found in resources.");
            }

            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            gameFont80 = baseFont.deriveFont(80f);
            gameFont70 = baseFont.deriveFont(70f);
            gameFont55 = baseFont.deriveFont(55f);
            gameFont40 = baseFont.deriveFont(40f);
            gameFont25 = baseFont.deriveFont(25f);
            gameFont20 = baseFont.deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            gameFont80 = new Font("Monospaced", Font.BOLD, 80); // fallback
            gameFont70 = new Font("Monospaced", Font.BOLD, 70); // fallback
            gameFont55 = new Font("Monospaced", Font.BOLD, 55); // fallback
            gameFont40 = new Font("Monospaced", Font.BOLD, 40); // fallback
            gameFont25 = new Font("Monospaced", Font.BOLD, 25); // fallback
            gameFont20 = new Font("Monospaced", Font.BOLD, 20); // fallback
        }
    }
}
