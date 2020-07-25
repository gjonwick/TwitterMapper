package twitter;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ConfigContextLiveTwitter {

    private static final String API_KEY;
    private static final String API_SECRET_KEY;
    private static final String ACCESS_TOKEN;
    private static final String ACCESS_TOKEN_SECRET;

    static{
        API_KEY = "df7uJ6wNmrE0WHZpbc1BQuXu4";
        API_SECRET_KEY = "1NzNOGmdv1uWHOB7eWqFFkpbTaLTBoUSNNNDuJBCVRtq0KE0DJ";
        ACCESS_TOKEN = "1281247078430191625-fWFCPXrAfU5MKC6ru1g4krLuFi1AjV";
        ACCESS_TOKEN_SECRET = "bvudeOcDbzTsB8uBjBTw0WNoCtPjHJw508sSP3guB2WfN";
    }

    public static Configuration getConfigurationContext(){
        return new ConfigurationBuilder()
                .setOAuthConsumerKey(API_KEY)
                .setOAuthConsumerSecret(API_SECRET_KEY)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET)
                .build();
    }
}
