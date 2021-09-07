package DeckOfCards;

import java.util.*;

public class DeckOfCards {

	public static void main(String[] args) {
		Deck deck = new Deck();
		ArrayList<Card> cards = deck.getCards();
		distributeCards(cards, 4, 9);
	}

	// Method to distribute the cards among players
	public static void distributeCards(ArrayList<Card> cards, int numOfPlayer, int numOfCards) {

		int k = 0;

		for (int i = 1; i <= numOfPlayer; i++) {
			System.out.println("Player " + i);
			for (int j = 1; j <= numOfCards; j++) {
				System.out.println(cards.get(k));
				k++;
			}
		}
	}
}

class Card {

	private final String suit;
	private final String rank;

	public Card(String suit, String rank) {
		this.suit = suit;
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "Card [suit=" + suit + ", rank=" + rank + "]";
	}
}

class Deck {

	private static final String suits[] = { "Club", "Diamond", "Heart", "Spade" };
	private static final String ranks[] = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen","King" };
	private final ArrayList<Card> cards;

	//Creates a deck of all cards in sorted order
	public Deck() {
		cards = new ArrayList<Card>();
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < ranks.length; j++) {
				this.cards.add(new Card(suits[i], ranks[j]));
			}
		}

		// Shuffle the deck
		for (int i = 0; i < cards.size(); i++) {
			int index = (int) (Math.random() * cards.size());
			Card temp1 = cards.get(i);
			Card temp2 = cards.get(index);
			cards.set(i, temp2);
			cards.set(index, temp1);
		}

	}

	// Get the cards in the deck
	public ArrayList<Card> getCards() {
		return cards;
	}
}

