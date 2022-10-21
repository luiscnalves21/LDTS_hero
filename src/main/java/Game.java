import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    final Arena arena;
    public Game() {
        try {
            TerminalSize terminalSize = new TerminalSize(40, 20); // tamanho do terminal
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal(); // criar terminal
            screen = new TerminalScreen(terminal); // criar window

            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary
        } catch (IOException e) {
            e.printStackTrace();
        }
        arena = new Arena(40, 20);
    }
    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }
    public void run() {
        while (true){
            try {
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);
                if (arena.verifyMonsterCollisions()) {
                    screen.close();
                    break;
                }
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                    screen.close();
                }
                else if (key.getKeyType() == KeyType.EOF) { // se fechar a janela
                    break;
                }
                if (arena.win) {
                    screen.close();
                    break;
                }
                arena.moveMonsters();
                if (arena.verifyMonsterCollisions()) {
                    screen.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp -> arena.moveHero(arena.moveUp()); // se fizer desta forma não preciso de usar break
            case ArrowDown -> arena.moveHero(arena.moveDown());
            case ArrowLeft -> arena.moveHero(arena.moveLeft());
            case ArrowRight -> arena.moveHero(arena.moveRight());
        }
    }
}