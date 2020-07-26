package program.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import query.Query;
import query.QueryController;
import ui.Application;

import java.awt.*;

import static org.testng.Assert.*;

public class SimpleIntegrationTest {


    @DataProvider
    public Object[][] queryDataProvider(){
        return new Object[][]{
                new Object[]{"mockQueryString1", Color.red},
                new Object[]{"mockQueryString2", Color.green},
                new Object[]{"mockQueryString3", Color.yellow}
        } ;
    }

    @Test(dataProvider = "queryDataProvider")
    public void interactingWithQueriesTest(String queryString, Color queryColor){
        Application app = new Application();
        Query query = app.handleQueryCreationAndReturnQuery(queryString, queryColor);
        assertTrue(queryExists(query));
        app.terminateQuery(query);
        assertFalse(queryExists(query));
    }


    /**
     * Helper method to check if a query specified by the user is added (or exists).
     * @param addedQuery the recently added query
     */
    private boolean queryExists(Query addedQuery){
        for(Query query : QueryController.getInstance()){
            if(query.equals(addedQuery))
                return true;
        }
        return false;
    }
}
