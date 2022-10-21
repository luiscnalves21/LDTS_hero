import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.List;
import java.util.Random;
import java.util.regex.PatternSyntaxException;

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

    public void moveMonsters() {
        setPosition(move());
    }

    public void verifyMonsterCollisions() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        Monster p = (Monster) o;
        return getPosition().getX() == p.getPosition().getX() && getPosition().getY() == p.getPosition().getY();
    }
}
