package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Player1 {
    private int xPos;
    private int yPos;

    private TETile[][] tiles;

    private TETile currTileUnder;

    public Player1(TETile[][] tiles, int x, int y) {
        this.tiles = tiles;

        this.currTileUnder = tiles[x][y];

        this.xPos = x;
        this.yPos = y;

        this.tiles[x][y] = Tileset.AVATAR;
    }

    public void movement(char keyPress) {
        char input = Character.toLowerCase(keyPress);

        if (input == 'w') {
            if (tiles[xPos][yPos + 1] == Tileset.FLOOR
                    && tiles[xPos][yPos + 1] != Tileset.WALL
                    && tiles[xPos][yPos + 1] != Tileset.NOTHING) {
                tiles[xPos][yPos] = currTileUnder;

                yPos += 1;

                currTileUnder = tiles[xPos][yPos];

                tiles[xPos][yPos] = Tileset.AVATAR;
            }
        } else if (input == 'a') {
            if (tiles[xPos - 1][yPos] == Tileset.FLOOR
                    && tiles[xPos - 1][yPos] != Tileset.WALL
                    && tiles[xPos - 1][yPos] != Tileset.NOTHING) {
                tiles[xPos][yPos] = currTileUnder;

                xPos -= 1;

                currTileUnder = tiles[xPos][yPos];

                tiles[xPos][yPos] = Tileset.AVATAR;
            }
        } else if (input == 's') {
            if (tiles[xPos][yPos - 1] == Tileset.FLOOR
                    && tiles[xPos][yPos - 1] != Tileset.WALL
                    && tiles[xPos][yPos - 1] != Tileset.NOTHING) {
                tiles[xPos][yPos] = currTileUnder;

                yPos -= 1;

                currTileUnder = tiles[xPos][yPos];

                tiles[xPos][yPos] = Tileset.AVATAR;
            }
        } else if (input == 'd') {
            if (tiles[xPos + 1][yPos] == Tileset.FLOOR
                    && tiles[xPos + 1][yPos] != Tileset.WALL
                    && tiles[xPos + 1][yPos] != Tileset.NOTHING) {
                tiles[xPos][yPos] = currTileUnder;

                xPos += 1;

                currTileUnder = tiles[xPos][yPos];

                tiles[xPos][yPos] = Tileset.AVATAR;
            }
        }
    }
}
