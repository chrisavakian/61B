package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;

public class WorldGeneration {
    //2D Array that contains the world
    private TETile[][] tiles;

    //Width of the world
    private final int WIDTH = 80;

    //Height of the world
    private final int HEIGHT = 28;

    private final int MAXROOMS = 11;

    //Seed that will randomize the world
    private long SEED;

    //Random function that is used for psuedorandom functions
    private Random RANDOM;

    //Arrays of validRoom coordinates. Contains the start and end coordinates
    private ArrayList<ArrayList<Integer>> validRooms = new ArrayList<>();

    //For use in printing out the functions
    public TETile[][] getTiles() {
        return tiles;
    }

    //When given a starting and ending xy values, creates floor values throughout the given values
    private void createRoom(int firstIndexx, int firstIndexy, int lastIndexx, int lastIndexy) {
        //Because we want to avoid infinite loops, we get the start index as the min of the two
        int startIndexx = Math.min(firstIndexx, lastIndexx);
        int startIndexy = Math.min(firstIndexy, lastIndexy);

        //Same idea for this one, but instead we want the max of the two
        int endIndexx = Math.max(firstIndexx, lastIndexx);
        int endIndexy = Math.max(firstIndexy, lastIndexy);

        //Iterates though every index of the room size
        for (int x = startIndexx; x <= endIndexx; x++) {
            for (int y = startIndexy; y <= endIndexy; y++) {
                //If statement to check for a valid position placement
                //Chance the end indicies are out of bounds
                if (isValidPos(x, y) && x < tiles.length - 1 && y < tiles[0].length - 1 && x > 0 && y > 0) {
                    //Sets the tile at that index into a floor
                    tiles[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    //@Source https://www.geeksforgeeks.org/find-all-adjacent-elements-of-given-element-in-a-2d-array-or-matrix/
    //Slightly modified to fit our needs
    private boolean isValidPos(int i, int j) {
        //checks if x and y values are neg or larger than the width and height
        if (i < 0 || j < 0 || i > WIDTH - 1 || j > HEIGHT - 1) {
            return false;
        }
        return true;
    }

    //Given and x y coordinate, will return an ArrayList of another xy coordinates
    //That will be the other corner of the room
    //Rooms will be no larger than a 5x5 at most
    //Due to two different values being chosen, there is a chance at squares or rectangles
    private ArrayList<Integer> otherxyCoords(int x, int y) {
        //Creates an ArrayList which will store the two terminating values
        ArrayList<Integer> returnList = new ArrayList<>();

        //Adds to the list by calling a random number between 3 and 5
        //Note the 6 is exclusive but the 3 is
        returnList.add(x + RANDOM.nextInt(3, 7));
        returnList.add(y + RANDOM.nextInt(3, 7));

        //returns the list
        return returnList;
    }

    //Creates hallways between the two closest rooms
    //Chooses a random point in both rooms
    //Creates a path between them
    private void createHallways() {
        ArrayList<Integer> previousRoom = validRooms.get(0);
        for (ArrayList<Integer> room: validRooms) {

            //For room one, retrieves the two corner xy values
            int xRoom = room.get(0);
            int yRoom = room.get(1);
            int xRoomTer = room.get(2);
            int yRoomter = room.get(3);

            //For room 2, also retrieves the two corner xy values
            int xPrev = previousRoom.get(0);
            int yPrev = previousRoom.get(1);
            int xPrevTer = previousRoom.get(2);
            int yPrevTer = previousRoom.get(3);

            //Finds a random number between the two x and y values for room 1
            int x = RANDOM.nextInt(xRoom, xRoomTer);
            int y = RANDOM.nextInt(yRoom, yRoomter);

            //Finds a random number between the two x and y values for room 2
            int x1 = RANDOM.nextInt(xPrev, xPrevTer);
            int y1 = RANDOM.nextInt(yPrev, yPrevTer);

            //Creates two straight line 'rooms' (actually supposed to be hallways)
            //This will connect two rooms together
            createRoom(x, y, x1, y);
            createRoom(x1, y, x1, y1);

            previousRoom = room;
        }
    }

    //Initializes world with nothing
    //Iterates through every single index and sets it equal to nothing
    private void createNothingWorld() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    //This function is called before creating a room to sus out any rooms that may overlap
    private ArrayList<Integer> overlaps(int x, int y) {
        int sum = 0;

        for (ArrayList<Integer> rooms: validRooms) {
            int distx = Math.abs(x - rooms.get(0));
            int disty = Math.abs(y - rooms.get(1));

            sum += disty;
            sum += distx;
        }
        ArrayList<Integer> returnValue = new ArrayList<>();

        if (validRooms.size() == 0) {
            sum = 0;
        } else {
            sum = sum / (validRooms.size() * 2);
        }



        returnValue.add(x + sum);
        returnValue.add(y + sum);

        return returnValue;
    }

    //Pseudorandomly chooses some flaggedRooms as rooms that will be created
    //Stores those values into another arrayList: validRooms
    //That List contains the coordinates of every room that is implemented
    public void findAndCreateValidRooms() {
        int numOfRooms = 8 + RANDOM.nextInt(MAXROOMS);

        while (numOfRooms > 0) {
            ArrayList<Integer> roomCreate = new ArrayList<>();

            int xValue = RANDOM.nextInt(WIDTH) + 1;
            int yValue = RANDOM.nextInt(HEIGHT) + 1;
            ArrayList<Integer> xyTer = otherxyCoords(xValue, yValue);
            int xValueTer = xyTer.get(0) - 1;
            int yValueTer = xyTer.get(1) - 1;

            if (isValidPos(xValue, yValue) && isValidPos(xValueTer, yValueTer)) {
                roomCreate.add(xValue);
                roomCreate.add(yValue);
                roomCreate.add(xValueTer);
                roomCreate.add(yValueTer);

                validRooms.add(roomCreate);

                createRoom(xValue, yValue, xValueTer, yValueTer);

                numOfRooms--;
            }
        }
    }

    //This creates walls around every floor
    //@Source https://www.geeksforgeeks.org/find-all-adjacent-elements-of-given-element-in-a-2d-array-or-matrix/
    //Slightly modified to fit our needs
    private void createWalls() {
        //Covers every room with walls
        //Some code taken from internet and modified slightly
        //This checks if the current block it's on is a floor
        //If true then it checks all spaces around, including diagonals
        //And places a wall if necessary
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (isValidPos(x - 1, y - 1) && tiles[x][y] == Tileset.FLOOR && tiles[x - 1][y - 1] != Tileset.FLOOR) {
                    tiles[x - 1][y - 1] = Tileset.WALL;
                }
                if (isValidPos(x - 1, y) && tiles[x][y] == Tileset.FLOOR && tiles[x - 1][y] != Tileset.FLOOR) {
                    tiles[x - 1][y] = Tileset.WALL;
                }
                if (isValidPos(x - 1, y + 1) && tiles[x][y] == Tileset.FLOOR && tiles[x - 1][y + 1] != Tileset.FLOOR) {
                    tiles[x - 1][y + 1] = Tileset.WALL;
                }
                if (isValidPos(x, y - 1) && tiles[x][y] == Tileset.FLOOR && tiles[x][y - 1] != Tileset.FLOOR) {
                    tiles[x][y - 1] = Tileset.WALL;
                }
                if (isValidPos(x, y + 1) && tiles[x][y] == Tileset.FLOOR && tiles[x][y + 1] != Tileset.FLOOR) {
                    tiles[x][y + 1] = Tileset.WALL;
                }
                if (isValidPos(x + 1, y - 1) && tiles[x][y] == Tileset.FLOOR && tiles[x + 1][y - 1] != Tileset.FLOOR) {
                    tiles[x + 1][y - 1] = Tileset.WALL;
                }
                if (isValidPos(x + 1, y) && tiles[x][y] == Tileset.FLOOR && tiles[x + 1][y] != Tileset.FLOOR) {
                    tiles[x + 1][y] = Tileset.WALL;
                }
                if (isValidPos(x + 1, y + 1) && tiles[x][y] == Tileset.FLOOR && tiles[x + 1][y + 1] != Tileset.FLOOR) {
                    tiles[x + 1][y + 1] = Tileset.WALL;
                }
            }
        }
    }

    WorldGeneration(String seed) {
        //Declares SEED integer with the user input
        this.SEED = Long.parseLong(seed);

        //Creates RANDOM function with the given user inputted seed
        RANDOM = new Random(SEED);

        //Initializes array with the given width and height
        tiles = new TETile[WIDTH][HEIGHT];

        //Initializes the world to be filled with nothing tiles
        this.createNothingWorld();

        //This will check for non overlapping rooms and will create those into the 2D array
        this.findAndCreateValidRooms();

        //This will connect the rooms together
        //Function works, but only connect the bottom left corners together
        this.createHallways();

        //This will place walls for every room
        this.createWalls();
    }

    public static void main(String[] args) {
    }

}
