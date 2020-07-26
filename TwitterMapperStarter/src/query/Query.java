package query;

import filters.Filter;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import twitter4j.Status;
import twitter4j.User;
import ui.MapMarkerWithImage;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A query over the twitter stream.
 * TODO: Task 4: you are to complete this class.
 */
public class Query implements Observer, DisplayElement {



    // The map on which to display markers when the query matches
    private final JMapViewer map;
    // Each query has its own "layer" so they can be turned on and off all at once
    private final Layer layer;
    // The color of the outside area of the marker
    private final Color color;
    // The string representing the filter for this query
    private final String queryString;
    // The filter parsed from the queryString
    private final Filter filter;
    // The checkBox in the UI corresponding to this query (so we can turn it on and off and delete it)
    private JCheckBox checkBox;

    private final Observable subject;

    // Current Status
    private Status status;

    private List<MapMarker> markers;

    public Query(String queryString, Color color, JMapViewer map, Observable subject) {
        this.queryString = queryString;
        this.filter = Filter.parse(queryString);
        this.color = color;
        this.layer = new Layer(queryString);
        this.map = map;
        this.subject = subject;
        markers = new ArrayList<>();
        subject.addObserver(this);
    }

    @Override
    public String toString() {
        return "Query: " + queryString;
    }

    /**
     * This query is no longer interesting, so terminate it and remove all traces of its existence.
     *
     * TODO: Implement this method
     */
    public void terminate() {
        layer.setVisible(false);
        removeMarkers();
        subject.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Status){
            status = (Status) arg;
            markers.add(createNewMapMarker());
            if(getFilter().matches(status)){
                display();
            }
        }
    }

    /**
     * Create a new MapMarkerWithImage
     * @return MapMarkerWithImage object
     */
    private MapMarkerWithImage createNewMapMarker(){
        Coordinate coordinate = Util.statusCoordinate(status);
        User user = status.getUser();
        String profileImageURL = user.getProfileImageURL();
        String tweet = status.getText();
        return new MapMarkerWithImage(getLayer(), coordinate, getColor(), profileImageURL, tweet);
    }

    /**
     * Remove markers
     */
    private void removeMarkers(){
        for(MapMarker mapMarker : markers){
            map.removeMapMarker(mapMarker);
        }
    }

    @Override
    public void display() {
        map.addMapMarker(markers.get(markers.size() - 1));
    }

    public Color getColor() {
        return color;
    }
    public String getQueryString() {
        return queryString;
    }
    public Filter getFilter() {
        return filter;
    }
    public Layer getLayer() {
        return layer;
    }
    public JCheckBox getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }
    public void setVisible(boolean visible) {
        layer.setVisible(visible);
    }
    public boolean getVisible() { return layer.isVisible(); }

    public JMapViewer getMap() {
        return map;
    }

    public List<MapMarker> getMarkers() {
        return markers;
    }

    public Status getStatus() {
        return status;
    }

}

