package boggle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Trie {
	private boolean wordEnd;
	private Map<Character, Trie> kids = new HashMap<>();
	
	public void insert(String word) {
		if (word.length() == 0) {
			wordEnd = true;
		} else {
			char first = word.charAt(0);
			String rest = word.substring(1);
			if (kids.containsKey(first)) {
				kids.get(first).insert(rest);
			} else {
				kids.put(first, new Trie());
				kids.get(first).insert(rest);
			}
		}
	}
	
	private Trie traverse(String path) {
		if (path.length() == 0) {
			return this;
		}
		char first = path.charAt(0);
		String rest = path.substring(1);
		if (!kids.containsKey(first)) {
			return null;
		}
		return kids.get(first).traverse(rest);
	}
	
	public boolean hasPath(String word) {
		Trie node = this.traverse(word);
		return node != null;
	}
	
	public boolean contains(String word) {
		Trie node = this.traverse(word);
		if (node == null) {
			return false;
		}
		return node.wordEnd;
	}
	
	public static void main(String[] args) throws Exception {
		Trie root = new Trie();
		Scanner scan = new Scanner(new FileInputStream("words.txt"));
		while (scan.hasNext()) {
			root.insert(scan.next());
		}
		
		System.out.println(root.contains("dog"));
		System.out.println(root.contains(""));
		System.out.println(root.contains("cat"));
		System.out.println(root.contains("petrichor"));
		System.out.println(root.contains("asdfasdfasdf"));
	}
}
