package org.somename.jbehave.storyUtils;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StubStories
{
	private static final String GIVEN = "given";
	private static final String WHEN = "when";
	private static final String THEN = "then";
	private static final String AND = "and";

    private static final Pattern PARAMETER_PATTERN = Pattern.compile("(<.*?>)");
	
    public List<String> stub(List<String> storyLines)
    {
        Set<String> usedStoryTags = new TreeSet<String>();
        List<String> methods = new ArrayList<String>();

        String lastTag = null;
        for (String line : storyLines)
        {
        	String lowerCaseLine = line.toLowerCase();
            String method = null;
        	
        	if (lowerCaseLine.startsWith(GIVEN))
        	{
        		method = createMethodIfNew(GIVEN, GIVEN.length(), line, usedStoryTags);
        		lastTag = GIVEN;
        	} else if (lowerCaseLine.startsWith(WHEN))
        	{
                method = createMethodIfNew(WHEN, WHEN.length(), line, usedStoryTags);
        		lastTag = WHEN;
        	} else if (lowerCaseLine.startsWith(THEN))
        	{
                method = createMethodIfNew(THEN, THEN.length(),line, usedStoryTags);
        		lastTag = THEN;
        	} else if (lowerCaseLine.startsWith(AND))
        	{
                method = createMethodIfNew(lastTag == null ? "unknown" : lastTag, AND.length(), line, usedStoryTags);
        	}
            if (method != null)
            {
                methods.add(method);
            }
        }

        return methods;
    }
    
    private String createMethodIfNew(String methodTag, int tagLength, String line, Set<String> usedMethods)
    {
    	String restOfLine = line.substring(tagLength + 1);
    	String annotation = String.format("@%s(\"%s\")", WordUtils.capitalize(methodTag), restOfLine);
    	
    	if (!usedMethods.contains(annotation))
    	{
            usedMethods.add(annotation);

            String methodName = String.format("public void %s(%s)\n{\n}", sanitizeAndCreateMethodName(methodTag, restOfLine), getMethodParameters(restOfLine));

    		return String.format("%s\n%s", annotation, methodName);
    	} else
        {
            return null;
        }
    }

    private String getMethodParameters(String restOfLine)
    {
        Matcher parametersMatcher = PARAMETER_PATTERN.matcher(restOfLine);
        String parameters = "";

        while (parametersMatcher.find())
        {
            if (parameters.length() != 0)
            {
                parameters += ", ";
            }
            parameters += "String " + parametersMatcher.group();
        }

        return parameters;
    }

    private String sanitizeAndCreateMethodName(String methodTag, String restOfMethodName)
    {
        String methodName = WordUtils.capitalize(restOfMethodName.replaceAll("[^A-Za-z0-9 ]", "")).replaceAll(" ", "");
        return methodTag + methodName;
    }
}
