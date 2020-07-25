package ui;

import javafx.scene.layout.Pane;
import query.Query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListedQueryPanel extends JPanel {

    private JPanel colorPanel;
    private JButton removeButton;
    private JCheckBox checkBox;
    private Query query;

    public ListedQueryPanel(Query query) {
        this.query = query;
        buildBasicQueryPanel();
    }

    public void buildBasicQueryPanel(){
        GridBagConstraints gridConstraints = initGridConstraints();
        addColorPanel(gridConstraints);
        addCheckBox(gridConstraints);
        addRemoveButton();
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

