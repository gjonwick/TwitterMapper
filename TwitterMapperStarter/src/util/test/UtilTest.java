package util.test;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import twitter4j.*;
import util.Util;

import java.awt.image.BufferedImage;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class UtilTest {




    @DataProvider
    public Object[][] distanceBetweenDataProvider(){
        return new Object[][]{
                new Object[]{0.0, 0.0, 0.0, 0.0, 0.0},
                new Object[]{1.0, 1.0, 5.0, 1.0, 444779.70657823497},
                new Object[]{-1.0, -1.0, -5.0, -1.0, 444779.70657823497},
                new Object[]{-3.0, -4.0, 3.0, 4.0, 1111623.8833894278}
        } ;
    }

    @DataProvider
    public Object[][] statusLatitudeDataProvider(){
        return new Object[][]{
                new Object[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                new Object[]{1.0, 5.0, 1.0, -5.0, 0.0, 0.0, 0.5},
                new Object[]{-1.0, -1.0, 7.0, 7.0, 7.0, 7.0, 3.0},
                new Object[]{-3.0, 4.0, 0.0, 0.0, 0.0, 0.0, -1.5}
        } ;
    }

    @DataProvider
    public Object[][] statusLongitudeDataProvider(){
        return new Object[][]{
                new Object[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                new Object[]{1.0, 5.0, 1.0, -5.0, 0.0, 0.0, 2.5},
                new Object[]{-1.0, -1.0, 7.0, 7.0, 7.0, 7.0, 3.0},
                new Object[]{-3.0, 4.0, 0.0, 0.0, 0.0, 0.0, 2.0}
        } ;
    }

    @DataProvider
    public Object[][] statusCoordinateDataProvider(){
        return new Object[][]{
                new Object[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                new Object[]{1.0, 5.0, 1.0, -5.0, 0.0, 0.0, 0.5, 2.5},
                new Object[]{-1.0, -1.0, 7.0, 7.0, 7.0, 7.0, 3.0, 3.0},
                new Object[]{-3.0, 4.0, 0.0, 0.0, 0.0, 0.0, -1.5, 2.0}
        } ;
    }



    @Test(dataProvider = "distanceBetweenDataProvider")
    public void testDistanceBetweenSphericalCoordinates(double firstPointLat, double firstPointLong, double secondPointLat, double secondPointLong, double distance) {
        // Arrange
        ICoordinate firstPoint = mock(ICoordinate.class);
        ICoordinate secondPoint = mock(ICoordinate.class);
        when(firstPoint.getLat()).thenReturn(firstPointLat);
        when(firstPoint.getLon()).thenReturn(firstPointLong);
        when(secondPoint.getLat()).thenReturn(secondPointLat);
        when(secondPoint.getLon()).thenReturn(secondPointLong);

        // Assert
        assertEquals(Util.distanceBetweenSphericalCoordinates(firstPoint, secondPoint), distance);
    }

    @Test(dataProvider = "statusCoordinateDataProvider")
    public void testStatusCoordinate(double lat1, double long1, double lat2, double long2, double lat3, double long3, double statusLatitude, double statusLongitude) {
        Place mockPlace = makePlace(lat1, long1, lat2, long2, lat3, long3);
        Status mockStatus = makeStatus(mockPlace);
        Coordinate coordinate = Util.statusCoordinate(mockStatus);
        assertNotNull(coordinate);
        assertEquals(coordinate.getLat(), statusLatitude);
        assertEquals(coordinate.getLon(), statusLongitude);
    }

    @Test(dataProvider = "statusLatitudeDataProvider")
    public void statusLatitude(double lat1, double long1, double lat2, double long2, double lat3, double long3, double statusLatitude){
        // Arrange
        Place mockPlace = makePlace(lat1, long1, lat2, long2, lat3, long3);
        Status mockStatus = makeStatus(mockPlace);

        // Assert
        assertEquals(Util.statusLatitude(mockStatus), statusLatitude);
    }

    @Test(dataProvider = "statusLongitudeDataProvider")
    public void statusLongitude(double lat1, double long1, double lat2, double long2, double lat3, double long3, double statusLongitude){
        // Arrange
        Place mockPlace = makePlace(lat1, long1, lat2, long2, lat3, long3);
        Status mockStatus = makeStatus(mockPlace);

        // Assert
        assertEquals(Util.statusLongitude(mockStatus), statusLongitude);
    }

    @Test
    public void testImageFromURL() {
        BufferedImage img = Util.imageFromURL("https://vignette.wikia.nocookie.net/mlp/images/d/d1/Rarity_standing_S1E19_CROPPED.png");
        assertNotNull(img);
    }

    private Status makeStatus(Place mockPlace)
    {
        return new Status() {
            @Override
            public Date getCreatedAt() {
                return null;
            }

            @Override
            public long getId() {
                return 0;
            }

            @Override
            public String getText() {
                return "testStatus";
            }

            @Override
            public String getSource() {
                return null;
            }

            @Override
            public boolean isTruncated() {
                return false;
            }

            @Override
            public long getInReplyToStatusId() {
                return 0;
            }

            @Override
            public long getInReplyToUserId() {
                return 0;
            }

            @Override
            public String getInReplyToScreenName() {
                return null;
            }

            @Override
            public GeoLocation getGeoLocation() {
                return null;
            }

            @Override
            public Place getPlace() {
                return mockPlace;
            }

            @Override
            public boolean isFavorited() {
                return false;
            }

            @Override
            public boolean isRetweeted() {
                return false;
            }

            @Override
            public int getFavoriteCount() {
                return 0;
            }

            @Override
            public User getUser() {
                return new User() {
                    @Override
                    public long getId() {
                        return 1;
                    }

                    @Override
                    public String getName() {
                        return "testUser";
                    }

                    @Override
                    public String getScreenName() {
                        return null;
                    }

                    @Override
                    public String getLocation() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }

                    @Override
                    public boolean isContributorsEnabled() {
                        return false;
                    }

                    @Override
                    public String getProfileImageURL() {
                        return "https://vignette.wikia.nocookie.net/mlp/images/d/d1/Rarity_standing_S1E19_CROPPED.png";
                    }

                    @Override
                    public String getBiggerProfileImageURL() {
                        return null;
                    }

                    @Override
                    public String getMiniProfileImageURL() {
                        return null;
                    }

                    @Override
                    public String getOriginalProfileImageURL() {
                        return null;
                    }

                    @Override
                    public String getProfileImageURLHttps() {
                        return null;
                    }

                    @Override
                    public String getBiggerProfileImageURLHttps() {
                        return null;
                    }

                    @Override
                    public String getMiniProfileImageURLHttps() {
                        return null;
                    }

                    @Override
                    public String getOriginalProfileImageURLHttps() {
                        return null;
                    }

                    @Override
                    public boolean isDefaultProfileImage() {
                        return false;
                    }

                    @Override
                    public String getURL() {
                        return null;
                    }

                    @Override
                    public boolean isProtected() {
                        return false;
                    }

                    @Override
                    public int getFollowersCount() {
                        return 0;
                    }

                    @Override
                    public Status getStatus() {
                        return null;
                    }

                    @Override
                    public String getProfileBackgroundColor() {
                        return null;
                    }

                    @Override
                    public String getProfileTextColor() {
                        return null;
                    }

                    @Override
                    public String getProfileLinkColor() {
                        return null;
                    }

                    @Override
                    public String getProfileSidebarFillColor() {
                        return null;
                    }

                    @Override
                    public String getProfileSidebarBorderColor() {
                        return null;
                    }

                    @Override
                    public boolean isProfileUseBackgroundImage() {
                        return false;
                    }

                    @Override
                    public boolean isDefaultProfile() {
                        return false;
                    }

                    @Override
                    public boolean isShowAllInlineMedia() {
                        return false;
                    }

                    @Override
                    public int getFriendsCount() {
                        return 0;
                    }

                    @Override
                    public Date getCreatedAt() {
                        return null;
                    }

                    @Override
                    public int getFavouritesCount() {
                        return 0;
                    }

                    @Override
                    public int getUtcOffset() {
                        return 0;
                    }

                    @Override
                    public String getTimeZone() {
                        return null;
                    }

                    @Override
                    public String getProfileBackgroundImageURL() {
                        return null;
                    }

                    @Override
                    public String getProfileBackgroundImageUrlHttps() {
                        return null;
                    }

                    @Override
                    public String getProfileBannerURL() {
                        return null;
                    }

                    @Override
                    public String getProfileBannerRetinaURL() {
                        return null;
                    }

                    @Override
                    public String getProfileBannerIPadURL() {
                        return null;
                    }

                    @Override
                    public String getProfileBannerIPadRetinaURL() {
                        return null;
                    }

                    @Override
                    public String getProfileBannerMobileURL() {
                        return null;
                    }

                    @Override
                    public String getProfileBannerMobileRetinaURL() {
                        return null;
                    }

                    @Override
                    public boolean isProfileBackgroundTiled() {
                        return false;
                    }

                    @Override
                    public String getLang() {
                        return null;
                    }

                    @Override
                    public int getStatusesCount() {
                        return 0;
                    }

                    @Override
                    public boolean isGeoEnabled() {
                        return false;
                    }

                    @Override
                    public boolean isVerified() {
                        return false;
                    }

                    @Override
                    public boolean isTranslator() {
                        return false;
                    }

                    @Override
                    public int getListedCount() {
                        return 0;
                    }

                    @Override
                    public boolean isFollowRequestSent() {
                        return false;
                    }

                    @Override
                    public URLEntity[] getDescriptionURLEntities() {
                        return new URLEntity[0];
                    }

                    @Override
                    public URLEntity getURLEntity() {
                        return null;
                    }

                    @Override
                    public String[] getWithheldInCountries() {
                        return new String[0];
                    }

                    @Override
                    public int compareTo(User o) {
                        return 0;
                    }

                    @Override
                    public RateLimitStatus getRateLimitStatus() {
                        return null;
                    }

                    @Override
                    public int getAccessLevel() {
                        return 0;
                    }
                };
            }

            @Override
            public boolean isRetweet() {
                return false;
            }

            @Override
            public Status getRetweetedStatus() {
                return null;
            }

            @Override
            public long[] getContributors() {
                return new long[0];
            }

            @Override
            public int getRetweetCount() {
                return 0;
            }

            @Override
            public boolean isRetweetedByMe() {
                return false;
            }

            @Override
            public long getCurrentUserRetweetId() {
                return 0;
            }

            @Override
            public boolean isPossiblySensitive() {
                return false;
            }

            @Override
            public String getLang() {
                return null;
            }

            @Override
            public Scopes getScopes() {
                return null;
            }

            @Override
            public String[] getWithheldInCountries() {
                return new String[0];
            }

            @Override
            public long getQuotedStatusId() {
                return 0;
            }

            @Override
            public Status getQuotedStatus() {
                return null;
            }

            @Override
            public int compareTo(Status o) {
                return 0;
            }

            @Override
            public UserMentionEntity[] getUserMentionEntities() {
                return new UserMentionEntity[0];
            }

            @Override
            public URLEntity[] getURLEntities() {
                return new URLEntity[0];
            }

            @Override
            public HashtagEntity[] getHashtagEntities() {
                return new HashtagEntity[0];
            }

            @Override
            public MediaEntity[] getMediaEntities() {
                return new MediaEntity[0];
            }

            @Override
            public ExtendedMediaEntity[] getExtendedMediaEntities() {
                return new ExtendedMediaEntity[0];
            }

            @Override
            public SymbolEntity[] getSymbolEntities() {
                return new SymbolEntity[0];
            }

            @Override
            public RateLimitStatus getRateLimitStatus() {
                return null;
            }

            @Override
            public int getAccessLevel() {
                return 0;
            }
        };
    }

    private Place makePlace(double lat1, double long1, double lat2, double long2, double lat3, double long3) {
        return new Place() {
            @Override
            public String getName () {
                return null;
            }

            @Override
            public String getStreetAddress() {
                return null;
            }

            @Override
            public String getCountryCode() {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public String getCountry() {
                return null;
            }

            @Override
            public String getPlaceType() {
                return null;
            }

            @Override
            public String getURL() {
                return null;
            }

            @Override
            public String getFullName() {
                return null;
            }

            @Override
            public String getBoundingBoxType() {
                return null;
            }

            @Override
            public GeoLocation[][] getBoundingBoxCoordinates() {
                GeoLocation[][] geoLocation = new GeoLocation[1][3];
                geoLocation[0][0] = new GeoLocation(lat1, long1);
                geoLocation[0][1] = new GeoLocation(lat2, long2);
                geoLocation[0][2] = new GeoLocation(lat3, long3);
                return geoLocation;
            }

            @Override
            public String getGeometryType() {
                return null;
            }

            @Override
            public GeoLocation[][] getGeometryCoordinates() {
                return new GeoLocation[0][];
            }

            @Override
            public Place[] getContainedWithIn() {
                return new Place[0];
            }

            @Override
            public RateLimitStatus getRateLimitStatus() {
                return null;
            }

            @Override
            public int getAccessLevel() {
                return 0;
            }

            @Override
            public int compareTo(Place o) {
                return 0;
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        };
    }
}