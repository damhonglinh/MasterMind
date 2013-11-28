package MasterMindGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Dam Linh - s3372757
 */
public class SubmitButtonListener extends MouseAdapter {

    private TokenModel model = null;

    public SubmitButtonListener(TokenModel model) {
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof CancelButton) {
            CancelButton but = (CancelButton) e.getSource();
            model.cancel(but.getId());
        } else if (e.getSource() instanceof OkButton) {
            OkButton but = (OkButton) e.getSource();
            model.ok(but.getId());
        }
    }
}
