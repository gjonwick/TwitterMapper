package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import query.Query;
import query.QueryController;
import twitter.LiveTwitterSource;
import twitter.TwitterSource;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * The Twitter viewer application
 * Derived from a JMapViewer demo program written by Jan Peter Stotz
 * Application class acts as a "middleware" controller.
 * It handles different operations, and delegates them to specific classes.
 * Mostly responsible for connecting the panels, the queries, and the map with each other.
 * Contains helper methods used to initialize the main GUI.
 */
public class Application extends JFrame {

    // The content panel, which contains the entire UI
    private ContentPanel contentPanel;

    // The provider of the tiles for the map, we use the Bing source
    private BingAerialTileSource bing;

    // The source of tweets, a TwitterSource, either live or playback
    private TwitterSource twitterSource;

    private void initializeFieldsAndPanels() {
        // To use the live twitter stream, use the following line
        twitterSource = new LiveTwitterSource();

        // To use the recorded twitter stream, use the following line
        // The number passed to the constructor is a speedup value:
        //  1.0 - play back at the recorded speed
        //  2.0 - play back twice as fast
        //twitterSource = new PlaybackTwitterSource(1.0);
        bing = new BingAerialTileSource();
        contentPanel = new ContentPanel(this);


    }

    private void initGUI(){
        // Do UI initialization
        setSize(300, 300);
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initMapConfig(){
        // Always have map markers showing.
        map().setMapMarkerVisible(true);
        // Always have zoom controls showing,
        // and allow scrolling of the map around the edge of the world.
        map().setZoomContolsVisible(true);
        map().setScrollWrapEnabled(true);

        // Use the Bing tile provider
        map().setTileSource(bing);
    }

    private void initBingTimerCalibration(){
        //NOTE This is so that the map eventually loads the tiles once Bing attribution is ready.
        Coordinate coord = new Coordinate(0, 0);

        // Calibrate BingTimer
        Timer bingTimer = new Timer();
        TimerTask bingAttributionCheck = new TimerTask() {
            @Override
            public void run() {
                // This is the best method we've found to determine when the Bing data has been loaded.
                // We use this to trigger zooming the map so that the entire world is visible.
                if (!bing.getAttributionText(0, coord, coord).equals("Error loading Bing attribution data")) {
                    map().setZoom(2);
                    bingTimer.cancel();
                }
            }
        };
        bingTimer.schedule(bingAttributionCheck, 100, 200);
    }

    private void initListeners(){

        // Set up a motion listener to create a tooltip showing the tweets at the pointer position
        map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                ICoordinate pos = map().getPosition(p);
                List<MapMarker> markers = getMarkersCovering(pos, pixelWidth(p));

                if (!markers.isEmpty()) {
                    drawToolTip(markers);
                }
            }
        });

        /* Other listeners go here ... */
    }



    /**
     * Constructs the {@code Application}.
     */
    public Application() {
        super("Twitter content viewer");

        initializeFieldsAndPanels();
        initGUI();
        initMapConfig();
        initBingTimerCalibration();
        initListeners();
    }


    /**
     * Creates a query with the specified parameters, and then delegates the insertion process.
     * It also returns the query, thus notifying the panel that the specified query was added - a functionality which may be needed in future implementations
     * @param queryString the queryString specified by the user
     * @param color the randomly generated color of the queryMarker
     */
    public Query handleQueryCreationAndReturnQuery(String queryString, Color color){
        Query newQuery = new Query(queryString, color, map(), twitterSource);
        addQuery(newQuery);
        return newQuery;
    }


    /**
     * A new query has been entered via the User Interface
     * @param   query   The new query object
     */
    public void addQuery(Query query) {
        QueryController.getInstance().addQuery(query, contentPanel, twitterSource);
    }



    /***
     * The remove button is clicked on a specific query
     * A query has been deleted, remove all traces of it
     * @param query The query object we wish to terminate
     */
    public void terminateQuery(Query query) {
        QueryController.getInstance().terminateQuery(query, twitterSource);
    }


    /**
     * 
     * @param markers
     */
    private void drawToolTip(List<MapMarker> markers){
        MapMarker marker = markers.get(markers.size() - 1);
        MapMarkerWithImage mapMarkerWithImage = (MapMarkerWithImage) marker;
        String tweet = mapMarkerWithImage.getTweet();
        String profilePictureURL = mapMarkerWithImage.getProfileImageUrl();
        // TODO: Use the following method to set the text that appears at the mouse cursor
        map().setToolTipText("<html><img src=" + profilePictureURL + " height=\"42\" width=\"42\">" + tweet + "</html>");
    }


    /**
     * How big is a single pixel on the map?  We use this to compute which tweet markers
     * are at the current most position.
     * @param point mouse pointer coordinates
     * @return distance between the center and the edge of the pixel
     */
    private double pixelWidth(Point point) {
        ICoordinate center = map().getPosition(point);
        ICoordinate edge = map().getPosition(new Point(point.x + 1, point.y));
        return Util.distanceBetweenSphericalCoordinates(center, edge);
    }

    /**
     * Get those layers (of tweet markers) that are visible because their corresponding query is enabled
     * @return The set of visible layers
     */
    private Set<Layer> getVisibleLayers() {
        Set<Layer> ans = new HashSet<>();
        for (Query q : QueryController.getInstance()) {
            if (q.getVisible()) {
                ans.add(q.getLayer());
            }
        }
        return ans;
    }

    /**
     * Get all the markers at the given map position, at the current map zoom setting
     * @param pos current position
     * @param pixelWidth the width of the pixel
     * @return The list of mapMarkers
     */
    private List<MapMarker> getMarkersCovering(ICoordinate pos, double pixelWidth) {
        List<MapMarker> ans = new ArrayList<>();
        Set<Layer> visibleLayers = getVisibleLayers();
        for (MapMarker m : map().getMapMarkerList()) {
            if (!visibleLayers.contains(m.getLayer())) continue;
            double distance = Util.distanceBetweenSphericalCoordinates(m.getCoordinate(), pos);
            if (distance < m.getRadius() * pixelWidth) {
                ans.add(m);
            }
        }
        return ans;
    }


    /**
     * Update which queries are visible after any checkBox has been changed
     */
    public void updateVisibility() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Recomputing visible queries");
            for (Query q : QueryController.getInstance()) {
                JCheckBox box = q.getCheckBox();
                //Boolean state = box.isSelected();
                q.setVisible(box.isSelected());
            }
            map().repaint();
        });
    }

    // Getters
    public JMapViewer map() {
        return contentPanel.getViewer();
    }

}
