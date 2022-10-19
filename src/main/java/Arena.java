import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;


public class Arena {
    final int width;
    final int height;
    final Hero hero;
    private List<Wall> walls;
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = createWalls();

        hero = new Hero(10, 10);
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    public void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp -> moveHero(hero.moveUp()); // se fizer desta forma não preciso de usar break
            case ArrowDown -> moveHero(hero.moveDown());
            case ArrowLeft -> moveHero(hero.moveLeft());
            case ArrowRight -> moveHero(hero.moveRight());
        }
    }

    public void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
        }
    }

    public boolean canHeroMove(Position position) {
        if (position.getX() >= 1 && position.getX() < width-1) {
            return position.getY() >= 1 && position.getY() < height-1;
        }
        return false;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls) {
            wall.draw(graphics);
        }
    }
}
