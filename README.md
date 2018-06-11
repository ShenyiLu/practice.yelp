YelpStore Practice
==========================

### This is not a required assignment.

In this practice assignment, you will practice using the following:

- [Data structures in Java 8](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/package-summary.html)
- [JSON](https://www.json.org/) Parsing using the [GSON library](https://github.com/google/gson)

For this practice assignment, you will design and implement a data structure to store the data provided in the [Yelp Academic Dataset](https://www.yelp.com/academic_dataset). The Yelp dataset contains information about businesses, information about users, and reviews, by users, of businesses in the dataset.

If you are new to San Francisco, I recommend you begin by creating a [Yelp](https://www.yelp.com/) account and exploring the web site!

This assignment is advanced and covers material that we will discuss during the first two weeks of class. If you want a challenge, you should try this assignment. If you find some of the concepts difficult, for example JSON parsing, do not worry! Try your best using the information available online and rest assured that we will cover these topics and you will have plenty of time to ask questions during class.

If this were a graded assignment, your grade will depend heavily on the *design* of your solution. A main goal of this assignment is to design an *efficient* data structure.

### Project Structure

To successfully execute the tests, **you will need to create** three additional directories:

- `results` - This is where the test suite will save the results produced by your code. You must create this directory manually.
-  `input` - Download and unzip the directory from [http://www.cs.usfca.edu/~srollins/cs601/input.zip](http://www.cs.usfca.edu/~srollins/cs601/input.zip). This directory contains all of the input files required by the test suite. Do not alter any of the files in this directory.
-  `output` - Download and unzip the directory from [http://www.cs.usfca.edu/~srollins/cs601/output.zip](http://www.cs.usfca.edu/~srollins/cs601/output.zip). This directory contains the output that your results will be compared against. Do not alter any of the files in this directory.

### Requirements

You are required to implement the methods in the class `practice.data.YelpStore`. You must implement a no-parameter constructor, and the methods `addUser`, `addReview`, `addBusiness`, `toString`, and `printToFile`. The Javadoc in the skeleton code describes how each method should behave. You may create additional supporting classes. My solution uses six additional classes. 

Note that not all of these methods may be thoroughly tested by the basic test cases provide. You are encouraged to implement additional tests cases.

:warning: You may not make any modifications to the API or the test cases provided.

:warning: The only third-party library you may use for this project is GSON. The pom.xml file provided is configured to allow you to use the GSON library.

The key element of this lab is the design of a data structure that will store all of the user information, business information, and reviews. Each review is of a specific business and authored by a specific user. The YelpStore will store all information, but may use multiple data structures for this purpose.

Think carefully about **efficiency**. Some specific considerations include avoiding storing duplicate data where possible and designing your data structure to optimize access to the data. Though you do not need to implement the get methods, look carefully at how the data will be accessed ensure efficiency.

Refer to the guide for [setting up your development environment](https://github.com/CS601-F18/notes/blob/master/admin/devenvironment.md) as well as the [easy practice assignment](https://github.com/CS601-F18/practice.easy) as necessary.

### Submission Requirements

1. Use the following link to create your private github repository for this assignment. The repository will be seeded with the skeleton code, test cases, and input files. [Yelp Practice](https://classroom.github.com/a/OakTxoPn)
2. For full credit, make sure to follow all [Style Guidelines](https://github.com/CS601-F18/notes/blob/master/admin/style.md). Points will be deducted for each violation.

### Grading Rubric

This is an ungraded assignment. For most assignments, the rubric will specify the number of points awarded for passing each set of test cases, the number of points awarded for passing additional test cases not provided, the number of points for design, and the number of points awarded for following the style guidelines as described above.

Partial credit may be awarded for partial functionality and/or partially correct design or style elements.

### Academic Dishonesty

Any work you submit is expected to be your own original work. If you use any web resources in developing your code you are required to cite those resources. The only exception to this rule is code that is posted on the class website. The URL of the resource you used in a comment in your code is fine. If I google even a single line of uncited code and find it on the internet you may get a 0 on the assignment or an F in the class. You may also get a 0 on the assignment or an F in the class if your solution is at all similar to that of any other student.