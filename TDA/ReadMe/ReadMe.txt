Test Data Analyzer by Group A

Outline

1) License information
2) General Information
3) Manual

1) ISCL

Copyright (c) 2017, SWT-Lab Group A*

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.


*Members: Frank Keßler, Tobias Schwartz, Andreas Köllner, Simon Meyer, Jan Martin

2) Gernal Information
In the following we describe this software as TDA.
XML files are the files provided by medatixx GmbH & Co. KG and each file will be parsed and displayed as one TestRun.
One TestRun consists of several UnitTests testing different classes.

TDA shall help visualize all this information and show possible correlations between different TestRuns per class.

3) 	How to use TDA:

3.1) 	Loading XML Files
	To Load XML Files into the TDA there are two ways:
		a) 	On startup click the folder button to select a folder and parse the folder and all underlying folders
			in recursive strategy or click the file button to select one or more specific XML files to be parsed.
		b) 	On the Menubar click File -> Open folder or Open file
	If successful you will then see the sidebar being updated with information.
	
3.2) 	Displaying one TestRun in the class Table
	To display a testrun in the table you have to click on a testrun shown in the sidebar with the TestRun - Tab active.
	It will automatically sort all tested classes by this testrun by its failure percentage(fp) and highlight classes
	red with a fp higher than 75% and yellow with a fp higher than 50%.

3.3)	Display the change of a classes failure percentage over all testruns.
	To display the change of a classes fp there are two ways:
		a) Select the Classes - Tab in the sidebar and search in the packages for your desired class and click on it.
		b) Select your desired class in the table containing all classes of one testrun.
		
	Both actions will result in a Chart being shown in the Chart - Tab on the main window.
	You can add several charts for comparison.