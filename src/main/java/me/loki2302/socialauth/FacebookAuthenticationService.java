package me.loki2302.socialauth;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


import me.loki2302.socialauth.impl.OAuth2Template;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Facebook OAuth v.2 authenticator service */
public class FacebookAuthenticationService {    
    private final static String FacebookAccessCodeServiceEndpointUri = "https://www.facebook.com/dialog/oauth";
    private final static String FacebookAccessTokenServiceEndpointUri = "https://graph.facebook.com/oauth/access_token";
    
    private final static OAuth2Template oauth2Template = new OAuth2Template();
    
    private final String clientId;
    private final String clientSecret; 
    private final String scope;
    private final String callbackUri;
    
    /**
     * @param clientId Facebook application id
     * @param clientSecret Facebook application secret
     * @param scope A space separated string with a list of desired scopes 
     * @param callbackUri URL to redirect the user to once Facebook part is done */
    public FacebookAuthenticationService(
            String clientId, 
            String clientSecret, 
            String scope, 
            String callbackUri) {
        
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.callbackUri = callbackUri;
    }
    
    /**
     * Get URI to redirect user to the Facebook authentication service. 
     * 
     * @return Facebook authentication service URI */
    public String getAuthenticationUri() {
        return oauth2Template.makeAuthenticationUri(
                FacebookAccessCodeServiceEndpointUri, 
                clientId, 
                callbackUri, 
                scope);
    }
    
    /**
     * Get OAuth v.2 access token based on given code parameter 
     * 
     * @return OAuth v.2 access token */
    public String getAccessToken(String code) {
        String accessTokenResponse;
        try {
            accessTokenResponse = oauth2Template.getAccessTokenResponse(
                    FacebookAccessTokenServiceEndpointUri, 
                    code, 
                    clientId, 
                    clientSecret, 
                    callbackUri);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        List<NameValuePair> responseNameValuePairs = URLEncodedUtils.parse(accessTokenResponse, Consts.UTF_8);
        for(NameValuePair nameValuePair : responseNameValuePairs) {
            if(nameValuePair.getName().equals("access_token")) {
                return nameValuePair.getValue();
            }
        }
        
        throw new RuntimeException("didn't expect to get this far");
    }
    
    /**
     * Get Facebook user details using provided access token
     * 
     * @return Facebook user details */
    public FacebookUserInfo getUserInfo(String accessToken) {
        String meUrl;
        try {
            meUrl = new URIBuilder("https://graph.facebook.com/me")
                .addParameter("access_token", accessToken)
                .build()
                .toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    
        HttpGet getMeRequest = new HttpGet(meUrl);            
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response;
        try {
            response = client.execute(getMeRequest);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        String responseString;
        try {
            responseString = IOUtils.toString(response.getEntity().getContent());
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(responseString, FacebookUserInfo.class);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
