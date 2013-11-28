package MasterMindGame;

import javax.swing.ImageIcon;

/**
 *
 * @author Dam Linh - s3372757
 */
public class OkButton extends BeautifulButton {

    private int id;
    private boolean showable;

    public OkButton(int id) {
        this.id = id;
        Class classs = this.getClass();
        this.setIcon(new ImageIcon(classs.getResource("Images/ok.png")));
        this.setPressedIcon(new ImageIcon(classs.getResource("Images/okPressed.png")));
        this.setRolloverIcon(new ImageIcon(classs.getResource("Images/okRollOver.png")));
        this.setToolTipText("Confirm");
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
