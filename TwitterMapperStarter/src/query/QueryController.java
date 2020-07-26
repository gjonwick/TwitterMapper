package query;

import twitter.TwitterSource;
import ui.ContentPanel;

import java.util.*;

public class QueryController implements Iterable<Query>{

    private final List<Query> queries;
    private static QueryController instance = null;

    public static QueryController getInstance(){
        if(instance == null){
            synchronized (QueryController.class){
                if(instance == null){
                    instance = new QueryController();
                }
            }
        }
        return instance;
    }

    private QueryController(){
        queries = new ArrayList<>();
    }


    /**
     * Encapsulate Queries collection. The client is forced to use the methods provided by QueryController, rather than those provided by List
     * @return UnmodifiableList of Queries
     */
    @Override
    public Iterator<Query> iterator() {
        List<Query> queries = Collections.unmodifiableList(this.queries);
        return queries.iterator();
    }


    /**
     * return a list of all terms mentioned in all queries. The live twitter source uses this
     * to request matching tweets from the Twitter API.
     * @return HashSet of query terms
     */
    public Set<String> getQueryTerms(){
        Set<String> set = new HashSet<>();
        for (Query q : queries){
            if (q.getFilter() != null){
                set.addAll(q.getFilter().terms());
            }
        }
        return set;
    }

    public void addQuery(Query query, ContentPanel contentPanel, TwitterSource twitterSource){
        queries.add(query);
        twitterSource.setFilterTerms(getQueryTerms());
        contentPanel.addListedQueryPanel(query);
    }

    public void terminateQuery(Query query, TwitterSource twitterSource){
        query.terminate();
        queries.remove(query);
        twitterSource.setFilterTerms(getQueryTerms());

    }

}
