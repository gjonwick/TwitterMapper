package twitter.test;

import org.junit.jupiter.api.Test;
import twitter.LiveTwitterSource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.Assert.assertFalse;

public class LiveTwitterSourceTest {
    @Test
    public void testSetFilterTerms() {
        LiveTwitterSource source = new LiveTwitterSource();
        assertTrue(source.getFilterTerms().isEmpty());
        source.setFilterTerms(set("food"));
        assertFalse(source.getFilterTerms().isEmpty());
    }


    @SafeVarargs
    private final <E> Set<E> set(E... p) {
        Set<E> ans = new HashSet<>();
        Collections.addAll(ans, p);
        return ans;
    }


}