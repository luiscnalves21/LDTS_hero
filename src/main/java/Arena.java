import com.googlecode.lanterna.SGR;
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
    private List<Monster> monsters;
    private List<Wall> walls;
    private List<Coin> coins;
    public boolean win;
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    private class Hero extends Element {
        private Hero(int x, int y) {
            super(x, y);
        }

        public void draw(TextGraphics graphics) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#ffff33"));
            graphics.enableModifiers(SGR.BOLD);
            graphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "X");
        }
    }
    public Position moveUp() {
        return new Position(hero.getPosition().getX(), (hero.getPosition().getY()-1));
    }

    public Position moveDown() { return new Position(hero.getPosition().getX(), (hero.getPosition().getY()+1)); }

    public Position moveLeft() {
        return new Position(hero.getPosition().getX()-1, (hero.getPosition().getY()));
    }

    public Position moveRight() {
        return new Position(hero.getPosition().getX()+1, (hero.getPosition().getY()));
    }

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        hero = new Hero(10, 10);
        walls = createWalls();
        coins = createCoins();
        monsters = createMonsters();
        win = false;
    }

    private List<Wall> createWalls() {
        walls = new ArrayList<>();
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
        coins = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Coin newcoin = new Coin(random.nextInt(width-2)+1, random.nextInt(height-2)+1);
            if (!coins.contains(newcoin) && !newcoin.getPosition().equals(hero.getPosition())) {
                coins.add(newcoin);
            }
        }
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Monster newmonster = new Monster(random.nextInt(width-2)+1, random.nextInt(height-2)+1);
            if (!monsters.contains(newmonster) && !newmonster.getPosition().equals(hero.getPosition())) {
                monsters.add(newmonster);
            }
        }
        return monsters;
    }

    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (coin.getPosition().equals(hero.getPosition())) {
                coins.remove(coin);
                break;
            }
        }
    }

    public void moveMonsters() {
        for (Monster monster : monsters) {
            monster.setPosition(monster.move());
        }
    }

    public boolean verifyMonsterCollisions() {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                System.out.println("==============");
                System.out.println("||GAME OVER!||");
                System.out.println("==============");
                return true;
            }
        }
        return false;
    }

    public void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
        }
        retrieveCoins();
    }

    public boolean canHeroMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        if (coins.size() == 0) {
            System.out.println("========");
            System.out.println("||WIN!||");
            System.out.println("========");
            win = true;
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
        for (Monster monster : monsters) {
            monster.draw(graphics);
        }
    }
}