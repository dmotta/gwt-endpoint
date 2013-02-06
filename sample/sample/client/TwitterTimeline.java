package sample.client;

import com.google.gwt.core.client.* ;

final public class TwitterTimeline extends JavaScriptObject {

  protected TwitterTimeline(){
  }

  public native java.lang.String getText()/*-{
     return this['text'];
  }-*/;

  public native com.google.gwt.core.client.JavaScriptObject getContributors()/*-{
     return this['contributors'];
  }-*/;

  public native com.google.gwt.core.client.JavaScriptObject getGeo()/*-{
     return this['geo'];
  }-*/;

  public native com.google.gwt.core.client.JavaScriptObject getInReplyToScreenName()/*-{
     return this['in_reply_to_screen_name'];
  }-*/;

  public native java.lang.Boolean isTruncated()/*-{
     return this['truncated'];
  }-*/;

  public native java.lang.Long getId()/*-{
     return this['id'];
  }-*/;

  public native java.lang.String getSource()/*-{
     return this['source'];
  }-*/;

  public native java.lang.Boolean isFavorited()/*-{
     return this['favorited'];
  }-*/;

  public native com.google.gwt.core.client.JavaScriptObject getInReplyToStatusId()/*-{
     return this['in_reply_to_status_id'];
  }-*/;

  public native com.google.gwt.core.client.JavaScriptObject getInReplyToUserId()/*-{
     return this['in_reply_to_user_id'];
  }-*/;

  public native java.lang.String getCreatedAt()/*-{
     return this['created_at'];
  }-*/;

  public native com.google.gwt.core.client.JavaScriptObject getPlace()/*-{
     return this['place'];
  }-*/;

  public native User getUser()/*-{
     return this['user'];
  }-*/;

  public native com.google.gwt.core.client.JavaScriptObject getCoordinates()/*-{
     return this['coordinates'];
  }-*/;

  final static public class User extends JavaScriptObject {

    protected User(){
    }

    public native java.lang.String getLocation()/*-{
       return this['location'];
    }-*/;

    public native java.lang.Boolean isProfileBackgroundTile()/*-{
       return this['profile_background_tile'];
    }-*/;

    public native java.lang.Long getStatusesCount()/*-{
       return this['statuses_count'];
    }-*/;

    public native java.lang.String getLang()/*-{
       return this['lang'];
    }-*/;

    public native java.lang.String getProfileLinkColor()/*-{
       return this['profile_link_color'];
    }-*/;

    public native java.lang.Long getId()/*-{
       return this['id'];
    }-*/;

    public native java.lang.Boolean isFollowing()/*-{
       return this['following'];
    }-*/;

    public native java.lang.Boolean isProtected()/*-{
       return this['protected'];
    }-*/;

    public native java.lang.Long getFavouritesCount()/*-{
       return this['favourites_count'];
    }-*/;

    public native java.lang.String getProfileTextColor()/*-{
       return this['profile_text_color'];
    }-*/;

    public native java.lang.Boolean isVerified()/*-{
       return this['verified'];
    }-*/;

    public native java.lang.String getDescription()/*-{
       return this['description'];
    }-*/;

    public native java.lang.Boolean isContributorsEnabled()/*-{
       return this['contributors_enabled'];
    }-*/;

    public native java.lang.String getProfileSidebarBorderColor()/*-{
       return this['profile_sidebar_border_color'];
    }-*/;

    public native java.lang.String getName()/*-{
       return this['name'];
    }-*/;

    public native java.lang.String getProfileBackgroundColor()/*-{
       return this['profile_background_color'];
    }-*/;

    public native java.lang.String getCreatedAt()/*-{
       return this['created_at'];
    }-*/;

    public native java.lang.Long getFollowersCount()/*-{
       return this['followers_count'];
    }-*/;

    public native java.lang.Boolean isGeoEnabled()/*-{
       return this['geo_enabled'];
    }-*/;

    public native java.lang.String getProfileBackgroundImageUrl()/*-{
       return this['profile_background_image_url'];
    }-*/;

    public native java.lang.Boolean isFollowRequestSent()/*-{
       return this['follow_request_sent'];
    }-*/;

    public native java.lang.String getUrl()/*-{
       return this['url'];
    }-*/;

    public native java.lang.Long getUtcOffset()/*-{
       return this['utc_offset'];
    }-*/;

    public native java.lang.String getTimeZone()/*-{
       return this['time_zone'];
    }-*/;

    public native java.lang.Boolean isNotifications()/*-{
       return this['notifications'];
    }-*/;

    public native java.lang.Boolean isProfileUseBackgroundImage()/*-{
       return this['profile_use_background_image'];
    }-*/;

    public native java.lang.Long getFriendsCount()/*-{
       return this['friends_count'];
    }-*/;

    public native java.lang.String getProfileSidebarFillColor()/*-{
       return this['profile_sidebar_fill_color'];
    }-*/;

    public native java.lang.String getScreenName()/*-{
       return this['screen_name'];
    }-*/;

    public native java.lang.String getProfileImageUrl()/*-{
       return this['profile_image_url'];
    }-*/;

    public native java.lang.Long getListedCount()/*-{
       return this['listed_count'];
    }-*/;

  }
}
