package twitter;

import twitter4j.*;
import util.Logger;

/**
 * Encapsulates the connection to Twitter
 *
 * Terms to include in the returned tweets can be set with setFilterTerms
 *
 * Implements Observable - each received tweet is signalled to all observers
 */
public class LiveTwitterSource extends TwitterSource {

    // TwitterStream object used to stream tweets in real time
    private TwitterStream twitterStream;

    // StatusListener object used to listen for incoming tweets
    private StatusListener listener;

    public LiveTwitterSource() {
        initLiveTwitter();
    }

    // Handle live twitter initialization
    private void initLiveTwitter() {
        initializeTwitterStream();
        initializeListener();
        twitterStream.addListener(listener);
    }

    // Create ConfigurationBuilder and pass in necessary credentials to authorize properly, then init TwitterStream.
    private void initializeTwitterStream(){
        // Pass the live twitter ConfigurationContext in when constructing TwitterStreamFactory.
        twitterStream = new TwitterStreamFactory(ConfigContextLiveTwitter.getConfigurationContext()).getInstance();
    }

    private void initializeListener() {
        listener = new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                // This method is called each time a tweet is delivered by the twitter API
                if (status.getPlace() != null) {
                    handleTweet(status);
                }
            }
        };
    }


    protected void sync() {
        FilterQuery filter = new FilterQuery();
        // https://stackoverflow.com/questions/21383345/using-multiple-threads-to-get-data-from-twitter-using-twitter4j
        String[] queriesArray = terms.toArray(new String[0]);
        filter.track(queriesArray);

        Logger.notifyLiveTwitterThreadInitialization(terms);

        twitterStream.filter(filter);
    }
}
