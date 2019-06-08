import org.junit.Assert;
import org.junit.Test;

public class PokerTest {

    @Test
    public void compare() {
        Poker poker = new Poker();
        String res1 = poker.compare("2H 3D 5S 9C KD", "2C 3H 4S 8C AH");
        Assert.assertEquals("White wins - high card: Ace", res1);


        String res2 = poker.compare("2H 4S 4C 2D 4H", "2S 8S AS QS 3S");
        Assert.assertEquals("Black wins - Full House", res2);


        String res3 = poker.compare("2H 3D 5S 9C KD", "2C 3H 4S 8C KH");
        Assert.assertEquals("Black wins - high card: 9", res3);


        String res4 = poker.compare("2H 3D 5S 9C KD", "2D 3H 5C 9S KH");
        Assert.assertEquals("Tie", res4);


    }
}
