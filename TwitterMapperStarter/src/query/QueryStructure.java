package query;

import filters.Filter;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import twitter4j.Status;
import twitter4j.User;
import ui.MapMarkerWithImage;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class QueryStructure {

    // The string representing the filter for this query
    private final String queryString;
    // The filter parsed from the queryString
    private final Filter filter;

    public QueryStructure(String queryString, Color color, JMapViewer map) {
        this.queryString = queryString;
        this.filter = Filter.parse(queryString);
    }


    public Filter getFilter() {
        return filter;
    }

    public String getQueryString() {
        return queryString;
    }

    @Override
    public String toString() {
        return "Query: " + queryString;
    }


}
