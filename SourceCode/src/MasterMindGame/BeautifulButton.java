package MasterMindGame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;

/**
 *
 * @author Dam Linh - s3372757
 */
public abstract class BeautifulButton extends JButton {

    public BeautifulButton() {
        this(null);
    }

    public BeautifulButton(String text) {
        super(text);
        setHorizontalTextPosition(CENTER);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Font font = new Font(Font.DIALOG,Font.BOLD,12);
        Color myColor = new Color(80,80,80);
        setForeground(myColor);
        setFont(font);
    }
}
