import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    final int width;
    final int height;
    final Hero hero;
    final List<Wall> walls;
    final List<Coin> coins;
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();

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

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            coins.add(new Coin(random.nextInt(width-2)+1, random.nextInt(height-2)+1));
            for (int j = 0; j < coins.size()-1; j++) {
                if (coins.get(j).equals(coins.get(i))) {
                    coins.remove(coins.size()-1);
                    i--;
                }
                if (coins.get(j).getPosition().getX() == 10 && coins.get(j).getPosition().getY() == 10) {
                    coins.remove(coins.size()-1);
                    i--;
                }
            }
        }
        return coins;
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
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        for (int i = 0; i < coins.size(); i++) {
            if (coins.get(i).getPosition().equals(position)) {
                coins.remove(coins.get(i));
                break;
            }
        }
        if (coins.size() == 0) {
            System.out.println("Ganhaste!");
        }
        return true;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Coin coin : coins) {
            coin.draw(graphics);
        }
        for (Wall wall : walls) {
            wall.draw(graphics);
        }
    }
}