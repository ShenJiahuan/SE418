package com.shenjiahuan.wordladder.play;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class WordLadder {
    private List<String> words = new ArrayList<>();
    public WordLadder(String filename) throws java.io.IOException, NullPointerException {
        InputStream fin = WordLadder.class.getResourceAsStream(filename);
        String path = getClass().getResource(filename).getPath();
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);

        String word;
        while ((word = buffReader.readLine()) != null) {
            words.add(word);
        }
    }

    private HashMap<String, ArrayList<String>> loadNear(String src, String dest) {
        HashMap<String, ArrayList<String>> near;
        List<String> sameLengthWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() == src.length()) {
                sameLengthWords.add(word);
            }
        }
        near = new HashMap<>();
        int wordsNum = sameLengthWords.size();
        for (int i = 0; i < wordsNum; ++i) {
            for (int j = i + 1; j < wordsNum; ++j) {
                if (isNear(sameLengthWords.get(i), sameLengthWords.get(j))) {
                    String word1 = sameLengthWords.get(i), word2 = sameLengthWords.get(j);
                    ArrayList<String> val1 = near.containsKey(word1) ? near.get(word1) : new ArrayList<>();
                    ArrayList<String> val2 = near.containsKey(word2) ? near.get(word2) : new ArrayList<>();
                    val1.add(word2);
                    val2.add(word1);
                    near.put(word1, val1);
                    near.put(word2, val2);
                }
            }
        }
        return near;
    }

    static ArrayList<String> combine(ArrayList<String> a, ArrayList<String> b) {
        ArrayList<String> result = new ArrayList<>(a);
        result.remove(result.size() - 1);
        result.addAll(b);
        return result;
    }

    private Object[] BFS(boolean forward, LinkedList<ArrayList<String>> queue, HashMap<String, ArrayList<String>> path1, HashMap<String, ArrayList<String>> path2, HashMap<String, ArrayList<String>> near) {
        LinkedList<ArrayList<String>> nextQueue = new LinkedList<>();
        ArrayList<String> result = null;
        boolean found = false;
        while (!queue.isEmpty()) {
            ArrayList<String> ladder = queue.poll();
            String elem = ladder.get(forward ? ladder.size() - 1: 0);
            if (path1.containsKey(elem)) {
                result = forward ? combine(ladder, path1.get(elem)) : combine(path1.get(elem), ladder);
                found = true;
                break;
            }
            if (near.get(elem) == null) {
                continue;
            }
            for (String word : near.get(elem)) {
                if (!ladder.contains(word)) {
                    ArrayList<String> newLadder = new ArrayList<>(ladder);
                    int pos = forward ? newLadder.size() : 0;
                    newLadder.add(pos, word);
                    nextQueue.add(newLadder);
                    if (!path2.containsKey(word)) {
                        path2.put(word, newLadder);
                    }
                }
            }
        }
        return new Object[]{found, nextQueue, result};
    }

    @SuppressWarnings("unchecked")
    public ArrayList<String> get(String src, String dest) {
        HashMap<String, ArrayList<String>> near;
        if (src.length() != dest.length()) {
            return null;
        }
        near = loadNear(src, dest);
        LinkedList<ArrayList<String>> queue1 = new LinkedList<>(), queue2 = new LinkedList<>();
        HashMap<String, ArrayList<String>> beginToElem = new HashMap<>();
        HashMap<String, ArrayList<String>> elemToEnd = new HashMap<>();

        ArrayList<String> srcArray = new ArrayList<>();
        ArrayList<String> destArray = new ArrayList<>();
        srcArray.add(src);
        destArray.add(dest);
        beginToElem.put(src, srcArray);
        elemToEnd.put(dest, destArray);
        queue1.add(srcArray);
        queue2.add(destArray);
        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            Object[] ret1 = BFS(true, queue1, elemToEnd, beginToElem, near);
            if ((boolean)ret1[0]) {
                return (ArrayList<String>) ret1[2];
            } else {
                queue1 = (LinkedList<ArrayList<String>>) ret1[1];
            }
            Object[] ret2 = BFS(false, queue2, beginToElem, elemToEnd, near);
            if ((boolean)ret2[0]) {
                return (ArrayList<String>) ret2[2];
            } else {
                queue2 = (LinkedList<ArrayList<String>>) ret2[1];
            }
        }
        return null;
    }

    static boolean isNear(String lhs, String rhs) {
        int count = 0;
        int length = lhs.length();
        for (int i = 0; i < length; ++i) {
            if (lhs.charAt(i) != rhs.charAt(i)) {
                ++count;
            }
        }
        return count == 1;
    }
}
