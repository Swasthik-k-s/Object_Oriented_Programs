package DeckOfCards;

import java.util.ArrayList;

public class Deck {

	private static final String suits[] = { "Club", "Diamond", "Heart", "Spade" };
	private static final String ranks[] = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen","King" };
	private ArrayList<String> cards;

	/**
	 * Creates a deck of all cards in sorted order
	 * @return //return a list of cards
	 */
	public ArrayList<String> generateDeck() {
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
		return cards;
	}
}
