package query;

import twitter.TwitterSource;
import ui.ContentPanel;

import java.util.*;

public class QueryController implements Iterable<Query>{
    private List<Query> queries;
    private static QueryController instance;

    public static QueryController getInstance(){
        if(instance == null){
            instance = new QueryController();
        }
        return instance;
    }

    private QueryController(){
        queries = new ArrayList<>();
    }

    @Override
    public Iterator<Query> iterator() {
        return queries.iterator();
    }

    private Set<String> getQueryTerms(){
        Set<String> set = new HashSet<>();
        for (Query q : queries){
            set.addAll(q.getFilter().terms());
        }
        return set;
    }

    public void addQuery(Query query, ContentPanel contentPanel, TwitterSource twitterSource){
        queries.add(query);
        twitterSource.setFilterTerms(getQueryTerms());
        contentPanel.addListedQueryPanel(query);
    }

    public void terminateQuery(Query query, TwitterSource twitterSource){
        queries.remove(query);
        twitterSource.setFilterTerms(getQueryTerms());
        query.terminate();
    }
}
