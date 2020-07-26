package twitter;

import twitter4j.Status;
import util.ImageCache;
import util.Logger;

import java.util.*;


public abstract class TwitterSource extends Observable {

    // Flag to check if logging is enabled
    protected boolean doLogging;

    // The set of terms to look for in the stream of tweets
    protected Set<String> terms;

    protected TwitterSource() {
        doLogging = true;
        terms = new HashSet<>();
    }

    // Called each time a new set of filter terms has been established
    abstract protected void sync();

    protected void log(Status status) {
        if (doLogging) {
            Logger.logStatusInformation(status);
        }
        ImageCache.getInstance().loadImage(status.getUser().getProfileImageURL());
    }

    public void setFilterTerms(Collection<String> newTerms) {
        terms.clear();
        terms.addAll(newTerms);
        sync();
    }

    public List<String> getFilterTerms() {
        return new ArrayList<>(terms);
    }

    // This method is called each time a tweet is delivered to the application.
    // TODO: Each active query should be informed about each incoming tweet so that
    //       it can determine whether the tweet should be displayed
    protected void handleTweet(Status status) {
        setChanged();
        log(status);
        notifyObservers(status);
    }

}
