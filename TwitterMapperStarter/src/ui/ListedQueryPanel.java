package ui;

import javafx.scene.layout.Pane;
import query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The panel representing an existing query.
 * It contains the respective GUI attached to an existing query.
 * Notifies app about different operations on an existing query.
 */
public class ListedQueryPanel extends JPanel {

    private JPanel colorPanel;
    private JButton removeButton;
    private JCheckBox checkBox;
    private Query query;
    private ContentPanel parentPanel;
    private Application app;

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
        colorPanel = new JPanel();
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
                parentPanel.getExistingQueryList().remove(this);
                parentPanel.revalidate();
            });
        }

        if(checkBox != null){
            checkBox.addActionListener(e -> app.updateVisibility());
        }

    }

    public JPanel getColorPanel() {
        return colorPanel;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public Query getQuery() {
        return query;
    }
}

