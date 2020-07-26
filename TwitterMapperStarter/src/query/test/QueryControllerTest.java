package query.test;

import filters.Filter;
import org.mockito.Mock;
import org.testng.annotations.*;
import query.Query;
import query.QueryController;
import twitter.TwitterSource;
import twitter4j.Status;
import ui.ContentPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class QueryControllerTest {

    private final QueryController queryController = QueryController.getInstance();

    @Mock
    private TwitterSource mockedTwitterSource;

    @Mock
    private Query mockedQuery;

    @Mock
    private ContentPanel mockedContentPanel;

    private final String mockString1 = "mockValue1";
    private final String mockString2 = "mockValue2";

    Filter testFilter = new Filter() {
        @Override
        public boolean matches(Status s) {
            return true;
        }

        @Override
        public List<String> terms() {
            List<String> testList = new ArrayList<>();
            testList.add(mockString1);
            testList.add(mockString2);
            return testList;
        }
    };

    @BeforeMethod
    public void beforeMethod() {
        mockedTwitterSource = mock(TwitterSource.class);
        mockedQuery = mock(Query.class);
        mockedContentPanel = mock(ContentPanel.class);

    }



    @Test
    public void testGetQueryTerms(){
        queryController.addQuery(mockedQuery, mockedContentPanel, mockedTwitterSource);
        when(mockedQuery.getFilter()).thenReturn(testFilter);
        Set<String> set = queryController.getQueryTerms();
        assertNotNull(set);
        assertFalse(set.isEmpty());
        assertTrue(set.contains(mockString1));
        assertTrue(set.contains(mockString2));
    }


    @Test
    public void testAddQuery() {
        queryController.addQuery(mockedQuery, mockedContentPanel, mockedTwitterSource);

        assertTrue(containsQuery(queryController, mockedQuery));
    }

    @Test
    public void testTerminateQuery() {
        queryController.addQuery(mockedQuery, mockedContentPanel, mockedTwitterSource);
        assertTrue(containsQuery(queryController, mockedQuery));
        queryController.terminateQuery(mockedQuery, mockedTwitterSource);
        assertFalse(containsQuery(queryController, mockedQuery));
    }

    /**
     * Helper method to check if a queryList contains a query
     */
    private boolean containsQuery(QueryController queryController, Query mockedQuery){
        boolean containsQuery = false;
        for(Query q : queryController){
            if (q.equals(mockedQuery)) {
                containsQuery = true;
                break;
            }
        }
        return containsQuery;
    }
}