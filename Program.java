package ControlWork;


public class Program {
    String channel;
    BroadcastsTime time;
    String name;

    public Program(String channel, BroadcastsTime time, String name) {
        this.channel = channel;
        this.time = time;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + channel + ") " + time;
    }
}