package MasterMindGame;

import javax.swing.ImageIcon;

/**
 *
 * @author Dam Linh - s3372757
 */
public class ActionButton extends BeautifulButton {

    public ActionButton(String text) {
        super(text);
        Class classs = this.getClass();
        setIcon(new ImageIcon(classs.getResource("Images/button.png")));
        setPressedIcon(new ImageIcon(classs.getResource("Images/buttonPressed.png")));
        setRolloverIcon(new ImageIcon(classs.getResource("Images/buttonRollOver.png")));
    }
}
