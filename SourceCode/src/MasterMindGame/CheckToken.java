package MasterMindGame;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Dam Linh - s3372757
 */
public class CheckToken extends JLabel {

    static final int SIZE = 13;
    static final int BLACK = 1;
    static final int WHITE = 2;
    static final int NO_COLOR = 3;
    private ImageIcon colorIcon;
    private int color = 3;
    private boolean changable = false;

    public CheckToken() {
        URL url = this.getClass().getResource("Images/nocolorcheck.png");
        colorIcon = new ImageIcon(url);
        this.setIcon(colorIcon);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        URL url;
        switch (color){
            case BLACK:
                url = this.getClass().getResource("Images/blackcheck.png");
                break;
            case WHITE:
                url = this.getClass().getResource("Images/whitecheck.png");
                break;
            default:
                url = this.getClass().getResource("Images/nocolorcheck.png");
        }
        colorIcon = new ImageIcon(url);
        this.setIcon(colorIcon);
    }
    
    public boolean isChangable() {
        return changable;
    }

    public void setChangable(boolean changable) {
        this.changable = changable;
    }
}
