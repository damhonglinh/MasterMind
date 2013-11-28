package MasterMindGame;

import javax.swing.ImageIcon;

/**
 *
 * @author Dam Linh - s3372757
 */
public class CancelButton extends BeautifulButton {

    private int id;
    private boolean showable;

    public CancelButton(int id) {
        this.id = id;
        Class classs = this.getClass();
        this.setIcon(new ImageIcon(classs.getResource("Images/cancel.png")));
        this.setPressedIcon(new ImageIcon(classs.getResource("Images/cancelPressed.png")));
        this.setRolloverIcon(new ImageIcon(classs.getResource("Images/cancelRollOver.png")));
        this.setToolTipText("Clear");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isShowable() {
        return showable;
    }

    public void setShowable(boolean showable) {
        this.showable = showable;
    }
}
