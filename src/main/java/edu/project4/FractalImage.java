package edu.project4;

public class FractalImage {
    private final Pixel[][] pixels;
    private final int width;
    private final int height;

    private FractalImage(Pixel[][] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public static FractalImage create(int width, int height) {
        return new FractalImage(fillPixels(width, height), width, height);
    }

    private static Pixel[][] fillPixels(int width, int height) {
        Pixel[][] pixels = new Pixel[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = new Pixel();
            }
        }
        return pixels;
    }

    private boolean contains(int x, int y) {
        return (pixels.length > x && pixels[0].length > y);
    }

    public Pixel getPixel(int x, int y) {
        if (contains(x, y)) {
            return pixels[x][y];
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
