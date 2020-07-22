package view;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import ui.MapMarkerWithImage;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MapDisplay implements Observer, DisplayElement{

    // The map on which to display markers when the query matches
    private final JMapViewer map;
    // Each query has its own "layer" so they can be turned on and off all at once
    private Layer layer;
    // The color of the outside area of the marker
    private final Color color;

    // The checkBox in the UI corresponding to this query (so we can turn it on and off and delete it)
    private JCheckBox checkBox;

    private MapMarkerWithImage mapMarkerWithImage;

    public MapDisplay(JMapViewer map, Layer layer, Color color) {
        this.map = map;
        this.layer = layer;
        this.color = color;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void display() {

    }
}
