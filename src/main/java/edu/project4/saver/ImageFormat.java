package edu.project4.saver;

public enum ImageFormat {
    JPEG("jpg"),
    BMP("bmp"),
    PNG("png");

    private final String format;

    ImageFormat(String format) {
        this.format = format;
    }

    @Override public String toString() {
        return format;
    }
}
