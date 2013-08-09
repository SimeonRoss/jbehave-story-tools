package org.somename.jbehave.storyUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Simeon
 *         Date: 9/08/13
 *         Time: 7:42 PM
 */
class FileChooserPanel extends JPanel {

    private String fileNameOrPath;

    public FileChooserPanel()
    {
        super(new BorderLayout());

        JFileChooser fc = new JFileChooser();

        // Note for later, may wish to consider multiple files or directories
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int returnVal = fc.showOpenDialog(FileChooserPanel.this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            fileNameOrPath = fc.getSelectedFile().getAbsolutePath();
        }
    }

    public String getFileNameOrPath()
    {
        return fileNameOrPath;
    }
}
