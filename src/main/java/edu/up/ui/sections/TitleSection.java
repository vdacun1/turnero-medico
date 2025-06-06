package edu.up.ui.sections;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Sección de logo y título en el header.
 * Carga el logo médico desde los recursos del proyecto.
 */
public class TitleSection {

  private static final String LOGO_PATH = "/images/medical-logo.png";
  private static final String TITLE_TEXT = "Turnero médico";
  private static final String FALLBACK_LOGO_TEXT = "🏥";
  private static final int LOGO_SIZE = 32;
  private static final int TITLE_FONT_SIZE = 24;

  private final JPanel panel;

  /**
   * Constructor que inicializa la sección de título con logo e información.
   */
  public TitleSection() {
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

    JLabel logoLabel = createLogoLabel();
    JLabel titleLabel = createTitleLabel();

    panel.add(logoLabel);
    panel.add(titleLabel);
  }

  /**
   * Crea el label del logo cargando la imagen desde recursos con escalado de alta
   * calidad.
   * 
   * @return JLabel con el logo médico o texto de respaldo
   */
  private JLabel createLogoLabel() {
    try {
      BufferedImage originalImage = ImageIO.read(
          Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH)));

      // Crear imagen escalada con alta calidad
      BufferedImage scaledImage = createHighQualityScaledImage(originalImage, LOGO_SIZE, LOGO_SIZE);
      ImageIcon logoIcon = new ImageIcon(scaledImage);

      return new JLabel(logoIcon);

    } catch (IOException | NullPointerException e) {
      // Fallback: usar emoji si no se puede cargar la imagen
      JLabel fallbackLabel = new JLabel(FALLBACK_LOGO_TEXT);
      fallbackLabel.setFont(new Font("SansSerif", Font.PLAIN, LOGO_SIZE));
      return fallbackLabel;
    }
  }

  /**
   * Crea una imagen escalada con alta calidad usando renderizado optimizado.
   * 
   * @param originalImage Imagen original
   * @param targetWidth   Ancho objetivo
   * @param targetHeight  Alto objetivo
   * @return Imagen escalada con alta calidad
   */
  private BufferedImage createHighQualityScaledImage(BufferedImage originalImage,
      int targetWidth, int targetHeight) {
    BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = scaledImage.createGraphics();

    // Configurar renderizado de alta calidad
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    // Dibujar la imagen escalada
    g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
    g2d.dispose();

    return scaledImage;
  }

  /**
   * Crea el label del título con el formato apropiado.
   * 
   * @return JLabel con el título formateado
   */
  private JLabel createTitleLabel() {
    JLabel titleLabel = new JLabel(TITLE_TEXT);
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, TITLE_FONT_SIZE));
    return titleLabel;
  }

  /**
   * Obtiene el panel principal que contiene logo y título.
   * 
   * @return Panel configurado
   */
  public JPanel getPanel() {
    return panel;
  }
}
