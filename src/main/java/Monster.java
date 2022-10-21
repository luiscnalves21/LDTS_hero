import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {
    public Monster(int x, int y) {
        super(x, y);
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#ff9000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "M");
    }

    public Position move() {
        Random random = new Random();
        int tempX = 0;
        int tempY = 0;
        while (tempX == 0 || tempX == 39 || tempY == 0 || tempY == 19 || tempX == getPosition().getX() || tempY == getPosition().getY()) {
            tempX = random.nextInt(3)-1+getPosition().getX();
            tempY = random.nextInt(3)-1+getPosition().getY();
        }
        return new Position(tempX, tempY);
    }
}
