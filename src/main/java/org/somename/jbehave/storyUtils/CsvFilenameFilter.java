package org.somename.jbehave.storyUtils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author Simeon
 *         Date: 9/08/13
 *         Time: 7:59 PM
 */
class CsvFilenameFilter implements FilenameFilter
{
    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith("csv");
    }
}
