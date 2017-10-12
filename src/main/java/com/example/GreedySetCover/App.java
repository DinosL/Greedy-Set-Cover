package com.example.GreedySetCover;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class App {
	// LinkedHashMap to retain order!!
	private static LinkedHashMap<String, Set<Integer>> sets = new LinkedHashMap<String, Set<Integer>>();
	private static HashSet<Integer> universe = new HashSet<Integer>();
	private static Set<Integer> intersection;

	public static void main(String[] args) {
		if(args.length != 1)
		{
			System.out.println("Usage Error! Specify filename as command line argument.");
			System.exit(1);
		}
		readFile(args[0]);

		printSets();

		greedySetCover();
	}

	private static void readFile(String filename) {
		int set = 1;
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = in.readLine()) != null) {
				String elements[] = line.split(",");
				Set<Integer> list = new HashSet<Integer>();
				for (String e : elements) {
					list.add(Integer.parseInt(e));
					universe.add(Integer.parseInt(e));
				}
				sets.put("S" + set, list);
				set++;
			}
			in.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	private static void greedySetCover() {
		ArrayList<String> coverKeys = new ArrayList<String>();

		while (!universe.isEmpty()) {
			int max = Integer.MIN_VALUE;
			String maxKey = "";
			Set<Integer> maxIntersection = new HashSet<Integer>();
			for (String key : sets.keySet()) {
				intersection = new HashSet<Integer>(sets.get(key));
				intersection.retainAll(universe);
				if (max < intersection.size()) {
					maxKey = key;
					max = intersection.size();
					maxIntersection = intersection;
				}
			}
			universe.removeAll(maxIntersection);
			coverKeys.add(maxKey);
		}
		System.out.println("Set-cover = " + coverKeys);
	}

	private static void printSets() {
		for (String key : sets.keySet()) {
			System.out.printf("%-3s = ",key);
			System.out.println(sets.get(key));
		}
		System.out.println("\nUniverse  = " + universe);
	}
}
