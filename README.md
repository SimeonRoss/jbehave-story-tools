jbehave-story-tools
===================

Some very simple tools to speed up the JBehave BDD development process

- StubStories can be used either on the commandline or via a GUI (shown if no commandline arguments). It takes in the path of a JBehave Story file and writes out the step stubs. Usually used in conjunction with thucydides (https://github.com/thucydides-webtests/thucydides). I run this from my IDE so that I can then just copy and paste from the console. Useful when you have a large number of stories to be implemented in a block.
- TableBuilder can be used to create correctly formatted, pipe separated JBehave example tables from CSV files. Like StubStories it will show a GUI if no commandline options are provided. On the commandline it takes in either the path to a directory containing CSV files or the path to a single CSV file. The results are then written out into the same directory as .table files using the existing filename.