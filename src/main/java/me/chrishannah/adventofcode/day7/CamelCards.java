package me.chrishannah.adventofcode.day7;

import me.chrishannah.adventofcode.AOCUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CamelCards {

    private record Hand(String cards, Integer bid, Integer baseRank, boolean jokerHand) implements Comparable<Hand> {
        @Override
        public int compareTo(Hand h) {
            if (this.baseRank.compareTo(h.baseRank) != 0) {
                return this.baseRank.compareTo(h.baseRank);
            }

            for (int i = 0; i < cards.length(); i++) {
                var c1 = cards.substring(i, i + 1);
                var c2 = h.cards.substring(i, i + 1);
                var compare = valueOfCard(c1).compareTo(valueOfCard(c2));
                if (compare != 0) {
                    return compare;
                }
            }

            return 0;
        }

        private Integer valueOfCard(String card) {
            try {
                var intValue = Integer.parseInt(card);
                return intValue;
            } catch (NumberFormatException ignored) {
            }

            switch (card) {
                case "T":
                    return 10;
                case "J":
                    if (jokerHand) {
                        return 1;
                    }
                    return 11;
                case "Q":
                    return 12;
                case "K":
                    return 13;
                case "A":
                    return 14;
                default:
                    return 0;
            }
        }

    }

    private static int calculateBaseRank(String cards, boolean withJokers) {
        var cardCount = new HashMap<String, Integer>();
        var jokerCount = 0;
        for (int i = 0; i < cards.length(); i++) {
            String card = cards.substring(i, i + 1);
            if (withJokers && card.equals("J")) {
                jokerCount++;
                continue;
            }
            if (cardCount.containsKey(card)) {
                cardCount.put(card, cardCount.get(card) + 1);
            } else {
                cardCount.put(card, 1);
            }
        }
        var uniqueCards = cardCount.keySet().stream().count();

        // Five of a kind
        if (uniqueCards < 2) {
            return 7;
        }

        // Four of a kind
        if (uniqueCards == 2) {
            for (Integer count : cardCount.values()) {
                if (count == 1) {
                    return 6;
                }
            }
        }

        // Full house
        if (uniqueCards == 2) {
            for (Integer count : cardCount.values()) {
                if (count + jokerCount == 3) {
                    return 5;
                }
            }
        }

        // Three of a kind
        for (Integer count : cardCount.values()) {
            if (count + jokerCount == 3) {
                return 4;
            }
        }

        // Two pair
        if (uniqueCards == 3) {
            var pairs = 0;
            for (Integer count : cardCount.values()) {
                if (count == 2) {
                    pairs++;
                }
            }
            if (pairs == 2 || (pairs == 1 && jokerCount > 0)) {
                return 3;
            }
        }

        // One pair
        if (uniqueCards == 4) {
            return 2;
        }

        // High card
        if (uniqueCards == 5) {
            return 1;
        }

        return 0;
    }

    private static long calculateTotalWinnings() {
        var reader = AOCUtils.readFile("src/main/resources/day-7-input.txt");
        var hands = reader.lines().map(line -> {
            var data = line.split(" ");
            var cards = data[0];
            var baseRank = calculateBaseRank(cards, false);
            var hand = new Hand(cards, Integer.parseInt(data[1]), baseRank, false);
            return hand;
        }).toList();


        var sorted = hands.stream().sorted().collect(Collectors.toList());
        var totalWinnings = 0;
        for (int i = 0; i < sorted.size(); i++) {
            var hand = sorted.get(i);
            var winnings = (i + 1) * hand.bid;
            totalWinnings += winnings;
        }
        return totalWinnings;
    }

    private static long calculateTotalWinningsWithJokers() {
        var reader = AOCUtils.readFile("src/main/resources/day-7-input.txt");
        var hands = reader.lines().map(line -> {
            var data = line.split(" ");
            var cards = data[0];
            var baseRank = calculateBaseRank(cards, true);
            var hand = new Hand(cards, Integer.parseInt(data[1]), baseRank, true);
            return hand;
        }).toList();


        var sorted = hands.stream().sorted().collect(Collectors.toList());
        var totalWinnings = 0;
        for (int i = 0; i < sorted.size(); i++) {
            var hand = sorted.get(i);
            var winnings = (i + 1) * hand.bid;
            totalWinnings += winnings;
        }
        return totalWinnings;
    }

    public static void main(String[] args) {
        // Part 1
        var start = new Date().getTime();
        var answer = calculateTotalWinnings();
        var stop = new Date().getTime();
        System.out.println(STR. "Part 1 | Answer: \{ answer } Time: \{ stop - start }ms" );

        // Part 2
        start = new Date().getTime();
        answer = calculateTotalWinningsWithJokers();
        stop = new Date().getTime();
        System.out.println(STR. "Part 2 | Answer: \{ answer } Time: \{ stop - start }ms" );
    }
}



