package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapMarkerWithImage extends MapMarkerCircle {
    public static final double defaultMarkerSize;

    public BufferedImage img;
    public String tweet;
    public String profileImageUrl;

    static {
        defaultMarkerSize = 17.0;
    }

    public MapMarkerWithImage(Layer layer, Coordinate coordinate, Color color, String profileImageURL, String tweet) {
        super(layer, null, coordinate, defaultMarkerSize, STYLE.FIXED, getDefaultStyle());
        setColor(Color.BLACK);
        setBackColor(color);
        img = Util.imageFromURL(profileImageURL);
        this.tweet = tweet;
        profileImageUrl = profileImageURL;
    }

    @Override
    public void paint(Graphics graphics, Point position, int radius) {
        int size = radius * 2;
        if (graphics instanceof Graphics2D && this.getBackColor() != null) {
            Graphics2D graphics2D = (Graphics2D) graphics;
            Composite oldComposite = graphics2D.getComposite();
            graphics2D.setComposite(AlphaComposite.getInstance(3));
            graphics2D.setPaint(this.getBackColor());
            graphics.fillOval(position.x - radius, position.y - radius, size, size);
            graphics2D.setComposite(oldComposite);
            graphics.drawImage(img, position.x - 10, position.y - 10, 20,20,null);
        }
    }

    public String getTweet() {
        return this.tweet;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

}