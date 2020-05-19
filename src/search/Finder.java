package search;

import java.util.*;

public class Finder {

    public FinderMethod method;

    void finder(Map<String, List<Integer>> invertedIndex, List<String> data, String[] value) {
        List<String> results = method.find(invertedIndex, data, value);

        if (results.size() > 0) {
            System.out.println(results.size() + " persons found:");
            for (String r : results) {
                System.out.println(r);
            }
        } else {
            System.out.println("No matching people found.");
        }
    }
}

interface FinderMethod {
    List<String> find(Map<String, List<Integer>> invertedIndex, List<String> data, String[] value);
}

class FindAnyMethod implements FinderMethod {

    @Override
    public List<String> find(Map<String, List<Integer>> invertedIndex, List<String> data, String[] value) {
        List<String> results = new ArrayList<>();

        for (int j = 0; j < value.length; j++) {
            if (invertedIndex.containsKey(value[j])) {
                for (Integer i : invertedIndex.get(value[j])) {
                    results.add(data.get(i));
                }
            }
        }
        return results;
    }
}

class FindAllMethod implements FinderMethod {

    @Override
    public List<String> find(Map<String, List<Integer>> invertedIndex, List<String> data, String[] value) {
        List<String> printResults = new ArrayList<>();

        Map<Integer, Integer> results = new HashMap<>();

        for (String s : value) {
            if (invertedIndex.containsKey(s)) {
                for (Integer i : invertedIndex.get(s)) {
                    if (!results.containsKey(i)) {
                        results.put(i, 1);
                    } else {
                        results.put(i, results.get(i) + 1);
                    }
                }
            }
        }

        for (var e : results.entrySet()) {
            if (e.getValue() > 1) {
                printResults.add(data.get(e.getKey()));
            }
        }

        if (printResults.size() == 0) {
            for (var r : results.entrySet()) {
                printResults.add(data.get(r.getKey()));
            }
        }
        return printResults;
    }
}

class FindNoneMethod implements FinderMethod {

    @Override
    public List<String> find(Map<String, List<Integer>> invertedIndex, List<String> data, String[] value) {
        List<String> results = new ArrayList<>();

        Set<Integer> exclude = new HashSet<>();

        for (int j = 0; j < value.length; j++) {
            if (invertedIndex.containsKey(value[j])) {
                exclude.addAll(invertedIndex.get(value[j]));
            }
        }

        for (int i = 0; i < data.size(); i++) {
            if (!exclude.contains(i)) {
                results.add(data.get(i));
            }
        }
        return results;
    }
}