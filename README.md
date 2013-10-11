# socialauth

Simple social authentication library for Java:

1. Google OAuth v.2
2. Facebook OAuth v.2
3. Twitter OAuth v.1

## How to use

### Maven
Add these to your pom.xml:

    <dependencies>
      <dependency>
        <groupId>me.loki2302</groupId>
        <artifactId>socialauth</artifactId>
        <version>0.0.1</version>
      </dependency>
    </dependencies>

### Google authentication

Construct Google authentication service client:

    GoogleAuthenticationService google = new GoogleAuthenticationService(
      "<YOUR_API_KEY>",
      "<YOUR_API_SECRET>",
      "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",
      "http://localhost:8080/googleCallback");

Redirect user to `authUri` to start authentication process:

    String authUri = google.getAuthenticationUri();

Write an authentication callback handler (`/googleCallback?code={code}`):

    ...
    String accessToken = google.getAccessToken(code);
    ...

Optional: use `accessToken` to retrieve user details:

    GoogleUserInfo userInfo = google.getUserInfo(accessToken);

### Facebook authentication

Construct Facebook authentication service client:

    FacebookAuthenticationService facebook = new FacebookAuthenticationService(
      "<YOUR_API_KEY>",
      "<YOUR_API_SECRET>",
      "email",
      "http://localhost:8080/facebookCallback");

Redirect user to `authUri` to start authentication process:

    String authUri = facebook.getAuthenticationUri();

Write an authentication callback handler (`/facebookCallback?code={code}`)

    ...
    String accessToken = facebook.getAccessToken(code);
    ...

Optional: use `accessToken` to retrieve user details:

    FacebookUserInfo userInfo = facebook.getUserInfo(accessToken);

### Twitter authentication

Construct Twitter authentication service client:

    TwitterAuthenticationService twitter = new TwitterAuthenticationService(
      "<YOUR_API_KEY>",
      "<YOUR_API_SECRET>",
      "http://localhost:8080/twitterCallback");

Redirect user to `authUri` to start authentication process:

    String authUri = twitter.getAuthenticationUri();

Write an authentication callback handler (`/twitterCallback?oauth_token={oauthToken}&oauth_verifier={oauthVerifier}`):

    ...
    OAuthToken accessToken = twitter.getAccessToken(oauthToken, oauthVerifier);
    ...

Optional: use `accessToken` to retrieve user details:

    TwitterUserInfo userInfo = twitter.getUserInfo(accessToken);


