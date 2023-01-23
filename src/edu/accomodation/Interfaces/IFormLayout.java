package edu.accomodation.Interfaces;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
 * Interfejs zapewnia domyślną metodę dodawania obrazu banera do JPanel
 */
public interface IFormLayout {

    /** Obraz banera odczytywany z pliku.*/
    static final String bannerImageUrl = "imgs/header-image.jpg";


    /**
     * Metoda ustawia tło i układ, dodaje ImageIcon obrazu banera do panelu
     * i ustawia preferowany rozmiar na rozmiar obrazu
     * @param imagePanel - JPanel jako argument
     */
    default void addBanner(JPanel imagePanel) throws IOException {
        BufferedImage img = ImageIO.read(new File(bannerImageUrl));
        imagePanel.setBackground(new Color(0, 0, 0, 0));
        imagePanel.setOpaque(false);
        imagePanel.setLayout(new GridLayout());
        imagePanel.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        imagePanel.add(new JLabel(new ImageIcon(img)));
    }
}
