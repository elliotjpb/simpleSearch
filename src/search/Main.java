package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        SearchEngine<String> peopleSearchEngine = new PeopleSearchEngine();
        peopleSearchEngine.loadData(args);
        peopleSearchEngine.start();
    }
}