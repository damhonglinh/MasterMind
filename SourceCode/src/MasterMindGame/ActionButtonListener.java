package MasterMindGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Dam Linh - s3372757
 */
public class ActionButtonListener extends MouseAdapter {

    private InGameModel model = null;

    public ActionButtonListener(InGameModel model) {
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        BeautifulButton but = (BeautifulButton) e.getSource();
        if (but.getText().equalsIgnoreCase("Quit")) {
            model.quit();
        } else if (but.getText().equalsIgnoreCase("Continue")) {
            model.cont();
        } else if (but.getText().equalsIgnoreCase("Result")) {
            model.showResult();
        }
    }
}
