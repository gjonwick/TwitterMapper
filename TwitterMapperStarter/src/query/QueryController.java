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

    /**
     * return a list of all terms mentioned in all queries. The live twitter source uses this
     * to request matching tweets from the Twitter API.
     * @return HashSet of query terms
     */
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
