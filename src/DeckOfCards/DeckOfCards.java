package DeckOfCards;

import java.util.*;

public class DeckOfCards {

	public static void main(String[] args) {
		Deck deck = new Deck();
		ArrayList<String> cards = deck.getCards();
		distributeCards(cards, 4, 9);
	}

	// Method to distribute the cards among players
	public static void distributeCards(ArrayList<String> cards, int numOfPlayer, int numOfCards) {

		String[][] array2D = new String[numOfPlayer][numOfCards];
		int k = 0;

		for (int i = 0; i < numOfPlayer; i++) {
			System.out.println("\nPlayer " + (i+1));
			for (int j = 0; j < numOfCards; j++) {
				
				array2D[i][j] = cards.get(k);
				k++;
				System.out.println(array2D[i][j]);
			}
		}
	}
}

class Deck {

	private static final String suits[] = { "Club", "Diamond", "Heart", "Spade" };
	private static final String ranks[] = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen","King" };
	private final ArrayList<String> cards;

	//Creates a deck of all cards in sorted order
	public Deck() {
		cards = new ArrayList<String>();
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < ranks.length; j++) {
				this.cards.add(suits[i] + " - " + ranks[j]);
			}
		}

		// Shuffle the deck
		for (int i = 0; i < cards.size(); i++) {
			int index = (int) (Math.random() * cards.size());
			String temp1 = cards.get(i);
			String temp2 = cards.get(index);
			cards.set(i, temp2);
			cards.set(index, temp1);
		}

	}

	// Get the cards in the deck
	public ArrayList<String> getCards() {
		return cards;
	}
}

