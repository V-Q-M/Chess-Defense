package main;

public class SettingsManager {

    // For the main menu
    public static String playText = "Play";

    public static String chooseGamemodeText = "Gamemode:";
    public static String normalModeText = "Normal";
    public static String spookyModeText = "Spooky";

    public static String chooseDifficultyText = "Difficulty:";
    public static String easyText = "Easy";
    public static String mediumText = "Medium";
    public static String hardText = "Hard";
    public static String goBackText = "Go back";
    public static String loadingText = "Loading...";
    public static String shopText = "Shop";
    public static String quitText = "Quit";
    public static String settingsText = "Settings";
    public static String helpText = "Help";

    // For the Settings
    public static String volumeText = "Main volume";
    public static String musicOnText = "Music on";
    public static String musicOffText = "Music off";
    public static String languageEnglishText = "Language English";
    public static String languageGermanText = "Language German";
    public static String debugOffText = "Hitboxes off";
    public static String debugOnText = "Hitboxes on (developer mode)";

    // For the Shop
    public static String wallUpgradeText = "Wall upgrade [PURCHASED]";
    public static String wallUpgradeDescriptionText = "Purchase two turrets defending their rows with cannonballs";
    public static String kingUpgradeText = "King upgrade [PURCHASED]";
    public static String kingUpgradeDescriptionText = "Purchase two more guards for the king - allowing him to attack 3 rows at once";
    public static String queenUpgradeText = "Queen upgrade [LOCKED]";
    public static String queenUpgradeDescriptionText = "Purchase an ancient talisman for the queen - allowing her to heal on enemies";

    // General UI text
    public static String pressEscapeText = "Press ESCAPE to return to the main menu";
    public static String pressLeftText = "Press <- to view previous page";
    public static String pressRightText = "Press -> to view next page";

    // Help page
    public static String welcomeText = "Welcome to Chess Defense!";
    public static String controlsText = "Controls:";
    public static String piecesText = "Pieces Overview:";
    public static String kingNameText = "The King";
    public static String queenNameText = "The Queen";
    public static String rookNameText = "The Rook";
    public static String knightNameText = "The Knight";
    public static String bishopNameText = "The Bishop";
    public static String spookyModeDefinitionText = "Spooky mode:";
    public static String[] helpTextArray = {"The enemy Chess Pieces have started an assault on your castle and you must hold",
            "them off for as long as possible! Will you be strong enough?",
            "Use the WASD or Arrow keys to move your pieces.",
            "Press SPACE to perform an attack. Each piece has its own unique attack.",
            "Press ESC to open the pause menu.",
            "Remember: every few seconds, your piece is automatically switched.",
            "Be prepared.",
            "Having mastered artillery, the Rook has proven to be a reliable ranged option,",
            "capable of defeating his enemies from a safe distance.",
            "Having lost his sword, the Knight relies on his mighty steed to strike fear",
            "into his enemies.",
            "Having found strength in his faith, the Bishop is ready to take up arms and",
            "fight for his beliefs.",
            "Having been secretly trained in the art of sword fighting, the Queen is as mobile",
            "as she is deadly, striking down those who dare cross her path.",
            "Having served his people for many years, the King utilizes his royal guard",
            "to defeat those who dare attack him.",
            "Having defended his castle for years – the King no longer even remembers",
            "what the inside of his own castle looks like.",
            "There have been rumours of ghosts haunting the battlefield and the dead ",
            "returning to fight for their brethren... but those are just rumours. Right?"
    };

    public static boolean musicOff = false;
    public static boolean languageGerman = false;
    public static boolean debugMode = false;
    public static int volume = 10;


    public static void writeSettings(){
        String[] newSettings = new String[3];
        if (musicOff){
            newSettings[0] = "music off";
        } else {
            newSettings[0] = "music on";
        }
        if (languageGerman){
            newSettings[1] = "language german";
        } else {
            newSettings[1] = "language english";
        }
        if (debugMode){
            newSettings[2] = "debug on";
        } else {
            newSettings[2] = "debug off";
        }

        FileManager.writeLinesToTempFile(newSettings);
    }

