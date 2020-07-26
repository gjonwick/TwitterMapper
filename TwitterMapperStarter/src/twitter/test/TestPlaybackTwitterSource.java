package twitter.test;

import org.junit.jupiter.api.Test;
import twitter.PlaybackTwitterSource;
import twitter4j.Status;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the basic functionality of the TwitterSource
 */
public class TestPlaybackTwitterSource {

    @Test
    public void testSetup() {
        PlaybackTwitterSource source = new PlaybackTwitterSource(1.0);
        TestObserver to = new TestObserver();
        // TODO: Once your TwitterSource class implements Observable, you must add the TestObserver as an observer to it here
        source.addObserver(to);
        source.setFilterTerms(set("food"));
        pause();
        assertTrue(to.getNTweets() > 0, "Expected getNTweets() to be > 0, was " + to.getNTweets());
        assertTrue(to.getNTweets() <= 10, "Expected getNTweets() to be <= 10, was " + to.getNTweets());
        int firstBunch = to.getNTweets();
        System.out.println("Now adding 'the'");
        source.setFilterTerms(set("food", "the"));
        pause();
        assertTrue(to.getNTweets() > 0, "Expected getNTweets() to be > 0, was " + to.getNTweets());
        assertTrue(to.getNTweets() > firstBunch, "Expected getNTweets() to be < firstBunch (" + firstBunch + "), was " + to.getNTweets());
        assertTrue(to.getNTweets() <= 10, "Expected getNTweets() to be <= 10, was " + to.getNTweets());
    }

    private void pause() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SafeVarargs
    private final <E> Set<E> set(E... p) {
        return new HashSet<>(Arrays.asList(p));
    }
    private static class TestObserver implements Observer {
        private int nTweets = 0;

        @Override
        public void update(Observable o, Object arg) {
            nTweets ++;
        }

        public int getNTweets() {
            return nTweets;
        }
    }
}
