package com.snapchat.image;

/**
 * Created by apolonxviii on 22.09.17.
 */
public class Image {
    private String path;
    private long time;
    private byte[] file;

    public Image(byte[] file, long time) {
        this.file = file;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public byte[] getFile() {
        return file;
    }
}
