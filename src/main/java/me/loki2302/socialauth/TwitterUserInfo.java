package me.loki2302.socialauth;

import me.loki2302.socialauth.impl.JsonUtils;

public class TwitterUserInfo {
    public String Id;
    public String Name;
    public String ScreenName;
    public String Url;
    public int FollowersCount;
    public int FriendsCount;
    public String TimeZone;
    public boolean Verified;
    public String ProfileImageUrl;

    @Override
    public String toString() {
        return JsonUtils.asJson(this);
    }
}