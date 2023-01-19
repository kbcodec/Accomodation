package edu.accomodation.Interfaces;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface IFormLayout {
    static final String bannerImageUrl = "imgs/header-image.jpg";

    default void addBanner(JPanel imagePanel) throws IOException {
        BufferedImage img = ImageIO.read(new File(bannerImageUrl));
        imagePanel.setBackground(new Color(0, 0, 0, 0));
        imagePanel.setOpaque(false);
        imagePanel.setLayout(new GridLayout());
        imagePanel.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        imagePanel.add(new JLabel(new ImageIcon(img)));
    }
}
