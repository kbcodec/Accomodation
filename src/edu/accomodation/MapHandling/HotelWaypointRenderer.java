package edu.accomodation.MapHandling;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class HotelWaypointRenderer extends WaypointPainter<HotelWaypoint> {
    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
        for(HotelWaypoint wp: getWaypoints()) {
            Point2D p = map.getTileFactory().geoToPixel(wp.getPosition(), map.getZoom());
            Rectangle rec = map.getViewportBounds();
            int x = (int)(p.getX() - rec.getX());
            int y = (int)(p.getY() - rec.getY());
            JButton cmd = wp.getButton();
            cmd.setLocation(x-cmd.getWidth()/2, y-cmd.getHeight());
        }
    }
}
