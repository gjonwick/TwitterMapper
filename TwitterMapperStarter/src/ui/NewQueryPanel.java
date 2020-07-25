package ui;

import query.Query;
import query.QueryDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * A UI panel for entering new queries.
 */
public class NewQueryPanel extends JPanel {
    private final JTextField newQuery;
    private final JPanel colorSetter;
    private final Application app;
    private final Random random;

    private void initListeners(){
        
    }

    public NewQueryPanel(Application app) {
        this.app = app;
        this.colorSetter = new JPanel();

        newQuery = new JTextField(10);
        JLabel queryLabel = new JLabel("Enter Search: ");
        random = new Random();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        queryLabel.setLabelFor(newQuery);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.NONE;
        c.gridy = 0;
        c.gridx = 0;
        add(queryLabel, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        newQuery.setMaximumSize(new Dimension(200, 20));
        c.gridx = 1;
        add(newQuery, c);

        add(Box.createRigidArea(new Dimension(5, 5)));

        JLabel colorLabel = new JLabel("Select Color: ");
        colorSetter.setBackground(getRandomColor());

        c.gridwidth = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.NONE;
        c.gridy = 1;
        c.gridx = 0;
        add(colorLabel, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        colorSetter.setMaximumSize(new Dimension(200, 20));
        add(colorSetter, c);

        add(Box.createRigidArea(new Dimension(5, 5)));

        JButton addQueryButton = new JButton("Add New Search");
        c.gridx = GridBagConstraints.RELATIVE;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = GridBagConstraints.RELATIVE;       //third row
        add(addQueryButton, c);

        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("New Search"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        addQueryButton.addActionListener(e -> {
            if (!newQuery.getText().equals("")) {
                addQuery(newQuery.getText().toLowerCase());
                updateNextQueryColor();
                newQuery.setText("");
            }
        });

        // This makes the "Enter" key submit the query.
        app.getRootPane().setDefaultButton(addQueryButton);

        colorSetter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Color newColor = JColorChooser.showDialog(
                            app,
                            "Choose Background Color",
                            colorSetter.getBackground());
                    // newColor is "null" if user clicks "Cancel" button on JColorChooser popup.
                    if (newColor != null) {
                        colorSetter.setBackground(newColor);
                    }
                }
            }
        });
    }

    private void addQuery(String newQueryString) {
        app.handleQueryCreation(newQueryString, colorSetter.getBackground(), app.map(), app.getTwitterSource());
    }


    private void updateNextQueryColor(){
        colorSetter.setBackground(getRandomColor());
    }

    public Color getRandomColor() {
        // Pleasant colors: https://stackoverflow.com/questions/4246351/creating-random-colour-in-java#4246418
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        return Color.getHSBColor(hue, saturation, luminance);
    }
}
