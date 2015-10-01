# Assignment 1: Media Database

**Overview**: You have a sizeable collection of music (CD or Cassette) and videos (DVD or VHS),
and you want to develop a database to store, access and process this collection. You first need to
implement a class hierarchy for your media to help implementing the database. You also have a
data file (text file) that contains the information on your media (see details of the file format below.)
You want to be able to insert new media entries, look up a media by title or remove a media given
its title. You also want to save any changes to your data file. You will implement a media database
by the help of a Single Linked List. In other words, you will be storing your Media objects in a
Single Linked List. Use the SLList class we have covered in class. Add any methods to that class
that you find necessary.

**Details of the Implementation**: Your program first should load the data file into the media
database (Single Linked List). Then you should display a simple menu (GUI or text-based) which
asks the user what operation to perform and take action according to the user input (Partial code is
given in MDConsoleIO.java). Appropriate output should be displayed.

1. Media Class Hierarchy: Implement the classes as described in Chapter 1, Programming Project
5 (pg. 56, 57 of KW textbook.) Note that the Media, Audio, and Video classes are abstract classes.
You are required to include instance variables and methods given in Fig 1.12 and any other
variables/methods you find necessary for the classes Media, Video, DVD and VHS. For the
remaining classes (Audio, Cassette, CD), determine your own instance variables and methods. The
playMedia() method should display: ”Now playing: ” followed by the title and all other information
regarding that media. It should also increment the number of plays. Test all your classes before you
proceed.

2. Download MediaDatabase.java, LinkedListMediaDatabase.java and MDConsoleIO.java. You
will complete the class LinkedListMediaDatabase by implementing the methods in the Medi-
aDatabase.java.

3. Study the partially given MDConsoleIO.java and see how the user interface works. Complete
the methods that are left blank.

4. MediaDatabase Interface specifies which methods to be implemented by LinkedListMedia-Database, the parameters and what needs to be returned by each method. Follow the requirements. The following operations has to be implemented.

  1. `void loadData(String sourceName)`: Loads the data file into the media database. The name of the data file is given by sourceName.
  2. `void addEntry(Media newMedia)`: Adds a new entry. If the given title already exists in the
database, print an appropriate message and do not insert the new entry.
  3. `Media lookupByTitle(String title)`: Searches the database for the given title and returns the
associated media object. Returns null if title is not found.
  4. `Media removeEntry(String title)`: Removes the entry with the specified title from the
database and returns the associated media object or null if not in the database.
  5. `void save()`: Writes the contents of the database of entries to the same data file you used for
loading entries initially.

## Input/Output Requirements:

1. Input: The data file. In the text file, the specification for each media object starts with the
media type (one of ”Cassette”, ”CD”, ”VHS” or ”DVD”). This is followed by each data
member for that type. Each data member will be read from a separate line of the text file. For
array type data members, e.g. supportingActors, first the number of supportingActors is given
followed by that many supportingActors—one per line. For example, an example entry for a
DVD is as follows:

**DVD\
Matrix\
Keanu Reeves\
136\
0\
2 ///number of supporting actors\
Laurence Fishburne\
Carrie-Anne Moss\
Wachowski\
HD\
0 ///number of special features\
TRUE\
TRUE\
0 ///number of soundOptions**

Submit an example text file of your collection with your code submission.
1. Additional entries: If the user wants to insert new entries, each new entry is typed by the
user at the keyboard when requested.
2. Output: Each media entry will be written to the data file in the same format as the input.
