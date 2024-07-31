package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
//Exit code 2 means quit game

public class Engine {
    /* Feel free to change the width and height. */
    //Originally 80, 30
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    TERenderer ter = new TERenderer();
    private Random RANDOM;

    private final int GAMEFONT = 15;
    private final int PAUSETIMER = 200;
    private final int TITLEFONT = 30;

    Player1 user;
    File saveFile;
    FileWriter fWriter;

    TETile[][] tiles;

    public Engine() {
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */

    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);

        drawTitleScreen();

        while (!StdDraw.hasNextKeyTyped()) {
            //Do nothing
        }
        char input = Character.toLowerCase(StdDraw.nextKeyTyped());

        switch (input) {
            case 'n':
                StdDraw.clear();

                try {
                    saveFile = new File(System.getProperty("user.dir") + "\\saveFile.txt");
                    fWriter = new FileWriter(saveFile, false);
                } catch (IOException e) {
                    System.out.println("An error has occured");
                }

                this.saveInputToFile('n');

                this.createNewGame();

                this.createPlayer(tiles);

                ter.renderFrame(tiles);

                this.gameLoop();

                break;
            case 'l':
                loadGame();
                break;
            case 'q':
                StdDraw.clear();
                System.exit(2);
                break;
            default:
                this.interactWithKeyboard();
        }
    }

    private void createSaveFile() {
        if (saveFile != null && saveFile.length() != 0) {
            try {
                fWriter = new FileWriter(saveFile, true);
            } catch (IOException e) {
                System.out.println("An error has occured");
            }

        } else {
            try {
                saveFile = new File(System.getProperty("user.dir") + "\\saveFile.txt");
                fWriter = new FileWriter(saveFile, false);
            } catch (IOException e) {
                System.out.println("An error has occured");
            }
        }
    }

    private void createPlayer(TETile[][] tile) {
        int x;
        int y;

        do {
            x = RANDOM.nextInt(WIDTH - 1);
            y = RANDOM.nextInt(HEIGHT - 1 - 1);
        } while (tile[x][y] != Tileset.FLOOR);

        this.user = new Player1(tile, x, y);
    }

    private void loadGame() {
        saveFile = new File(System.getProperty("user.dir") + "\\saveFile.txt");

        if (saveFile.length() == 0){
            this.interactWithKeyboard();
        }

        try {
            fWriter = new FileWriter(saveFile, true);
        } catch (IOException e) {
            System.out.println("An Error Occured");
        }

        In in = new In(saveFile.getPath());

        String[] arr = in.readString().split("");

        StringBuilder temp = new StringBuilder();
        int i = 1;
        while (!arr[i].equals("s")) {
            temp.append(arr[i]);
            i++;
        }
        i++;

        long seed = Long.parseLong(temp.toString());

        RANDOM = new Random(seed);
        WorldGeneration bruh = new WorldGeneration(temp.toString());
        tiles = bruh.getTiles();

        createPlayer(tiles);

        Font gameFont = new Font("Monaco", Font.PLAIN, GAMEFONT);
        StdDraw.setFont(gameFont);

        while (i < arr.length) {
            user.movement(arr[i].toCharArray()[0]);
            ter.renderFrame(tiles);

            StdDraw.pause(PAUSETIMER);
            i++;
        }

        ter.renderFrame(tiles);

        gameLoop();
    }

    private String retrieveSeed() {
        StringBuilder seed = new StringBuilder();

        while (!StdDraw.hasNextKeyTyped()) {
            //Do nothing
        }
        while (StdDraw.hasNextKeyTyped()) {
            char number = StdDraw.nextKeyTyped();

            if (!Character.isDigit(number) && Character.toLowerCase(number) != 's') {
                while (!StdDraw.hasNextKeyTyped()) {
                    //Do nothing
                }
            } else {
                if (Character.toLowerCase(number) == 's') {

                    try {
                        fWriter.write('s');
                        break;
                    } catch (IOException e) {
                        System.out.println("An Error Occured");
                    }

                }

                seed.append(number);

                try {
                    fWriter.write(number);
                } catch (IOException e) {
                    System.out.println("An Error Occured");
                }

                StdDraw.text(WIDTH / 2, HEIGHT - 5, "Please Enter in Seed and press s when finished");
                StdDraw.text(WIDTH / 2, HEIGHT / 2, seed.toString());
                StdDraw.show();

                while (!StdDraw.hasNextKeyTyped()) {
                    //Do nothing
                }
                StdDraw.clear(Color.BLACK);
            }

        }
        return seed.toString();
    }

    private void gameLoop() {
        boolean ourCodeIsIndustrialStrength = true;

        while (ourCodeIsIndustrialStrength) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();

            char userInput = ' ';

            if (StdDraw.hasNextKeyTyped()) {
                userInput = StdDraw.nextKeyTyped();
            }

            this.gameLoopHelper(userInput);

            ter.renderFrame(tiles);

            this.drawUI(x, y);

            StdDraw.show();

            StdDraw.pause(GAMEFONT + 1);
        }
    }

    private void gameLoopHelper(char input) {
        this.checkForQuit(input);

        if (input != ' ') {
            this.saveInputToFile(input);

            user.movement(input);
        }
    }

    private void createNewGame() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        Font titleFont = new Font("Monaco", Font.BOLD, TITLEFONT);
        StdDraw.setFont(titleFont);
        StdDraw.text(WIDTH / 2, HEIGHT - 5, "Please Enter in Seed and press s when finished");
        StdDraw.show();

        String seed = retrieveSeed();

        RANDOM = new Random(Long.parseLong(seed.toString()));
        WorldGeneration bruh = new WorldGeneration(seed.toString());
        tiles = bruh.getTiles();

        Font gameFont = new Font("Monaco", Font.PLAIN, GAMEFONT);
        StdDraw.setFont(gameFont);
    }

    private void saveInputToFile(char input) {
        try {
            fWriter.write(input);
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    private void checkForQuit(char input) {
        if (input == ':') {
            while (!StdDraw.hasNextKeyTyped()) {
                //Do nothing
            }
            char userInput = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (userInput == 'q') {
                try {
                    fWriter.close();
                    System.exit(2);
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            } else if (userInput == 'p') {
                try {
                    fWriter.close();
                    interactWithKeyboard();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

    private void drawUI(double x, double y) {
        StdDraw.setPenColor(Color.WHITE);

        if (x > WIDTH - 1) {
            x = WIDTH - 1;
        } else if (y > HEIGHT - 1 - 1 - 1) {
            y = HEIGHT - 1 - 1 - 1;
        }

        TETile blockUnder = tiles[(int) x][(int) y];

        StdDraw.text(1 + 1, HEIGHT - 1, blockUnder.description());
    }

    public void drawTitleScreen() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        Font titleFont = new Font("Monaco", Font.BOLD, TITLEFONT);
        StdDraw.setFont(titleFont);
        StdDraw.text(WIDTH / 2, HEIGHT - 5, "CS61B Game");

        StdDraw.show();

        StdDraw.pause(PAUSETIMER / 2);

        Font optionsFont = new Font("Monaco", Font.PLAIN, TITLEFONT - 1 - 1 - 1 - 1 - 1);
        StdDraw.setFont(optionsFont);
        StdDraw.text(WIDTH / 2, HEIGHT - GAMEFONT + 1 + 1 + 1 + 1 + 1, "N: New Game");

        StdDraw.show();

        StdDraw.pause(PAUSETIMER / 2);

        StdDraw.text(WIDTH / 2, HEIGHT - GAMEFONT, "L: Load Game");

        StdDraw.show();

        StdDraw.pause(PAUSETIMER / 2);

        StdDraw.text(WIDTH / 2, HEIGHT - GAMEFONT - 1 - 1 - 1 - 1 - 1, "Q: Quit");

        StdDraw.show();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        //coverts the string input into an array of chars
        char[] rawInputs = input.toCharArray();

        //This iterates through the array of chars and coverts every character into lowercase
        //If applicable
        for (int i = 0; i < rawInputs.length; i++) {
            rawInputs[i] = Character.toLowerCase(rawInputs[i]);
        }

        //This means load game from the string
        if (rawInputs[0] == 'l') {
            this.inputStringLoadHelper(rawInputs);
        } else if (rawInputs[0] == 'n') {
            this.inputStringNewHelper(rawInputs);
        }
        return tiles;
    }

    private void inputStringNewHelper(char[] rawInputs) {
        this.createSaveFile();

        //Add n to file
        try {
            fWriter.write('n');
        } catch (IOException e) {
            System.out.println("AN Error Occured");
        }

        StringBuilder seed = new StringBuilder();
        int counter = 1;
        while (counter < rawInputs.length - 1 && rawInputs[counter] != 's') {
            if (Character.isDigit(rawInputs[counter])) {
                seed.append(rawInputs[counter]);

                //Add input to file
                try {
                    fWriter.write(rawInputs[counter]);
                } catch (IOException e) {
                    System.out.println("AN Error Occured");
                }

                counter++;
            }
        }

        //Add s to file
        try {
            fWriter.write('s');
        } catch (IOException e) {
            System.out.println("AN Error Occured");
        }

        counter++;
        RANDOM = new Random(Long.parseLong(seed.toString()));
        WorldGeneration bruh = new WorldGeneration(seed.toString());
        tiles = bruh.getTiles();
        createPlayer(tiles);
        for (int i = counter; i < rawInputs.length; i++) {
            if (i == rawInputs.length - 2
                    && Character.toLowerCase(rawInputs[i]) == ':'
                    && Character.toLowerCase(rawInputs[i + 1]) == 'q') {
                try {
                    fWriter.close();
                    return;
                } catch (IOException e) {
                    System.out.println("AN Error Occured");
                }
            } else {
                try {
                    fWriter.write(Character.toLowerCase(rawInputs[i]));
                    user.movement(rawInputs[i]);
                } catch (IOException e) {
                    System.out.println("AN Error Occured");
                }
            }
        }
    }

    private void inputStringLoadHelper(char[] rawInputs) {
        saveFile = new File(System.getProperty("user.dir") + "\\saveFile.txt");

        try {
            fWriter = new FileWriter(saveFile, true);
        } catch (IOException e) {
            System.out.println("An Error Occured");
        }

        //Create the in object to read the inputs
        In in = new In(saveFile.getPath());

        //Store the read inputs into a char array
        char[] savedInputs = in.readLine().toCharArray();

        //Ensures that all the letters are in lower case if applicable
        for (int i = 0; i < savedInputs.length; i++) {
            savedInputs[i] = Character.toLowerCase(savedInputs[i]);
        }

        StringBuilder tempSeed = new StringBuilder();

        //index counter variable
        int i = 1;
        //Runs while there char is not an s
        while (savedInputs[i] != 's') {
            //Checks if the char is a digit
            if (Character.isDigit(savedInputs[i])) {
                tempSeed.append(savedInputs[i]);
                i++;
            }
        }
        i++;

        //Uses the seed to create the Random Function
        RANDOM = new Random(Long.parseLong(tempSeed.toString()));

        //Uses the seed to create the world generation
        WorldGeneration bruh = new WorldGeneration(tempSeed.toString());

        //Set tiles equal to the world's tiles
        tiles = bruh.getTiles();

        //Create the player
        createPlayer(tiles);

        //Runs the movement for the rest of the time
        //From the saved inputs
        while (i < savedInputs.length) {
            user.movement(savedInputs[i]);
            i++;
        }

        //Now we need to run the rest of the inputs
        //Skip the first input bc the first input is an 'l'
        int j = 1;
        while (j < rawInputs.length) {

            //Checks if quit is called
            //this seems suseptiple to an index out of bounds error
            if (j == rawInputs.length - 2 && rawInputs[j] == ':' && rawInputs[j + 1] == 'q') {
                try {
                    fWriter.close();
                    return;
                } catch (IOException e) {
                    System.out.println("Error");
                }
            }

            try {
                user.movement(rawInputs[j]);
                fWriter.write(rawInputs[j]);
            } catch (IOException e) {
                System.out.println("Error");
            }

            j++;
        }
    }

}
