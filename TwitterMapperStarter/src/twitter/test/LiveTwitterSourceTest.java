package twitter.test;

import org.junit.jupiter.api.Test;
import twitter.LiveTwitterSource;
import twitter.PlaybackTwitterSource;
import twitter4j.Status;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.Assert.*;

public class LiveTwitterSourceTest {
    @Test
    public void testSetFilterTerms() {
        LiveTwitterSource source = new LiveTwitterSource();
        assertTrue(source.getFilterTerms().isEmpty());
        source.setFilterTerms(set("food"));
        assertFalse(source.getFilterTerms().isEmpty());
    }


    private <E> Set<E> set(E ... p) {
        Set<E> ans = new HashSet<>();
        for (E a : p) {
            ans.add(a);
        }
        return ans;
    }


}