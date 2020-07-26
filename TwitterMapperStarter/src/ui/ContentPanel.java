package ui;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import query.Query;

import javax.swing.*;
import java.awt.*;

/**
 * Builds the overall layout of the application, containing the map, the new query panel, and the listed query panels.
 */
public class ContentPanel extends JPanel {
    private JSplitPane querySplitPane;
    private JPanel newQueryPanel;
    private JPanel existingQueryList;
    private JPanel layerPanelContainer;
    private JMapViewer map;
    private Application app;

    public ContentPanel(Application app) {
        initialize(app);

        wrapExistingQueryList();
        constructQuerySplitPane(layerPanelContainer);
        constructTopLevelSplitPane();

        revalidate();
        repaint();
    }

    private void initialize(Application app){
        this.app = app;
        map = new JMapViewer();
        map.setMinimumSize(new Dimension(100, 50));
        setLayout(new BorderLayout());
        newQueryPanel = new NewQueryPanel(app);
        existingQueryList = new JPanel();
        layerPanelContainer = new JPanel();
    }

    private void wrapExistingQueryList(){
        // NOTE: We wrap existingQueryList in a container so it gets a pretty border.

        existingQueryList.setLayout(new javax.swing.BoxLayout(existingQueryList, javax.swing.BoxLayout.Y_AXIS));
        layerPanelContainer.setLayout(new BorderLayout());
        layerPanelContainer.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Current Queries"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        layerPanelContainer.add(existingQueryList, BorderLayout.NORTH);
    }

    private void constructQuerySplitPane(JPanel layerPanelContainer){
        querySplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        querySplitPane.setDividerLocation(150);
        querySplitPane.setTopComponent(newQueryPanel);
        querySplitPane.setBottomComponent(layerPanelContainer);

    }

    private void constructTopLevelSplitPane(){
        JSplitPane topLevelSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        topLevelSplitPane.setDividerLocation(150);
        topLevelSplitPane.setLeftComponent(querySplitPane);
        topLevelSplitPane.setRightComponent(map);
        add(topLevelSplitPane, "Center");
    }

    // Add a new query to the set of queries and update the UI by creating a new ListedQueryPanel to reflect the new query.
    public void addListedQueryPanel(Query query) {
        ListedQueryPanel listedQueryPanel = new ListedQueryPanel(query, this, app);
        existingQueryList.add(listedQueryPanel);
        validate();
    }


    public void removeFromExistingQueryList(ListedQueryPanel panel){
        existingQueryList.remove(panel);
    }

    public JMapViewer getViewer() {
        return map;
    }

    public JPanel getExistingQueryList() {
        return existingQueryList;
    }
}
