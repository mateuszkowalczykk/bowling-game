import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class GameTest {
Game game = new Game();

    void multiRoll(int numberOfRolls, int knockedPins){
        for(int i = 0;i<numberOfRolls;i++){
            game.roll(knockedPins);
        }
    }

    @Test
    @DisplayName("Zero knocked Pins Test")
    void zeroKnockedPinsTest() {
        multiRoll(20,0);
        assertThat(game.score()).isEqualTo(0);
    }

    @Test
    @DisplayName("Always 3 Knocked Pins Test")
    void always3KnockedPinsTest() {
        multiRoll(20,3);
        assertThat(game.score()).isEqualTo(60);
    }

    @Test
    @DisplayName("Strike Test")
    void strikeTest() {
        multiRoll(10,1);//10
        game.roll(10);//10
        game.roll(3);//6
        game.roll(3);//6
        multiRoll(6,1);//6
        assertThat(game.score()).isEqualTo(38);
    }

    @Test
    @DisplayName("Spare Test")
    void spareTest() {
        multiRoll(10,1);//10
        game.roll(7);//7
        game.roll(3);//3
        game.roll(5);//10
        multiRoll(6,1);//6
        assertThat(game.score()).isEqualTo(36);
    }

    @Test
    @DisplayName("Should Ignore Too Much Rolls")
    void shouldIgnoreTooMuchRolls() {
        multiRoll(20,1);//10
        multiRoll(10,5);//6
        assertThat(game.score()).isEqualTo(20);
    }

    @Test
    @DisplayName("Should Return 133")
    void shouldReturn133() {
        game.roll(1);
        game.roll(4);
        game.roll(4);
        game.roll(5);
        game.roll(6);
        game.roll(4);
        game.roll(5);
        game.roll(5);
        game.roll(10);
        game.roll(0);
        game.roll(1);
        game.roll(7);
        game.roll(3);
        game.roll(6);
        game.roll(4);
        game.roll(10);
        game.roll(2);
        game.roll(8);
        game.roll(6);
        assertThat(game.score()).isEqualTo(133);
    }

    @Test
    @DisplayName("Should Return 300")
    void shouldReturn300() {
         multiRoll(12, 10);

        assertThat(game.score()).isEqualTo(300);
    }
}
