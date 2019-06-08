import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.*;

/**
 * https://school.thoughtworks.cn/learn/program-center/subjectiveQuiz/index.html#/student/program/195/task/3110/assignment/4213/quiz/1299
 */
public class Poker{
    public static Map<String, String> map = new HashMap<>();
    public static int[] pair = {0, 0,0};
    public static int[] pairOne = {0, 0,0,0};
    public String compare(String black, String white) {
        int b = check(black);
        int w = check(white);
        map.put("1", "High Card");
        map.put("2", "Pair");
        map.put("3", "Two Pairs");
        map.put("4", "Three of a Kind");
        map.put("5", "Straight");
        map.put("6", "Flush");
        map.put("7", "Full House");
        map.put("8", "Four of a kind");
        map.put("9", "Straight flush");
        map.put("10", "T");
        map.put("11", "J");
        map.put("12", "Q");
        map.put("13", "K");
        map.put("14", "Ace");
        String winner;
        if (b > w) {
            winner = "Black";
            return winner + " wins - " + map.get(String.valueOf(b));
        } else if (b < w) {
            winner = "White";
            return winner + " wins - " + map.get(String.valueOf(b));
        } else {
            ArrayList<Integer> bs = splitNum(black);
            ArrayList<Integer> ws = splitNum(white);
            Collections.sort(bs);
            Collections.sort(ws);

            //判断同花顺--判断最大牌大小
            //判断顺子
            if (b == 9 || b==5) {
                int r1 = bs.get(bs.size() - 1);
                int r2 = ws.get(ws.size() - 1);
                return cmpPoint(r1, r2, b);
            }

            //判断铁支--判断总点数
            //判断葫芦 --判断总点数
            //判断三条
            if (b == 8 || b==7 || b==4) {
                int bSum = sumPoint(bs);
                int wSum = sumPoint(ws);
                return cmpPoint(bSum, wSum, b);
            }

            //判断两对
            // 比较大对子的大小，若相同，比较小对子的大小，若还相同，比较单张牌的大小，若还相同，则为平局。
            if (b == 3) {
                twoPairs(white);
                int[] wpair = new int[3];
                wpair[0] = pair[0];
                wpair[1] = pair[1];
                wpair[2] = pair[2];


                twoPairs(black);
                int[] bpair = new int[2];
                bpair[0] = pair[0];
                bpair[1] = pair[1];
                bpair[2] = pair[2];


                if (bpair[1] > wpair[1]) {
                    return "Black wins - " + map.get(String.valueOf(b));
                } else if (bpair[1] < wpair[1]) {
                    return "White wins - " + map.get(String.valueOf(b));
                } else {
                    if (bpair[0] > wpair[0]) {
                        return "Black wins - " + map.get(String.valueOf(b));
                    } else if (bpair[0] < wpair[0]) {
                        return "White wins - " + map.get(String.valueOf(b));
                    } else {
                        if (bpair[2] > wpair[2]) {
                            return "Black wins - " + map.get(String.valueOf(b));
                        } else if (bpair[2] < wpair[2]) {
                            return "White wins - " + map.get(String.valueOf(b));
                        } else {
                            return "Tie";
                        }
                    }
                }
            }
            //判断对子
            if (b == 2) {
                pair(black);
                int douB = pairOne[0];

                pair((white));
                int douW = pairOne[0];

                if (douB > douW) {
                    return "Black wins - " + map.get(String.valueOf(b));
                } else if (douB < douW) {
                    return "White wins - " + map.get(String.valueOf(b));
                } else {
                    int countB = 1;
                    int countW = 1;
                    for (int i = 0; i < bs.size(); i++) {
                        if (countB <= 2 && bs.get(i).equals(douB)) {
                            bs.remove(i);
                            countB++;
                        }
                        if (countW <= 2 && ws.get(i).equals(douW)) {
                            ws.remove(i);
                            countW++;
                        }
                    }
                    Collections.sort(bs);
                    Collections.sort(ws);

                    for (int i = bs.size() - 1; i > 0; i--) {
                        if (bs.get(i) > ws.get(i)) {
                            return "Black wins - " + map.get(String.valueOf(b));
                        } else if (bs.get(i) < ws.get(i)) {
                            return "White wins - " + map.get(String.valueOf(b));
                        }
                    }
                    return "Tie";
                }


            }

            //判断散牌
            //判断同花
            if (b == 1 || b==6) {
                for (int i = bs.size() - 1; i > 0; i--) {
                    if (bs.get(i) > ws.get(i)) {
                        String card = bs.get(i) >= 10 ? map.get(String.valueOf(bs.get(i))) : String.valueOf(bs.get(i));
                        if (b == 6) {
                            return  "Black wins - " + map.get(String.valueOf(b));
                        }
                        return"Black wins - high card: " + card;
                    } else if (bs.get(i) < ws.get(i)) {
                        String card = ws.get(i) >= 10 ? map.get(String.valueOf(ws.get(i))) : String.valueOf(ws.get(i));
                        if (b == 6) {
                            return  "White wins - " + map.get(String.valueOf(b));
                        }
                        return "White wins - high card: " + card;
                    }
                }
                return "Tie";
            }



        }
        return "";

    }

