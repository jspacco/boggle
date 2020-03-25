package boggle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * You can play along on REPL here:
 * 
 * https://repl.it/@jspacco/Boggle-template
 * 
 * @author jspacco
 *
 */
public class Board {
	private Die[] dice;
	private Random rand;
	private String[][] grid;
	private Trie trie;
	private Set<String> words = new TreeSet<>();
	
	
	public String getLetter(int row, int col) {
		if (row < 0 || row >= 4 || col < 0 || col >= 4) {
			throw new IndexOutOfBoundsException(
					String.format("Row %d Col %d out of bounds", row, col));
		}
		return grid[row][col];
	}

	
	public Board(Random rand) {
		this.rand = rand;
		initDice();
		grid = new String[4][4];
		
		// TODO: refactor
		for (int i=0; i<dice.length; i++) {
			int loc = rand.nextInt(16);
			Die tmp = dice[i];
			dice[i] = dice[loc];
			dice[loc] = tmp;
		}
		int index = 0;
		for (int r=0; r<4; r++) {
			for (int c=0; c<4; c++) {
				grid[r][c] = dice[index].roll(rand);
				index++;
			}
		}
		
		// TODO: refactor
		makeTrie("words.txt");
		
		// helper method???
		for (int r=0; r<4; r++) {
			for (int c=0; c<4; c++) {
				findWords("", r, c, new HashSet<String>(), words);
			}
		}
		
	}
	
	private void makeTrie(String filename) {
		trie = new Trie();
		Scanner scan;
		try {
			scan = new Scanner(new FileInputStream(filename));
			while (scan.hasNext()) {
				trie.insert(scan.next().toUpperCase());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String coord(int row, int col) {
		return row + "-" + col;
	}
	
	private void findWords(String prefix, int row, int col, Set<String> visited, Set<String> words) {
		String word = prefix + getLetter(row, col);
		if (!trie.hasPath(word)) {
			return;
		}
		if (trie.contains(word) && word.length() > 2) {
			words.add(word);
		}
		visited.add(coord(row, col));
		for (int r=row-1; r<=row+1; r++) {
			for (int c=col-1; c<=col+1; c++) {
				if (r < 0 || r > 3 || c < 0 || c > 3) {
					continue;
				}
				if (r == row && c == col) {
					continue;
				}
				Set<String> copy = new HashSet<>(visited);
				findWords(word, r, c, copy, words);
			}
		}
	}
	
	public Set<String> getWords() {
		return this.words;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (int r=0; r<4; r++) {
			for (int c=0; c<4; c++) {
				buf.append(getLetter(r, c));
				buf.append(" ");
			}
			buf.append("\n");
		}
		return buf.toString();
	}

	private void initDice() {
		dice=new Die[] {
				new Die("J", "B", "O", "A", "B", "O"),
				new Die("H", "W", "V", "E", "T", "R"),
				new Die("M", "U", "O", "C", "T", "I"),
				new Die("N", "E", "E", "G", "A", "A"),
				new Die("C", "S", "O", "A", "P", "H"),
				new Die("I", "T", "E", "S", "O", "S"),
				new Die("E", "E", "H", "N", "W", "G"),
				new Die("A", "W", "T", "O", "T", "O"),
				new Die("I", "U", "N", "E", "E", "S"),
				new Die("A", "F", "P", "S", "K", "F"),
				new Die("E", "Y", "L", "D", "R", "V"),
				new Die("Z", "H", "R", "L", "N", "N"),
				new Die("Y", "T", "L", "E", "T", "R"), 
				new Die("I", "T", "S", "T", "D", "Y"),
				new Die("U", "N", "H", "I", "M", "QU"),
				new Die("D", "R", "X", "I", "L", "E")
		};
	}

	
	public static void main(String[] args) {
		Random r = new Random(1);
		//Random r = new Random();
		
		Board board = new Board(r);
		
		System.out.println(board);
		
		System.out.println(board.getWords().size());
		
		for (String s : board.getWords()) {
			System.out.println(s);
		}
	}


}