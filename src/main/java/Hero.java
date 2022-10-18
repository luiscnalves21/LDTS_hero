import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

public class Hero {
    private int x;
    private int y;
    public Hero(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        y -= 1;
    }

    public void moveDown() {
        y += 1;
    }

    public void moveLeft() {
        x -= 1;
    }

    public void moveRight() {
        x += 1;
    }

    public void draw(Screen screen) {
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
    }
}
