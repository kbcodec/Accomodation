package edu.accomodation.Waypoints;

import edu.accomodation.Hotel;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyWaypoint extends DefaultWaypoint {
    private Hotel hotel;
    private JButton button;
    private JTabbedPane contextPanel;
    private List<JLabel> hotelLabels;
    private JButton nextBtn;
    private JButton prevBtn;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    int actualIter = 0;

    public MyWaypoint(GeoPosition coord, Hotel hotel, JTabbedPane contextPanel, List<JLabel> hotelLabels, JButton nextBtn, JButton prevBtn) {
        super(coord);
        this.hotel = hotel;
        this.contextPanel = contextPanel;
        this.hotelLabels = hotelLabels;
        this.prevBtn = prevBtn;
        this.nextBtn = nextBtn;
        initButton();
    }

    private void initButton() {
        button = new ButtonWaypoint();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contextPanel.setSelectedIndex(3);
                Object[] myArr = hotelLabels.toArray();
                JLabel hotelName = (JLabel)myArr[0];
                JLabel hotelWeb = (JLabel)myArr[1];
                JLabel hotelCat = (JLabel)myArr[2];
                JLabel hotelDesc = (JLabel)myArr[3];
                JLabel hotelImg = (JLabel)myArr[4];

                hotelName.setText(hotel.getName());
                hotelWeb.setText(hotel.getWeb());
                hotelCat.setText(hotel.getCategory());
                hotelDesc.setText("<html><p text-align=\"justify\">" + hotel.getDescription() + "</p></html>");

                Image image1;
                Image image2;
                Image image3;
                Image dimg1 = null;
                Image dimg2 = null;
                Image dimg3 = null;
                List<Image> images = new ArrayList<>();

                //ZMIENIC URL NA hotel.getPicture1 itd.
                try {
                    URL url1 = new URL("https://drive.google.com/uc?id=1cSBUuq-kdyEWDoawQAVJN27bPouhGKI4");
                    URL url2 = new URL("https://drive.google.com/uc?id=1EaXVsqX4-VyaTw1xf0j9Eu9TxiDjuN5I");
                    URL url3 = new URL("https://drive.google.com/uc?id=19wPx2rm22OtYGeDBdpMHnnA2Bcc3knne");
                    URLConnection uc1 = url1.openConnection();
                    uc1.addRequestProperty("User-Agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                    uc1.connect();
                    URLConnection uc2 = url2.openConnection();
                    uc2.addRequestProperty("User-Agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                    uc2.connect();
                    URLConnection uc3 = url3.openConnection();
                    uc3.addRequestProperty("User-Agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                    uc3.connect();
                    image1 = ImageIO.read(url1);
                    image2 = ImageIO.read(url2);
                    image3 = ImageIO.read(url3);
                    dimg1 = image1.getScaledInstance(hotelImg.getWidth(), hotelImg.getHeight(), Image.SCALE_SMOOTH);
                    dimg2 = image2.getScaledInstance(hotelImg.getWidth(), hotelImg.getHeight(), Image.SCALE_SMOOTH);
                    dimg3 = image3.getScaledInstance(hotelImg.getWidth(), hotelImg.getHeight(), Image.SCALE_SMOOTH);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                images.add(dimg1);
                images.add(dimg2);
                images.add(dimg3);

                hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
                nextBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(actualIter < 2) {
                            actualIter++;
                            hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
                        } else {
                            actualIter = 0;
                            hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
                        }
                    }
                });

                prevBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(actualIter > 0) {
                            actualIter--;
                            hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
                        } else {
                            actualIter = 2;
                            hotelImg.setIcon(new ImageIcon(images.get(actualIter)));
                        }
                    }
                });
            }
        });
    }
}
