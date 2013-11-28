package MasterMindGame;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Dam Linh - s3372757
 */
public final class Token extends JLabel {

    public static final int SIZE = 30;
    public static final int GREEN = 1;
    public static final int BLUE = 2;
    public static final int SKY = 3;
    public static final int RED = 4;
    public static final int PURPLE = 5;
    public static final int PINK = 6;
    public static final int DARK_GREEN = 7;
    public static final int YELLOW = 8;
    public static final int BLACK = 9;
    public static final int WHITE = 10;
    public static final int NO_COLOR = 11;
    public static final int HIDDEN = 12;
    private ImageIcon colorIcon;
    private int color = 11;
    private boolean changeable = false;
    private boolean based = false;

    public Token() {
        this.setColor(NO_COLOR);
    }

    public Token(int color) {
        setColor(color);
        this.based = true;
        changeable = false;
    }

    public boolean isBased() {
        return based;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        URL url = null;
        switch (color) {
            case GREEN:
                url = this.getClass().getResource("Images/green.png");
                break;
            case BLUE:
                url = this.getClass().getResource("Images/blue.png");
                break;
            case RED:
                url = this.getClass().getResource("Images/red.png");
                break;
            case YELLOW:
                url = this.getClass().getResource("Images/yellow.png");
                break;
            case PURPLE:
                url = this.getClass().getResource("Images/purple.png");
                break;
            case DARK_GREEN:
                url = this.getClass().getResource("Images/darkgreen.png");
                break;
            case PINK:
                url = this.getClass().getResource("Images/pink.png");
                break;
            case SKY:
                url = this.getClass().getResource("Images/sky.png");
                break;
            case BLACK:
                url = this.getClass().getResource("Images/black.png");
                break;
            case WHITE:
                url = this.getClass().getResource("Images/white.png");
                break;
            case HIDDEN:
                url = this.getClass().getResource("Images/hidden.png");
                break;
            default:
                url = this.getClass().getResource("Images/nocolor.png");
        }
        colorIcon = new ImageIcon(url);
        this.setIcon(colorIcon);
    }

    public boolean isChangeable() {
        return changeable;
    }

    public void setChangeable(boolean changable) {
        this.changeable = changable;
    }
}
