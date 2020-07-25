package util;

import twitter4j.Status;

import java.util.Set;

public class Logger {

    public static void logStatusInformation(Status status){
        System.out.println(status.getUser().getName() + ": " + status.getText());
    }

    public static void notifyPlaybackThreadInitialization(Set<String> terms){
        System.out.println("Starting playback thread with " + terms);
    }

    public static void notifyLiveTwitterThreadInitialization(Set<String> terms){
        System.out.println("Syncing live Twitter stream with " + terms);
    }
}
