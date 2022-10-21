import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.awt.*;
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
    public boolean gameOver;
    public boolean win;
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        walls = createWalls();
        coins = createCoins();
        monsters = createMonsters();
        gameOver = false;
        win = false;

        hero = new Hero(10, 10);
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
            coins.add(new Coin(random.nextInt(width-2)+1, random.nextInt(height-2)+1));
            for (int j = 0; j < coins.size()-1; j++) {
                if (coins.get(j).equals(coins.get(i))) {
                    coins.remove(coins.size()-1);
                    i--;
                    break;
                }
                if (coins.get(j).getPosition().getX() == 10 && coins.get(j).getPosition().getY() == 10) {
                    coins.remove(coins.size()-1);
                    i--;
                    break;
                }
            }
        }
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            monsters.add(new Monster(random.nextInt(width-2)+1, random.nextInt(height-2)+1));
            for (int j = 0; j < monsters.size()-1; j++) {
                if (monsters.get(j).equals(monsters.get(i))) {
                    monsters.remove(monsters.size()-1);
                    i--;
                    break;
                }
                if (monsters.get(j).getPosition().getX() == 10 && monsters.get(j).getPosition().getY() == 10) {
                    monsters.remove(monsters.size()-1);
                    i--;
                    break;
                }
            }
        }
        return monsters;
    }



    public void processKey(KeyStroke key) {
        for (Monster monster : monsters) {
            monster.moveMonsters();
        }
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
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                System.out.println("==============");
                System.out.println("||GAME OVER!||");
                System.out.println("==============");
                gameOver = true;
                return true;
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