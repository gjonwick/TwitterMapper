package ui;

import query.Query;

import javax.swing.*;
import java.awt.*;

/**
 * The panel representing an existing query.
 * It contains the respective GUI attached to an existing query.
 * Notifies app about different operations on an existing query.
 */
public class ListedQueryPanel extends JPanel {

    private JButton removeButton;
    private JCheckBox checkBox;
    private final Query query;
    private final ContentPanel parentPanel;
    private final Application app;

    public ListedQueryPanel(Query query, ContentPanel parentPanel, Application app) {
        this.query = query;
        this.parentPanel = parentPanel;
        this.app = app;
        buildBasicQueryPanel();
    }

    public void buildBasicQueryPanel(){
        GridBagConstraints gridConstraints = initGridConstraints();
        addColorPanel(gridConstraints);
        addCheckBox(gridConstraints);
        addRemoveButton();
        addListenersToQueryPanelComponents();
    }

    public void addColorPanel(GridBagConstraints gridConstraints){
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(query.getColor());
        colorPanel.setPreferredSize(new Dimension(30, 30));
        this.add(colorPanel, gridConstraints);
    }

    public void addRemoveButton(){
        removeButton = new JButton("X");
        removeButton.setPreferredSize(new Dimension(30, 20));
        this.add(removeButton);
    }

    public void addCheckBox(GridBagConstraints gridConstraints){
        checkBox = new JCheckBox(query.getQueryString());
        checkBox.setSelected(true);
        query.setCheckBox(checkBox);
        this.add(checkBox, gridConstraints);
    }

    private GridBagConstraints initGridConstraints(){
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.weightx = 1.0;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        return gridConstraints;
    }

    private void addListenersToQueryPanelComponents(){

        if(removeButton != null){
            removeButton.addActionListener(e -> {
                app.terminateQuery(query);
                parentPanel.removeFromExistingQueryList(this);
                parentPanel.revalidate();
            });
        }

        if(checkBox != null){
            checkBox.addActionListener(e -> app.updateVisibility());
        }

    }

    public Query getQuery() {
        return query;
    }
}

