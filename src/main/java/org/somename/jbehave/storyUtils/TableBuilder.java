package org.somename.jbehave.storyUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TableBuilder extends AbstractGuiOrCommandline
{
    private static final String COLUMN_SEPARATOR = "|";

    public static void main(String[] args)
    {
        TableBuilder builder = new TableBuilder();
        builder.start(args);
    }

    @Override
    protected void performAction(String fileNameOrPath)
    {
        process(fileNameOrPath);
    }

    @Override
    protected void showCommandLineArgsError()
    {
        PrintStream out = System.out;

        out.println("Invalid argument");
        out.println("Required [directory or file path]");
        out.println();
    }

    public void process(String pathOrFilename)
    {
        File directoryOrFile = new File(pathOrFilename);
        if (directoryOrFile.isDirectory())
        {
            for (File file : directoryOrFile.listFiles(new CsvFilenameFilter()))
            {
                processFile(file.getAbsolutePath());
            }
        } else
        {
            processFile(pathOrFilename);
        }
    }

    private void processFile(String filename)
    {
        List<String> lines = readInFile(filename);

        int numberOfColumns = lines.get(0).split(",").length;

        String[][] table = new String[lines.size()][numberOfColumns];
        String[] outputTable = new String[lines.size()];
        int[] columnLengths = new int[numberOfColumns];

        fillTable(table, lines);
        determineColumnLengths(table, columnLengths);
        formatTable(outputTable, table, columnLengths);
        writeOutput(filename, outputTable);

        System.out.println("Processed " + filename);
    }

    private void writeOutput(String filename, String[] outputTable)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getNewFilename(filename)));

            for (String row : outputTable)
            {
                writer.write(row);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e)
        {
            throw new RuntimeException("Unable to write output file: ", e);
        }
    }

    private void formatTable(String[] outputTable, String[][] table, int[] columnLengths)
    {
        for (int i = 0; i < table.length; i++)
        {
            outputTable[i] = formatRow(table[i], columnLengths);
        }
    }

    private String formatRow(String[] strings, int[] columnLengths)
    {
        String row = "";

        for (int i = 0; i < strings.length; i++)
        {
            int padding = columnLengths[i] - strings[i].length() + 1; //+1 is for a trailing space

            if (i != 0)
            {
                row += COLUMN_SEPARATOR + " ";
            }
            row += strings[i];
            row += StringUtils.repeat(" ", padding);
        }
        return row;
    }

    private void determineColumnLengths(String[][] table, int[] columnLengths)
    {
        for (int i = 0; i < table.length; i++)
        {
            for (int j = 0; j < columnLengths.length; j++)
            {
                int cellLength = table[i][j].length();
                if (cellLength > columnLengths[j])
                {
                    columnLengths[j] = cellLength;
                }
            }
        }
    }

    private void fillTable(String[][] table, List<String> rows)
    {
        int i = 0;
        for (String row : rows)
        {
            // Remove any unnecessary space so we don't have tables that have long columns due to things like
            // 1           ,2,3,4
            String[] cells = row.split(",");
            for (int j = 0; j < cells.length; j++)
            {
                cells[j] = cells[j].trim();
            }
            table[i++] = cells;
        }
    }

    private List<String> readInFile(String filename)
    {
        try
        {
            Path storyPath = new File(filename).toPath();
            return Files.readAllLines(storyPath, Charset.defaultCharset());

        } catch (IOException e)
        {
            throw new RuntimeException("Couldn't read from source file: ", e);
        }
    }

    private String getNewFilename(String filename)
    {
        if (filename.endsWith(".csv"))
        {
            return filename.replace(".csv", ".table");
        } else
        {
            return filename.concat(".table");
        }
    }
}
