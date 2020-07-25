package ui;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import query.Query;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private JSplitPane topLevelSplitPane;
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
        querySplitPane = new JSplitPane(0);
        querySplitPane.setDividerLocation(150);
        querySplitPane.setTopComponent(newQueryPanel);
        querySplitPane.setBottomComponent(layerPanelContainer);

    }

    private void constructTopLevelSplitPane(){
        topLevelSplitPane = new JSplitPane(1);
        topLevelSplitPane.setDividerLocation(150);
        topLevelSplitPane.setLeftComponent(querySplitPane);
        topLevelSplitPane.setRightComponent(map);
        add(topLevelSplitPane, "Center");
    }

    // Add a new query to the set of queries and update the UI to reflect the new query.
    public void addListedQueryPanel(Query query) {
        //GridBagConstraints constraints = initGridConstraints();
        ListedQueryPanel listedQueryPanel = new ListedQueryPanel(query, this, app);
        //addListenersToQueryPanelComponents(listedQueryPanel);
        existingQueryList.add(listedQueryPanel);
        validate();
    }

//    private void addListenersToQueryPanelComponents(ListedQueryPanel listedQueryPanel){
//        JButton removeButton = listedQueryPanel.getRemoveButton();
//        JCheckBox checkBox = listedQueryPanel.getCheckBox();
//        Query currentQuery = listedQueryPanel.getQuery();
//
//        if(removeButton != null){
//            removeButton.addActionListener(e -> {
//                app.terminateQuery(currentQuery);
//                existingQueryList.remove(listedQueryPanel);
//                revalidate();
//            });
//        }
//
//        if(checkBox != null){
//            checkBox.addActionListener(e -> app.updateVisibility());
//        }
//
//    }


    private GridBagConstraints initGridConstraints(){
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.weightx = 1.0;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        return gridConstraints;
    }



    public JMapViewer getViewer() {
        return map;
    }

    public JPanel getExistingQueryList() {
        return existingQueryList;
    }
}
