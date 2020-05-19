package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public interface SearchEngine<T> {
    void start();
    void search();
    void printData();
    void loadData(T[] source);
}

class PeopleSearchEngine implements SearchEngine<String> {

    private List<String> data;
    private Scanner scanner;
    private Map<String, List<Integer>> invertedIndex;

    public PeopleSearchEngine() {
        this.data = new ArrayList<>();;
        this.scanner = new Scanner(System.in);
        this.invertedIndex = new HashMap<>();
    }

    @Override
    public void start() {

        String menu = "=== Menu === \n" +
                "1. Find a person \n" +
                "2. Print all people\n" +
                "0. Exit";

        boolean exit = false;
        while (!exit) {
            System.out.println(menu);
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    search();
                    break;
                case "2":
                    printData();
                    break;
                case "0":
                    System.out.println("Bye!");
                    exit = true;
                    break;
                default:
                    System.out.println("\nIncorrect option! Try again.");
            }
            System.out.println();
        }
    }

    @Override
    public void search() {

        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String option = scanner.nextLine().toLowerCase();
        System.out.println("\nEnter a name or email to search all suitable people.");
        String[] value = scanner.nextLine().toLowerCase().split(" ");
        Finder f = new Finder();
        switch (option) {
            case "all":
                f.method = new FindAllMethod();
                break;
            case "any":
                f.method = new FindAnyMethod();
                break;
            case "none":
                f.method = new FindNoneMethod();
                break;
            default:
                System.out.println("\n Invalid matching strategy chose: ALL, ANY, NONE");
        }
        f.finder(invertedIndex, data, value);
    }

    @Override
    public void printData() {

        System.out.println("\n=== List of people ===");
        for (String result : data) {
            System.out.println(result);
        }
    }

    @Override
    public void loadData(String[] source) {

        String filePath = source[1];

        try (Scanner read = new Scanner(new File (filePath))) {
            int line = 0;
            while (read.hasNextLine()) {
                String currentRecord = read.nextLine();
                data.add(currentRecord);
                String[] word = currentRecord.toLowerCase().split("\\s+");
                for (String w : word) {
                    invertedIndex.computeIfAbsent(w, k -> new ArrayList<>());
                    invertedIndex.get(w).add(line);
                }
                line++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: file at " + filePath + " not found");
        }
    }
}