    public static void switchToGerman(){
        languageGerman = true;
        // Main Menu
        playText = "Start";

        chooseGamemodeText = "Spielmodus:";
        normalModeText = "Normal";
        spookyModeText = "Gruselig";
        spookyModeDefinitionText = "Gruseliger Modus:";

        chooseDifficultyText = "Schwierigkeit:";
        easyText = "Leicht";
        mediumText = "Mittel";
        hardText = "Schwer";
        goBackText = "Gehe zurück";
        loadingText = "Lade...";

        shopText = "Shop";
        quitText = "Verlassen";
        settingsText = "Einstellungen";
        helpText = "Hilfe";

        // Settings Menu
        volumeText = "Gesamtlautstärke";
        musicOnText = "Musik an";
        musicOffText = "Musik aus";
        languageEnglishText = "Sprache Englisch";
        languageGermanText = "Sprache Deutsch";
        debugOffText = "Hitboxen aus";
        debugOnText = "Hitboxen an (Entwicklermodus)";

        wallUpgradeText = "Mauer upgrade [GEKAUFT]";
        wallUpgradeDescriptionText = "Kaufe zwei Tuerme, welche ihre Reihen mit Kannonkugeln verteidigen.";
        kingUpgradeText = "König upgrade [GEKAUFT]";
        kingUpgradeDescriptionText = "Kaufe zwei weitere Wachen fuer den König, wodurch er 3 Reihen angreifen kann.";
        queenUpgradeText = "Dame upgrade [GESPERRT]";
        queenUpgradeDescriptionText = "Kaufe einen antiken Talisman für die Dame, wodurch sie sich an Feinden heilen kann";

        // General UI text
        pressEscapeText = "Drücke ESC um zum Menü zu gehen";
        pressLeftText = "Drücke <- um zurück zu gehen";
        pressRightText = "Drücke -> um weiter zu gehen";

        // Help page
        welcomeText = "Willkommen bei Chess Defense!";
        controlsText = "Steuerung:";
        piecesText = "Übersicht der Figuren:";
        kingNameText = "Der König";
        queenNameText = "Die Dame";
        rookNameText = "Der Turm";
        knightNameText = "Der Springer";
        bishopNameText = "Der Läufer";

        helpTextArray[0] = "Die feindlichen Schachfiguren haben einen Angriff auf dein Schloss gestartet und";
        helpTextArray[1] = "du musst sie so lange wie möglich aufhalten! Wirst du stark genug sein?";
        helpTextArray[2] = "Verwende WASD oder die Pfeiltasten, um deine Figuren zu bewegen.";
        helpTextArray[3] = "Drücke LEERTASTE, um anzugreifen. Jede Figur hat ihre eigene Attacke.";
        helpTextArray[4] = "Drücke ESC, um das Pausenmenü zu öffnen.";
        helpTextArray[5] = "Denke daran: Alle paar Sekunden wird deine Figur automatisch gewechselt.";
        helpTextArray[6] = "Sei vorbereitet.";
        helpTextArray[7] = "Als Meister der Artillerie hat sich der Turm als zuverlässige Fernkampfeinheit";
        helpTextArray[8] = "bewährt, die seine Feinde aus sicherer Distanz besiegen kann.";
        helpTextArray[9] = "Seitdem der Springer sein Schwert verloren hat, ist er auf sein mächtiges Ross";
        helpTextArray[10] = "angewiesen, das seinen Feinden Furcht und Schrecken einjagt.";
        helpTextArray[11] = "Sein Glaube hat dem Läufer neue Stärke verliehen. Nun ist er bereit,";
        helpTextArray[12] = "für seinen Glauben zu kämpfen.";
        helpTextArray[13] = "Durch ihr geheimes Schwertkampftraining ist die Dame sowohl flink";
        helpTextArray[14] = "als auch tödlich und besiegt jeden, der sich ihr in den Weg stellt.";
        helpTextArray[15] = "Als hoch angesehener Herrscher nutzt der König seine königliche Garde,";
        helpTextArray[16] = "um jene zu besiegen, die es wagen, ihn anzugreifen.";
        helpTextArray[17] = "Nach Jahren der Verteidigung erinnert sich der König kaum noch daran,";
        helpTextArray[18] = "wie sein Schloss von innen aussieht.";
        helpTextArray[19] = "Es gibt Gerüchte über Geister, die das Schlachtfeld heimsuchen, und über Tote,";
        helpTextArray[20] = "die zurückkehren, um weiter zu kämpfen... Aber das sind nur Gerüchte, oder?";
    }
    public static void switchToEnglish(){
        languageGerman = false;
        playText = "Play";
        shopText = "Shop";
        quitText = "Quit";
        settingsText = "Settings";
        helpText = "Help";

        chooseGamemodeText = "Gamemode:";
        normalModeText = "Normal";
        spookyModeText = "Spooky";
        spookyModeDefinitionText = "Spooky mode:";

        chooseDifficultyText = "Difficulty:";
        easyText = "Easy";
        mediumText = "Medium";
        hardText = "Hard";
        goBackText = "Go back";
        loadingText = "Loading...";

        volumeText = "Main volume";
        musicOnText = "Music on";
        musicOffText = "Music off";
        languageEnglishText = "Language English";
        languageGermanText = "Language German";
        debugOffText = "Hitboxes off";
        debugOnText = "Hitboxes on (developer mode)";

        wallUpgradeText = "Wall upgrade [PURCHASED]";
        wallUpgradeDescriptionText = "Purchase two turrets defending their rows with cannonballs";
        kingUpgradeText = "King upgrade [PURCHASED]";
        kingUpgradeDescriptionText = "Purchase two more guards for the king - allowing him to attack 3 rows at once";
        queenUpgradeText = "Queen upgrade [LOCKED]";
        queenUpgradeDescriptionText = "Purchase an ancient talisman for the queen - allowing her to heal on enemies";

        // General UI text
        pressEscapeText = "Press ESCAPE to return to the main menu";
        pressLeftText = "Press <- to view previous page";
        pressRightText = "Press -> to view next page";

        // Help page
        welcomeText = "Welcome to Chess Defense!";
        controlsText = "Controls:";
        piecesText = "Pieces Overview:";
        kingNameText = "The King";
        queenNameText = "The Queen";
        rookNameText = "The Rook";
        knightNameText = "The Knight";
        bishopNameText = "The Bishop";

        helpTextArray[0] = "The enemy Chess Pieces have started an assault on your castle and you must hold";
        helpTextArray[1] = "them off for as long as possible! Will you be strong enough?";
        helpTextArray[2] = "Use the WASD or Arrow keys to move your pieces.";
        helpTextArray[3] = "Press SPACE to perform an attack. Each piece has its own unique attack.";
        helpTextArray[4] = "Press ESC to open the pause menu.";
        helpTextArray[5] = "Remember: every few seconds, your piece is automatically switched.";
        helpTextArray[6] = "Be prepared.";
        helpTextArray[7] = "Having mastered artillery, the Rook has proven to be a reliable ranged option,";
        helpTextArray[8] = "capable of defeating his enemies from a safe distance.";
        helpTextArray[9] = "Having lost his sword, the Knight relies on his mighty steed to strike fear";
        helpTextArray[10] = "into his enemies.";
        helpTextArray[11] = "Having found strength in his faith, the Bishop is ready to take up arms and";
        helpTextArray[12] = "fight for his beliefs.";
        helpTextArray[13] = "Having been secretly trained in the art of sword fighting, the Queen is as mobile";
        helpTextArray[14] = "as she is deadly, striking down those who dare cross her path.";
        helpTextArray[15] = "Having served his people for many years, the King utilizes his royal guard";
        helpTextArray[16] = "to defeat those who dare attack him.";
        helpTextArray[17] = "Having defended his castle for years – the King no longer even remembers";
        helpTextArray[18] = "what the inside of his own castle looks like.";
        helpTextArray[19] = "There have been rumours of ghosts haunting the battlefield and the dead";
        helpTextArray[20] = "returning to fight for their brethren... but those are just rumours. Right?";
    }
}
