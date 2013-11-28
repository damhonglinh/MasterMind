package MasterMindGame;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Dam Linh - s3372757
 */
public class BeautifulPanel extends JPanel {

    private Image background;

    public BeautifulPanel() {
        URL url = this.getClass().getResource("Images/bg.png");
        background = new ImageIcon(url).getImage();
    }

    public BeautifulPanel(URL url) {
        background = new ImageIcon(url).getImage();
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public void setSource(URL url) {
        background = new ImageIcon(url).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}
