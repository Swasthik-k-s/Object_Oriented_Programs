package DeckOfCards;

import java.util.*;

public class DeckOfCards {

	public static void main(String[] args) {
		Deck deck = new Deck();
		ArrayList<String> cards = deck.generateDeck();
		distributeCards(cards, 4, 9);
	}

	
	/**
	 * Method to distribute the cards among players
	 * @param cards //List containing all the cards
	 * @param numOfPlayer //Number of players to divide the cards among
	 * @param numOfCards //Number of cards each player gets
	 */
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