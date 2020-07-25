package util;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import twitter4j.GeoLocation;
import twitter4j.Status;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Helpful methods that don't clearly fit anywhere else.
 */
public class Util {

    private static final int RADIUS = 6371000;   // radius of earth in metres
    public static BufferedImage defaultImage = imageFromURL("http://png-2.findicons.com/files/icons/1995/web_application/48/smiley.png");

    /**
     * Find distance in metres between two lat/lon points
     *
     * @param p1  first point
     * @param p2  second point
     * @return distance between p1 and p2 in metres
     */
    public static double distanceBetween(ICoordinate p1, ICoordinate p2) {
        double lat1 = p1.getLat() / 180.0 * Math.PI;
        double lat2 = p2.getLat() / 180.0 * Math.PI;
        double deltaLon = (p2.getLon() - p1.getLon()) / 180.0 * Math.PI;
        double deltaLat = (p2.getLat() - p1.getLat()) / 180.0 * Math.PI;

        double a = Math.sin(deltaLat / 2.0) * Math.sin(deltaLat / 2.0)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2.0) * Math.sin(deltaLon / 2.0);
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * RADIUS;
    }


    public static GeoLocation statusLocation(Status status) {
        return new GeoLocation(statusLatitude(status), statusLongitude(status));
    }

    public static Coordinate GeoLocationToCoordinate(GeoLocation loc) {
        return new Coordinate(loc.getLatitude(), loc.getLongitude());
    }

    public static Coordinate statusCoordinate(Status status) {
        return new Coordinate(statusLatitude(status), statusLongitude(status));
    }

    private static double statusLatitude(Status status){
        GeoLocation bottomRight = status.getPlace().getBoundingBoxCoordinates()[0][0];
        GeoLocation topLeft = status.getPlace().getBoundingBoxCoordinates()[0][2];
        return (bottomRight.getLatitude() + topLeft.getLatitude())/2;
    }

    private static double statusLongitude(Status status){
        GeoLocation bottomRight = status.getPlace().getBoundingBoxCoordinates()[0][0];
        GeoLocation topLeft = status.getPlace().getBoundingBoxCoordinates()[0][2];
        return (bottomRight.getLongitude() + topLeft.getLongitude())/2;
    }




    public static BufferedImage imageFromURL(String url) {
        try {
            BufferedImage img = ImageIO.read(new URL(url));
            if (img == null) return defaultImage;
            return img;
        } catch (IOException e) {
            return defaultImage;
        }
    }
}
