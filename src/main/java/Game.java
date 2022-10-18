import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.security.Key;

public class Game {
    private Hero hero;
    private Screen screen;
    public Game() {
        try {
            hero = new Hero(10, 10);
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
    }
    private void draw() throws IOException {
        screen.clear();
        hero.draw(screen);
        screen.refresh();
    }
    public void run() {
        while (true){
            try {
                draw();
                KeyStroke key = screen.readInput();
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                    screen.close();
                    break;
                }
                else if (key.getKeyType() == KeyType.EOF) { // se fechar a janela
                    break;
                }
                processKey(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp -> hero.moveUp(); // se fizer desta forma não preciso de usar break
            case ArrowDown -> hero.moveDown();
            case ArrowLeft -> hero.moveLeft();
            case ArrowRight -> hero.moveRight();
        }
    }
}