package maalausprojektikirjanpito.utilities;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class TabAndEnterEventHandler implements EventHandler<KeyEvent> {
    private KeyEvent recodedEvent;

    @Override
    public void handle(KeyEvent event) {
        if (recodedEvent != null) {
            recodedEvent = null;
            return;
        }
    }
}
