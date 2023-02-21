package com.mozhimen.componentk.camerak.preview;

/**
 * @ClassName Direction
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/21 21:47
 * @Version 1.0
 */
public enum Direction {
    UP(0),
    LEFT(1),
    DOWN(2),
    RIGHT(3),
    AUTO(4);

    private static Direction[] sDirection = new Direction[]{UP, LEFT, DOWN, RIGHT};
    final int mNativeInt;

    private Direction(int ni) {
        this.mNativeInt = ni;
    }

    public static Direction nativeToDir(int ni) {
        return sDirection[ni];
    }

    public int getValue() {
        return this.mNativeInt;
    }

    /**
     * 获取方向
     *
     * @param degree
     * @return
     */
    public static Direction getDegree(int degree) {
        switch (degree) {
            case 0:
                return Direction.UP;
            case 90:
                return Direction.LEFT;
            case 180:
                return Direction.DOWN;
            case 270:
                return Direction.RIGHT;
            default:
                return Direction.AUTO;
        }
    }
}
