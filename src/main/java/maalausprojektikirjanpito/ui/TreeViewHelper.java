package maalausprojektikirjanpito.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import javafx.scene.control.TreeItem;
import maalausprojektikirjanpito.domain.Layer;
import maalausprojektikirjanpito.domain.PaintProject;
import maalausprojektikirjanpito.domain.SubProject;
import static maalausprojektikirjanpito.domain.Utilities.categoriesAsStrings;
import static maalausprojektikirjanpito.domain.Utilities.factionsAsStrings;


public class TreeViewHelper {
    private UserInterface UI;

    public TreeViewHelper(UserInterface UI) {
        this.UI = UI;
    }
    

    

    public UserInterface getUI() {
        return UI;
    }

    public void setUI(UserInterface UI) {
        this.UI = UI;
    }
}
