package net.savagedev.ra.common;


import java.io.Serializable;
import java.util.List;

public class Announcement implements Serializable {
    private List<String> message;
    private Sound sound;

    public Announcement(List<String> message, Sound sound) {
        this.message = message;
        this.sound = sound;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public List<String> getMessage() {
        return this.message;
    }

    public boolean hasSound() {
        return this.sound != null;
    }

    public Sound getSound() {
        return this.sound;
    }
}
