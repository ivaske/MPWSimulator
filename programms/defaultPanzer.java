public class defaultPanzer extends Model.Panzer {
    public defaultPanzer(Model.Landschaft landschaft, Controller.AktionenButtonController controller) {
        super(landschaft,controller);
    }
    public void main() {
        map = new int[SIZE_X][SIZE_Y];

        scanMap();

    }

    private static final int SIZE_X = 10;
    private static final int SIZE_Y = 10;

    void scanMap() {
        int x = 0;
        int y = 0;
        int dir = 1;
        boolean allScanned = false;

        while (!allScanned) {
            scan(x, y, dir);
            durchfuehreSchritt(x, y, dir);
        }
    }

    void scan(int x, int y, int dir) {
        for (int i = 0; i < 4; i++) {
            int[] next = gebe(dir, x, y);
            if (next[0] < 0 || next[0] >= SIZE_X) {
                dir = dreheLiks(dir);
                continue;
            }
            if (next[1] < 0 || next[1] >= SIZE_Y) {
                dir = dreheLiks(dir);
                continue;
            }

            if (vornFrei()) {
                map[next[0]][next[1]] = 1;
            } else {
                map[next[0]][next[1]] = -1;
            }

            dir = dreheLiks(dir);
        }
    }

    void durchfuehreSchritt(int x, int y, int dir) {
        int[] nord = null;
        int[] ost = null;
        int[] sued = null;
        int[] west = null;

        for (int i = 0; i < 4; i++) {
            int[] freiDir = gebe(i, x, y);
            if (freiDir[0] < 0 || freiDir[0] >= SIZE_X) {
                continue;
            }
            if (freiDir[1] < 0 || freiDir[1] >= SIZE_Y) {
                continue;
            }
            if (map[freiDir[0]][freiDir[1]] == 1) {

                switch (i) {
                    case 0:
                        nord = new int[]{freiDir[0], freiDir[1], i};
                        break;
                    case 1:
                        ost = new int[]{freiDir[0], freiDir[1], i};
                        break;
                    case 2:
                        sued = new int[]{freiDir[0], freiDir[1], i};
                        break;
                    case 3:
                        west = new int[]{freiDir[0], freiDir[1], i};
                        break;
                }
            }
        }

        int[][] it = new int[][]{nord, ost, west, sued};

        java.util.Random random = new java.util.Random();

        int[] schritt = null;
        do {
            int n = it.length;
            int temp = random.nextInt() % n;
            int value = temp;

            switch (value) {
                case 0:
                    schritt = nord;
                    break;
                case 1:
                    schritt = ost;
                    break;
                case 2:
                    schritt = sued;
                    break;
                case 3:
                    schritt = west;
                    break;
            }

        } while (schritt == null);

        while (dir != schritt[2]) {
            dir = dreheLiks(dir);
        }
        vor();

    }

    int dreheLiks(int dir) {
        dir--;
        if (dir == -1) {
            dir = 3;
        }
        linksUm();
        return dir;
    }

    private int[] gebe(int direction, int oldX, int oldY) {
        switch (direction) {
            case 0:
                return new int[]{oldX, oldY - 1};
            case 1:
                return new int[]{oldX + 1, oldY};
            case 2:
                return new int[]{oldX, oldY + 1};
            case 3:
                return new int[]{oldX - 1, oldY};
        }
        return null;
    }


    private int[][] map;
}