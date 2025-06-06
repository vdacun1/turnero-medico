package edu.up.ui.sections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.up.ui.views.IView;

public class HeaderSection {
  private final JPanel panel;
  private final MenuSection menuSection;

  public HeaderSection(List<IView> allViews) {
    panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // sección izquierda: logo + título
    TitleSection title = new TitleSection();
    panel.add(title.getPanel(), BorderLayout.WEST);

    // sección derecha: menús automáticos
    menuSection = new MenuSection(allViews);
    panel.add(menuSection.getPanel(), BorderLayout.EAST);
  }

  public JPanel getPanel() {
    return panel;
  }

  public void setMenuAction(String menuName, Runnable action) {
    menuSection.setMenuAction(menuName, action);
  }
}
