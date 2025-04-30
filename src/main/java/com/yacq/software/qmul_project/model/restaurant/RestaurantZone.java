package com.yacq.software.qmul_project.model.restaurant;

public enum RestaurantZone {
    // Format: (minX, minY, maxX, maxY)
    WINDOW_LEFT(0, 0, 3, 16),       // Left wall (x=0-3, full height)
    WINDOW_ENTRANCE(3, 13, 16, 16), // Entrance wall (bottom 3 rows)
    BAR(10, 0, 16, 5),              // Top-right corner
    PRIVATE(8, 5, 14, 10),          // Center-right booths
    EVENT(10, 8, 16, 13),           // Right-middle event space
    CENTER(3, 3, 10, 13);          // Main dining area

    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;

    // Constructor for enum values
    RestaurantZone(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    // Getters
    public int getMinX() { return minX; }
    public int getMinY() { return minY; }
    public int getMaxX() { return maxX; }
    public int getMaxY() { return maxY; }

    // Utility method to check if coordinates are in zone
    public boolean contains(int x, int y) {
        return x >= minX && x < maxX &&
                y >= minY && y < maxY;
    }
}
