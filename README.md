# socialauth

Simple social authentication library for Java. Supports Google OAuth v.2, Facebook OAuth v.2 and Twitter OAuth v.1.

## How to use

### Maven
Add these to your pom.xml:

    <repositories>
      <repository>
        <id>fakerj-mvn-repo</id>
        <url>https://raw.github.com/loki2302/socialauth/mvn-repo/</url>
        <snapshots>
          <enabled>true</enabled>
          <updatePolicy>always</updatePolicy>
        </snapshots>
      </repository>
    </repositories>
    ...
    <dependencies>
      <dependency>
        <groupId>me.loki2302.socialauth</groupId>
        <artifactId>socialauth</artifactId>
        <version>0.0.1-SNAPSHOT</version>
      </dependency>
    </dependencies>

### Google authentication

    // Construct google authentication service client
    GoogleAuthenticationService google = new GoogleAuthenticationService(
      "<YOUR_API_KEY>",
      "<YOUR_API_SECRET>",
      "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",
      "http://localhost:8080/googleCallback");

    // Redirect user to authUri to start authentication process
    String authUri = google.getAuthenticationUri();

    // Write a handler for /googleCallback?code={code}
    ...
    String accessToken = google.getAccessToken(code);
    ...

    // Optional: use accessToken to retrieve user details
    GoogleUserInfo userInfo = google.getUserInfo(accessToken);

### Facebook authentication

TODO: Facebook

### Twitter authentication

TODO: Twitter


