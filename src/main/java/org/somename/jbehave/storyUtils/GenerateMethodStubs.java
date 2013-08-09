package org.somename.jbehave.storyUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.System.exit;

@SuppressWarnings("serial")
public class GenerateMethodStubs extends JPanel {

    public static void main(String[] args)
    {
    	if (args.length == 0)
    	{
    		//Schedule a job for the event dispatch thread:
    		//creating and showing this application's GUI.
    		SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				//Turn off metal's use of bold fonts
    				UIManager.put("swing.boldMetal", Boolean.FALSE);
    				createAndShowGUI();
    			}
    		});
    		
    	} else if (args.length == 1)
    	{
    		// File path
    		StubStories stubber = new StubStories();
    		List<String> stubs = stubber.stub(readFile(args[0]));
            logOutStories(stubs);
    	} else 
    	{
    		System.out.println("Invalid command line arguments.");
    		System.out.println("To specify the story file to process using the graphical interface please don't specify any arguments.");
    		System.out.println("To specify the file to process from the command line just specify the complete file path as the only argument.");
    	}
    }

    private static void logOutStories(List<String> stubs)
    {
        for (String stub : stubs)
        {
            System.out.println(stub);
            System.out.println();
        }
    }

    private static List<String> readFile(String storyFilePath)
    {
        try
        {
            Path storyPath = new File(storyFilePath).toPath();
            return Files.readAllLines(storyPath, Charset.defaultCharset());

        } catch (IOException e) {
            throw new RuntimeException("Couldn't read from source file: ", e);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new GenerateMethodStubs());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public GenerateMethodStubs() {
        super(new BorderLayout());

        JFileChooser fc = new JFileChooser();
        
        // Note for later, may wish to consider multiple files or directories
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int returnVal = fc.showOpenDialog(GenerateMethodStubs.this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
        	StubStories stubber = new StubStories();
            List<String> stubs = stubber.stub(readFile(fc.getSelectedFile().getAbsolutePath()));
            logOutStories(stubs);
        }
        exit(0);
    }
}
