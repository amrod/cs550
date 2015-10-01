package mediadb;

import java.io.IOException;

/** The interface for the media database.
 */

public interface MediaDatabase {

  /** Load the data file containing the database, or
      establish a connection with the data source.
      @param sourceName The name of the file (data source)
                        with the media entries
   */
  void loadData(String sourceName) throws IOException;

  /** Look up an entry by title.
      @param title The title to look up
      @return The Media object if found, null otherwise
   */
  Media lookupByTitle(String title);


  /** Add an entry.
      @param newMedia The Media object to be added
      @return none
   */
  void addEntry(Media newMedia);

  /** Remove an entry from the database.
      @param title The title of the media to be removed
      @return The object that is removed. If not in database, null is
              returned
   */
  Media removeEntry(String title);

  /** Method to save the database.
      pre:  The database has been loaded with data.
      post: Contents of database written back to the file in the
            form of the source file,
            modified is reset to false.
   */
  void save();
}
