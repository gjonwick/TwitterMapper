package query;

import twitter4j.Status;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class QueryDisplay implements Observer, DisplayElement{

    private Color color;
    private Query query;


    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void display(Status status) {

    }
}
