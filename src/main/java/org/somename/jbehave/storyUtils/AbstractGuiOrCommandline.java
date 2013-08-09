package org.somename.jbehave.storyUtils;

import javax.swing.*;

/**
 * @author RossS
 *         Date: 9/08/13
 *         Time: 3:36 PM
 */
abstract class AbstractGuiOrCommandline extends JPanel
{
    public void start(String[] args)
    {
        if (args.length == 0)
        {
            createAndShowGUI();

        } else if (args.length == 1)
        {
            performAction(args[0]);
        } else
        {
            showCommandLineArgsError();
        }
    }

    protected abstract void showCommandLineArgsError();

    protected abstract void performAction(String fileNameOrPath);

    private void createAndShowGUI()
    {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FileChooserPanel fileChooserPanel = new FileChooserPanel();

        //Add content to the window.
        frame.add(fileChooserPanel);

        //Display the window.
        frame.setVisible(true);
        frame.pack();

        String fileNameOrPath = fileChooserPanel.getFileNameOrPath();

        frame.dispose();

        if (fileNameOrPath != null)
        {
            performAction(fileNameOrPath);
        }
    }
}