    /**
     * 牌总点数
     * @param list
     * @return
     */
    private int sumPoint(ArrayList<Integer> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum;
    }

    private String cmpPoint(int b, int w,int type) {
        if (b == w) {
            return "Tie";
        } else if (b > w) {
            return "Black wins - " + map.get(String.valueOf(type));
        } else {
            return "White wins - " + map.get(String.valueOf(type));
        }
    }

    private int check(String cards) {
        List<Integer> list = new ArrayList<>();
        list.add(straightFlush(cards));
        list.add(fourOfAKind(cards));
        list.add(fullHouse(cards));
        list.add(flush(cards));
        list.add(straight(cards));
        list.add(threeOfAKind(cards));
        list.add(twoPairs(cards));
        list.add(pair(cards));
        list.add(1);
        Collections.sort(list);
        return list.get(list.size() - 1);

    }


    /**
     * 同花顺
     *
     * @return
     */
    private int straightFlush(String cards) {
        if (straight(cards) == 5 && flush(cards) == 6) {
            return 9;
        }
        return 0;
    }

    /**
     * 铁支
     * @param cards
     * @return
     */
    private int fourOfAKind(String cards) {
        List<Integer> cardNum = splitNum(cards);
        Collections.sort(cardNum);
        int count = 1;
        for (int i = 1; i < cardNum.size(); i++) {
            if (cardNum.get(i).equals(cardNum.get(i - 1))) {
                count++;
            } else {
                count = 1;
            }
            if (count >= 4) {
                return 8;
            }
        }


        return 0;
    }

    /**
     * 葫芦
     * @param cards
     * @return
     */
    private int fullHouse(String cards) {
        if (threeOfAKind(cards) + pair(cards) == 6) {
            return 7;
        }
        return 0;
    }

    /**
     * 同花
     * @param cards
     * @return
     */
    private int flush(String cards) {
        List<String> cardColor = splitColor(cards);
        for (int i = 1; i < cardColor.size(); i++) {
            if (!cardColor.get(i).equals(cardColor.get(i - 1))) {
                return 0;
            }
        }
        return 6;
    }

    /**
     * 顺子
     * @param cards
     * @return
     */
    private int straight(String cards) {
        List<Integer> cardNum = splitNum(cards);
        Collections.sort(cardNum);

        for (int i = 1; i < cardNum.size(); i++) {
            if (cardNum.get(i) - cardNum.get(i - 1) != 1) {
                return 0;
            }
        }
        return 5;
    }

    /**
     * 三条
     * @param cards
     * @return
     */
    private int threeOfAKind(String cards) {
        List<Integer> cardNum = splitNum(cards);
        Collections.sort(cardNum);
        int count = 1;
        for (int i = 1; i < cardNum.size(); i++) {
            if (cardNum.get(i).equals(cardNum.get(i - 1))) {
                count++;
            }else {
                count = 1;
            }
            if (count == 3) {
                return 4;
            }
        }

        return 0;
    }

    /**
     * 两对
     * @param cards
     * @return
     */
    private int twoPairs(String cards) {
        List<Integer> cardNum = splitNum(cards);
        Collections.sort(cardNum);
        int count = 0;
        int temp = 1;
        for (int i = 1; i < cardNum.size(); i++) {
            if (cardNum.get(i).equals(cardNum.get(i - 1))) {
                temp++;
            }else {
                pair[2] = cardNum.get(i - 1);
                temp = 1;
            }
            if (temp == 2) {
                i++;
                count++;
                pair[count-1] = cardNum.get(i);
                temp = 1;
            }
        }
        if (count == 2) {
            return 3;
        }
        return 0;
    }

    /**
     * 对子
     * @param cards
     * @return
     */
    private int pair(String cards) {
        List<Integer> cardNum = splitNum(cards);
        Collections.sort(cardNum);
        for (int i = 1; i < cardNum.size(); i++) {
            if (cardNum.get(i).equals(cardNum.get(i - 1))) {
                pairOne[0] = cardNum.get(i);
                return 2;
            }
        }
        return 0;
    }

    private static ArrayList<Integer> splitNum(String cards) {
        cards =  cards.replaceAll("T", "10");
        cards =  cards.replaceAll("J", "11");
        cards =  cards.replaceAll("Q", "12");
        cards =  cards.replaceAll("K", "13");
        cards =  cards.replaceAll("A", "14");
        ArrayList<Integer> cardList = new ArrayList<>();
        String[] cardSplits = cards.split(" ");
        for (String card : cardSplits
        ) {
            cardList.add(Integer.valueOf(card.substring(0,card.length()-1)));
        }
        return cardList;
    }
    private static ArrayList<String> splitColor(String cards) {
        ArrayList<String> cardList = new ArrayList<>();
        String[] cardSplits = cards.split(" ");
        for (String card : cardSplits
        ) {
            cardList.add(card.substring(card.length()-1,card.length()));
        }

        return cardList;
    }
}
