package managers.interfaces;

import javax.swing.*;
import java.awt.*;

public interface ILookAndFeelManager {
    void updateDimension(Dimension d, int width, int height);
    boolean changeLookAndFeel(JFrame frame, String name);
    }
