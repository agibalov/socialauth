package me.loki2302.socialauth;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterAuthenticationService {
    private final String apiKey; 
    private final String apiSecret;
    private final String callbackUri;
    
    public TwitterAuthenticationService(
            String apiKey, 
            String apiSecret, 
            String callbackUri) {
        
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;        
        this.callbackUri = callbackUri; 
    }
    
    public String getAuthenticationUri() {
        Twitter twitter = makeTwitter();
        
        RequestToken requestToken;
        try {
            requestToken = twitter.getOAuthRequestToken(callbackUri);
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
        return requestToken.getAuthenticationURL();
    }
    
    public OAuthToken getAccessToken(String oauthToken, String oauthVerifier) {        
        Twitter twitter = makeTwitter();
        
        RequestToken requestToken = new RequestToken(oauthToken, oauthVerifier);        
        AccessToken accessToken;
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
        
        OAuthToken oauthAccessToken = new OAuthToken();
        oauthAccessToken.accessToken = accessToken.getToken();
        oauthAccessToken.accessTokenSecret = accessToken.getTokenSecret();
                
        return oauthAccessToken;
    }
    
    public TwitterUserInfo getUserInfo(OAuthToken oauthToken) {
        Twitter twitter = makeTwitter();
        
        AccessToken accessToken = new AccessToken(
                oauthToken.accessToken, 
                oauthToken.accessTokenSecret);
        
        twitter.setOAuthAccessToken(accessToken);
        User user;
        try {
            user = twitter.verifyCredentials();
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }        
        
        TwitterUserInfo twitterUserInfo = new TwitterUserInfo();
        twitterUserInfo.Id = new Long(user.getId()).toString();
        twitterUserInfo.Name = user.getName();
        twitterUserInfo.ScreenName = user.getScreenName();
        twitterUserInfo.Url = user.getURL();
        twitterUserInfo.FollowersCount = user.getFollowersCount();
        twitterUserInfo.FriendsCount = user.getFriendsCount();
        twitterUserInfo.TimeZone = user.getTimeZone();
        twitterUserInfo.Verified = user.isVerified();
        twitterUserInfo.ProfileImageUrl = user.getProfileImageURL();
        
        return twitterUserInfo;
    }
    
    private Twitter makeTwitter() {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(apiKey, apiSecret);
        return twitter;
    }
    
    public static class OAuthToken {
        public String accessToken;
        public String accessTokenSecret;
    }
}