package me.loki2302.socialauth;

import me.loki2302.socialauth.impl.JsonUtils;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterUserInfo {
    /*@JsonProperty("id")*/ public String Id;
    /*@JsonProperty("name")*/ public String Name;
    /*@JsonProperty("screen_name")*/ public String ScreenName;
    /*@JsonProperty("url")*/ public String Url;
    /*@JsonProperty("followers_count")*/ public int FollowersCount;
    /*@JsonProperty("friends_count")*/ public int FriendsCount;
    /*@JsonProperty("time_zone")*/ public String TimeZone;
    /*@JsonProperty("verified")*/ public boolean Verified;
    /*@JsonProperty("profile_image_url")*/ public String ProfileImageUrl;

    @Override
    public String toString() {
        return JsonUtils.asJson(this);
    }
}