package org.somename.jbehave.storyUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author RossS
 *         Date: 9/08/13
 *         Time: 11:54 AM
 */
public class StubStoriesTest
{

    private StubStories stubber;

    @Before
    public void setup()
    {
        stubber = new StubStories();
    }

    @Test
    public void testNumberOfStoriesIsWrite()
    {
        String aStory = "Given the month is <month> and my name is <name>";
        String anotherStory = "When I click a button";

        List<String> results = stubber.stub(Arrays.asList(aStory));
        assertThat(results).hasSize(1);

        results = stubber.stub(Arrays.asList(aStory, anotherStory));
        assertThat(results).hasSize(2);
    }

}
