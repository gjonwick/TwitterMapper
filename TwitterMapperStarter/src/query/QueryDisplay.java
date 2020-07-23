package query;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import twitter4j.Status;
import twitter4j.User;
import ui.MapMarkerWithImage;
import util.Util;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class QueryDisplay implements Observer, DisplayElement{

    private final Color color;
    private final Query query;
    private final JMapViewer map;
    private final Observable observable;
    private Status status;
    private final Layer layer;
    private MapMarkerWithImage mapMarkerWithImage;


    public QueryDisplay(Color color, Query query, JMapViewer map, Observable observable) {
        this.color = color;
        this.query = query;
        this.map = map;
        this.observable = observable;
        this.layer = new Layer(query.getQueryString());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Status){
            Status status = (Status) arg;
            if(query.getFilter().matches(status)) {
                this.status = status;
                this.mapMarkerWithImage = new MapMarkerWithImage(getLayer(), Util.statusCoordinate(status), getColor(), status.getUser().getProfileImageURL(), status.getText());
                display();
            }
        }
    }

    @Override
    public void display() {
        map.addMapMarker(mapMarkerWithImage);
    }

    public Color getColor() {
        return color;
    }

    public Query getQuery() {
        return query;
    }

    public JMapViewer getMap() {
        return map;
    }

    public Observable getObservable() {
        return observable;
    }

    public Status getStatus() {
        return status;
    }

    public Layer getLayer() {
        return layer;
    }

    public MapMarkerWithImage getMapMarkerWithImage() {
        return mapMarkerWithImage;
    }
}
