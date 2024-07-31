package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {
    private TETile[][] tiles;
    private void punchHole(int startIndexx, int startIndexy, int size) {
            for (int x = startIndexx; x < startIndexx + size; x++) {
                    for (int y = startIndexy; y < startIndexy + size; y++) {
                        if (y < tiles[0].length && x < tiles.length) {
                            tiles[x][y] = Tileset.NOTHING;
                        }
                    }
            }
    }

    public KnightWorld(int width, int height, int holeSize) {

        //Initializes array with the given width and height
        tiles = new TETile[width][height];

        //Initializes world with locked doors
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.LOCKED_DOOR;
            }
        }

       for (int y = 0; y < height;) {
            for (int x = 0; x < width;) {
                    punchHole(x, y, holeSize);
                    punchHole(x + 2 * holeSize, y + holeSize, holeSize);
                    punchHole(x + 4 * holeSize, y + 2 * holeSize, holeSize);
                    punchHole(x + 1 * holeSize, y + 3 * holeSize, holeSize);
                    punchHole(x + 3 * holeSize, y + 4 * holeSize, holeSize);
                x = x + 5 * holeSize;
            }
            y = y + 5 * holeSize;
        }
    }

    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        //40,70,4
        int width = 50;
        int height = 50;
        int holeSize = 3;

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        ter.renderFrame(knightWorld.getTiles());

    }
}
