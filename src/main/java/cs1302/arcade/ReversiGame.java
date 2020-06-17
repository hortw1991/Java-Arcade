package cs1302.arcade;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.Popup;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

import java.util.Random;
import java.util.List;

import cs1302.arcade.ReversiSquare;
import cs1302.arcade.ArcadeApp;

/**
 * Represents a game of reversi.
 */
public class ReversiGame extends VBox {
    Text highScoreText;
    TextField initialInputField;
    Popup popup;
    LinkedHashMap<String, Integer> highScores;

    HBox statusBar;
    Text score;
    GridPane grid;

    ReversiSquare[][] board;
    int[][] pieces;
    int player;
    int redPieces;
    int greenPieces;

    boolean playerOneMoves;
    boolean playerTwoMoves;

    ArcadeApp app;

    HBox menu;
    Label scoreLabel;
    Label livesLabel;
    Label difficultyLabel;
    Button highScoresButton;
    Button restartButton;
    Button exitButton;
    Button gameSelectButton;

    String unresponsiveString;

    /**
     * Set responsive allows the square in question to
     *show potential vertical, horizontal, or diagonal moves,
     * provided it would surround the opposing player's piece.
     *
     * @param i  the relevant row
     * @param j  the relevant column
     *
     * @return {@code EventHandler<? super MouseEvent} hide and show moves.
     */
    private EventHandler<? super MouseEvent> setResponsive(int i, int j) {
        return event -> {
            hideMoves();
            showMoves(i, j);
        };
    } // setResponsive

    /**
     *Sets a square to be unresponsive.
     *
     *@return {@code EventHandler<? super MouseEvent} sets a space to be unresponsive.
     */
    private EventHandler<? super MouseEvent> setUnresponsive() {
        return event -> unresponsiveString = "a";
    } //setUnresponsive

    /**
     * Adds the ability to place a piece in highlighted squares.  Once
     * clicked again, it removes highlighted and adds the piece.
     *
     * The {@code setResponsive EventHandler} allows the
     *initial checking of available moves.  This function actually sets
     * the "highlighted" potential moves to place the piece there.
     * @param i  the relevant row
     * @param j  the relevant column
     * @param direction  the direction in which the pieces will be flipped
     *
     * @return {@code EventHandler<? super MouseEvent} hide and show moves.
     */
    private EventHandler<? super MouseEvent> placePiece(int i, int j, String direction) {
        return event -> {
            if (player == 1) {
                board[i][j].addPiece(player);
                pieces[i][j] = player;
                flipPieces(i, j, direction);
                player = 2;

            } else {
                board[i][j].addPiece(player);
                pieces[i][j] = player;
                flipPieces(i, j, direction);
                player = 1;

            }

            hideMoves();
            updateScore();
            hideMoves();
        };

    } // placePiece

    /**
     *  Represents a game of Reversi.
     *
     * @param a  an {@code ArcadeApp} object.
     */
    public ReversiGame(ArcadeApp a) {
        player = 1;
        grid = new GridPane();
        grid.setGridLinesVisible(true);

        app = a;
        createGameOverPopup();

        // Init both boards (one is the visible board, the other is for tracking purposes).
        board = new ReversiSquare[8][8];
        pieces = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                // Visual board
                board[i][j] = new ReversiSquare();
                board[i][j].setAlignment(Pos.CENTER);
                GridPane.setRowIndex(board[i][j], i);
                GridPane.setColumnIndex(board[i][j], j);
                grid.getChildren().add(board[i][j]);

                // Board representation
                pieces[i][j] = 0;
            }
        }

        // Starting center pieces, which also get the responsive handler.  We do NOT want the
        // player to be able to click on an empty square and see potential moves.
        board[3][3].addPiece(1);
        board[3][4].addPiece(2);
        board[4][3].addPiece(2);
        board[4][4].addPiece(1);
        board[3][3].setOnMouseClicked(setResponsive(3, 3));
        board[3][4].setOnMouseClicked(setResponsive(3, 4));
        board[4][3].setOnMouseClicked(setResponsive(4, 3));
        board[4][4].setOnMouseClicked(setResponsive(4, 4));
        pieces[3][3] = 1;
        pieces[3][4] = 2;
        pieces[4][3] = 2;
        pieces[4][4] = 1;
        redPieces = 2;
        greenPieces = 2;


        playerOneMoves = true;
        playerTwoMoves = true;


        // Status bar section (is updated by updateScore method).
        statusBar = new HBox(5);
        score = new Text("Player 1's Turn");
        statusBar.getChildren().add(score);

        createMenu();



        this.getChildren().addAll(statusBar, grid);

    } // start

    /**
     * Controls the direction things will be flipped.
     *
     * Note that "flip north" means that from the player's perspective the board will flip to north.
     * The actual control for this is going from the blank space in reverse towards the original
     * piece and breaking.
     *
     * @param row  the row containing the blank piece
     * @param col  the col containing the blank piece
     * @param direction  the {@code String} direction to go opposite of.
     * */
    public void flipPieces(int row, int col, String direction) {

        if (direction.equals("N")) {
            flipNorth(row, col);
        } //if

        if (direction.equals("S")) {
            flipSouth(row, col);
        } //if

        if (direction.equals("E")) {
            flipEast(row, col);
        } //if

        if (direction.equals("W")) {
            flipWest(row, col);
        } //if
        if (direction.equals("NW")) {
            flipNorthWest(row, col);
        } //if

        if (direction.equals("NE")) {
            flipNorthEast(row, col);
        } //if

        if (direction.equals("SW")) {
            flipSouthWest(row, col);
        } //if

        if (direction.equals("SE")) {
            flipSouthEast(row, col);
        } //if
    } //flipPieces

    /**
     * Flips the pieces vertically in a southerly direction.
     *
     * @param  row the row where a piece was placed
     * @param  col the column where a piece was placed
     */
    private void flipNorth(int row, int col) {
        for (int i = row + 1; i < 8; i++ ) {
            if (pieces[i][col] == player) {
                break;
            }

            row++;
            pieces[row][col] = player;
            board[row][col].flipPiece();
        }
    } //flipNorth

    /**
     * Flips the pieces vertically in a northerly direction.
     *
     * @param  row the row where a piece was placed
     * @param  col the column where a piece was placed
     */
    private void flipSouth(int row, int col) {
        for (int i = row - 1; i >= 0; i--) {
            if (pieces[i][col] == player) {
                break;
            }
            pieces[i][col] = player;
            board[i][col].flipPiece();
        }
    } //flipSouth

    /**
     * Flips the pieces horizontally in a westerly direction.
     *
     * @param  row the row where a piece was placed
     * @param  col the column where a piece was placed
     */
    private void flipEast(int row, int col) {
        for (int i = col - 1; i >= 0; i--) {
            if (pieces[row][i] == player) {
                break;
            }
            pieces[row][i] = player;
            board[row][i].flipPiece();
        }
    } //flipEast

    /**
     * Flips the pieces horizontally in a westerly direction.
     *
     * @param  row the row where a piece was placed
     * @param  col the column where a piece was placed
     */
    private void flipWest(int row, int col) {
        for (int i = col + 1; i < 8; i++) {
            if (pieces[row][i] == player) {
                break;
            }
            pieces[row][i] = player;
            board[row][i].flipPiece();
        }
    } //flipWest

    /**
     * Flips the pieces when the piece placed is to the southeast.
     *
     * @param  row the row where a piece was placed
     * @param  col the column where a piece was placed
     */
    private void flipSouthEast(int row, int col) {
        while (true) {
            row--;
            col--;
            if (pieces[row][col] == player) {
                break;
            }
            pieces[row][col] = player;
            board[row][col].flipPiece();
        }
    } //flipSouthEast

    /**
     * Flips the pieces when the piece placed is to the southwest.
     *
     * @param  row the row where a piece was placed
     * @param  col the column where a piece was placed
     */
    private void flipSouthWest(int row, int col) {
        while (true) {
            row--;
            col++;
            if (pieces[row][col] == player) {
                break;
            }
            pieces[row][col] = player;
            board[row][col].flipPiece();
        }
    } //flipSouthWest

    /**
     * Flips the pieces when the piece placed is to the northeast.
     *
     * @param  row the row where a piece was placed
     * @param  col the column where a piece was placed
     */
    private void flipNorthEast(int row, int col) {
        while (true) {
            row++;
            col--;
            if (pieces[row][col] == player) {
                break;
            }
            pieces[row][col] = player;
            board[row][col].flipPiece();
        }
    } //flipNorthEast

    /**
     * Flips the pieces when the piece placed is to the northwest.
     *
     * @param  row the row where a piece was placed
     * @param  col the column where a piece was placed
     */
    private void flipNorthWest(int row, int col) {
        while (true) {
            row++;
            col++;
            if (pieces[row][col] == player) {
                break;
            }
            pieces[row][col] = player;
            board[row][col].flipPiece();
        }
    } //flipNorthWest


    /**
     * Creates the menu object.
     */
    public void createMenu() {
        menu = new HBox(5);

        exitButton = new Button("Exit");
        gameSelectButton = new Button("Game Select");
        restartButton = new Button("Restart");
        highScoresButton = new Button("High Scores");

        EventHandler<? super MouseEvent> exitHandler = e -> {
            System.exit(0);
        };
        exitButton.setOnMouseClicked(exitHandler);

        EventHandler<? super MouseEvent> resetHandler = r -> {
            app.setReversiScene();
        };
        restartButton.setOnMouseClicked(resetHandler);

        EventHandler<? super MouseEvent> gameSelectHandler = e -> {
            app.setMainScene();
        };
        gameSelectButton.setOnMouseClicked(gameSelectHandler);


        EventHandler<? super MouseEvent> highScoresHandler = h -> {
            highScoreDisplay();
        };
        highScoresButton.setOnMouseClicked(highScoresHandler);

        menu.getChildren().addAll(exitButton, restartButton, gameSelectButton, highScoresButton);
        this.getChildren().add(menu);

    } //createMenu

    /**
     * Show moves.
     *
     * If it is player 1's turn, we are looking for flanked discs, so stop
     * the iteration when either a blank spot or the same color is
     * encountered.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showMoves(int row, int col) {
        if (col == 7 && row < 8 && pieces[row + 1][col] != 0 && pieces[row][col] == player) {
            showLastSouth(row, col);
        } // S last col
        if (col == 7 && row > 0 && pieces[row - 1][col] != 0 && pieces[row][col] == player) {
            showLastNorth(row, col);
        } // N last col
        if (col == 7 && row > 0 || col == 7 && row == 7 || col == 7 && row == 0
            && pieces[row][col - 1] != 0 && pieces[row][col] == player) {
            showLastWest(row, col);
        } // W last col
        if ((col == 7 && row != 0 || row == 7 && col > 1 || col == 7 && row == 7)
            && pieces[row - 1][col - 1] != 0 && pieces[row][col] == player) {
            showLastNorthWest(row, col);
        } // NW last col/row
        if (row == 7 && col < 6 && pieces[row][col] != 0 && pieces[row][col] == player
            && pieces[row - 1][col + 1] != 0 && pieces[row][col] == player) {
            showLastNorthEast(row, col);
        } //NE last row
        if (col == 7 && row != 7 && pieces[row + 1][col - 1] != 0 && pieces[row][col] == player) {
            showLastSouthWest(row, col);
        } // SW last col
        if (col < 8 && pieces[row][col + 1] != 0 && pieces[row][col] == player) {
            showEast(row, col);
        } // East
        if (col > 0 && col < 7 && pieces[row][col - 1] != 0 && pieces[row][col] == player) {
            showWest(row, col);
        } //West
        if (row < 8 && pieces[row + 1][col] != 0 && pieces[row][col] == player) {
            showSouth(row, col);
        } // South
        if (row > 0 && pieces[row - 1][col] != 0 && pieces[row][col] == player) {
            showNorth(row, col);
        } // North
        if (row > 0 && col > 0 && pieces[row - 1][col - 1] != 0 && pieces[row][col] == player) {
            showNorthWest(row, col);
        } // NW
        if ((row > 0 && col < 8 || row == 7 && col < 8) && pieces[row - 1][col + 1] != 0
            && pieces[row][col] == player) {
            showNorthEast(row, col);
        } // NE
        if (row < 8 && col > 0 && pieces[row + 1][col - 1] != 0 && pieces[row][col] == player) {
            showSouthWest(row, col);
        } // SW
        if (row < 8 && col < 8 && pieces[row + 1][col + 1] != 0 && pieces[row][col] == player) {
            showSouthEast(row, col);
        } //SE
    } //showMoves

    /**
     * Shows available moves to the north.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showNorth(int row, int col) {
        for (int i = row - 1; i >= 0; i--) {
            if (pieces[i][col] == player) {
                break;
            } else {
                if (pieces[i][col] == 0) {
                    board[i][col].highlightSquare();
                    board[i][col].setOnMouseClicked(placePiece(i, col, "N"));
                    break;
                }
            }
        }
    } //showNorth

    /**
     * Shows available moves to the south.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showSouth(int row, int col) {
        for (int i = row + 1; i < 8; i++) {
            if (pieces[i][col] == player) {
                break;
            } else {
                if (pieces[i][col] == 0) {
                    board[i][col].highlightSquare();
                    board[i][col].setOnMouseClicked(placePiece(i, col, "S"));
                    break;
                }
            }
        }
    } //showSouth

    /**
     * Shows available moves to the west.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showWest(int row, int col) {
        for (int i = col - 1; i >= 0; i--) {
            if (pieces[row][i] == player) {
                break;
            } else {
                if (pieces[row][i] == 0) {
                    board[row][i].highlightSquare();
                    board[row][i].setOnMouseClicked(placePiece(row, i, "W"));
                    break;
                }
            }
        }
    } //showWest

    /**
     * Shows available moves to the east.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showEast(int row, int col) {
        for (int i = col + 1; i < 8; i++) {
            if (pieces[row][i] == player) {
                break;
            } else {
                if (pieces[row][i] == 0) {
                    board[row][i].highlightSquare();
                    board[row][i].setOnMouseClicked(placePiece(row, i, "E"));
                    break;
                }
            }
        }
    } //showEast

    /**
     * Shows available moves to the NW.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showNorthWest(int row, int col) {
        int r1 = row;
        int c1 = col;
        r1--;
        c1--;
        while (r1 >= 0 && c1 >= 0) {
            if (pieces[r1][c1] == player) {
                break;
            } else {
                if (pieces[r1][c1] == 0) {
                    board[r1][c1].highlightSquare();
                    board[r1][c1].setOnMouseClicked(placePiece(r1, c1, "NW"));
                    break;
                }
            }
            r1--;
            c1--;
        }
    } //showNorthWest

    /**
     * Shows available moves to the NE.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showNorthEast(int row, int col) {
        int r2 = row;
        int c2 = col;
        r2--;
        c2++;
        while (r2 >= 0 && c2 < 8) {
            if (pieces[r2][c2] == player) {
                break;
            } else {
                if (pieces[r2][c2] == 0) {
                    board[r2][c2].highlightSquare();
                    board[r2][c2].setOnMouseClicked(placePiece(r2, c2, "NE"));
                    break;
                }
            }
            r2--;
            c2++;
        }
    } //showNorthEast

    /**
     * Shows available moves to the SW.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showSouthWest(int row, int col) {
        int r3 = row;
        int c3 = col;
        r3++;
        c3--;
        board[r3][c3].highlightSquare();
        while (r3 < 8 && c3 >= 0) {
            if (pieces[r3][c3] == player) {
                break;
            } else {
                if (pieces[r3][c3] == 0) {
                    board[r3][c3].highlightSquare();
                    board[r3][c3].setOnMouseClicked(placePiece(r3, c3, "SW"));
                    break;
                }
            }
            r3++;
            c3--;
        }
    } //showSouthWest

    /**
     * Shows available moves to the SE.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showSouthEast(int row, int col) {
        row++;
        col++;
        while (row < 8 && col < 8) {
            if (pieces[row][col] == player) {
                break;
            } else {
                if (pieces[row][col] == 0) {
                    board[row][col].highlightSquare();
                    board[row][col].setOnMouseClicked(placePiece(row, col, "SE"));
                    break;
                }
            }
            row++;
            col++;
        }
    } //showSouthEast

    /**
     * Shows available moves to the SW for the last col.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showLastSouthWest(int row, int col) {
        int checkRow2 = row;
        int checkCol2 = col;
        checkRow2++;
        checkCol2--;
        while (checkRow2 < 8 && checkCol2 >= 0) {
            if (pieces[checkRow2][checkCol2] == player) {
                break;
            } else {
                if (pieces[checkRow2][checkCol2] == 0) {
                    board[checkRow2][checkCol2].highlightSquare();
                    board[checkRow2][checkCol2]
                        .setOnMouseClicked(placePiece(checkRow2, checkCol2, "SW"));
                    break;
                } //if
            } //while
            checkRow2++;
            checkCol2--;
        } //if
    } //showLastSouthWest

    /**
     * Shows available moves to the NW for the last col.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showLastNorthWest(int row, int col) {
        int checkRow = row;
        int checkCol = col;
        checkRow--;
        checkCol--;
        while (checkRow > 0 && checkCol >= 0) {
            if (pieces[checkRow][checkCol] == player) {
                break;
            } else {
                if (pieces[checkRow][checkCol] == 0) {
                    board[checkRow][checkCol].highlightSquare();
                    board[checkRow][checkCol]
                        .setOnMouseClicked(placePiece(checkRow, checkCol, "NW"));
                    break;
                } //if
            } //if
            checkRow--;
            checkCol--;
        } //while
    } //showLastNorthWest

    /**
     * Shows available moves to the NE for the last row.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showLastNorthEast(int row, int col) {
        int checkRow = row;
        int checkCol = col;
        checkRow--;
        checkCol++;
        while (checkRow > 0 && checkCol < 8) {
            if (pieces[checkRow][checkCol] == player) {
                break;
            } else {
                if (pieces[checkRow][checkCol] == 0) {
                    board[checkRow][checkCol].highlightSquare();
                    board[checkRow][checkCol]
                        .setOnMouseClicked(placePiece(checkRow, checkCol, "NE"));
                    break;
                } //if
            } //if
            checkRow--;
            checkCol++;
        } //while
    } //showLastNorthEast

    /**
     * Shows available moves to the west for the last col.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showLastWest(int row, int col) {
        int col2 = col;
        col2--;

        while (col2 > 0) {
            if (pieces[row][col2] == player) {
                break;
            } else {
                if (pieces[row][col2] == 0) {
                    board[row][col2].highlightSquare();
                    board[row][col2]
                        .setOnMouseClicked(placePiece(row, col2, "W"));
                    break;
                } //if
            } //if
            col2--;
        } //while
    } //showLastWest

    /**
     * Shows available moves to the north for the last col.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showLastNorth(int row, int col) {
        for (int i = row - 1; i >= 0; i--) {
            if (pieces[i][col] == player) {
                break;
            } else {
                if (pieces[i][col] == 0) {
                    board[i][col].highlightSquare();
                    board[i][col].setOnMouseClicked(placePiece(i, col, "N"));
                    break;
                }
            }
        }
    } //showLastNorth

    /**
     * Shows available moves to the south for the last col.
     *
     * @param row the row clicked on.
     * @param col the col clicked on.
     */
    private void showLastSouth(int row, int col) {
        for (int i = row + 1; i < 8; i++) {
            if (pieces[i][col] == player) {
                break;
            } else {
                if (pieces[i][col] == 0) {
                    board[i][col].highlightSquare();
                    board[i][col].setOnMouseClicked(placePiece(i, col, "S"));
                    break;
                }
            }
        }
    } //showLastSouth

    /**
     * Checks to see if the player has any available moves by going through each piece and
     * checking for:
     *   1. The piece touches a blank in that direction
     *   2. The piece touches their own color in that direction
     *   3. Any directions containing enemy pieces are met with their own color.
     *
     *@return hasMoves  a variable that stores whether or not the player has any moves left.
     */
    private boolean checkForMoves() {

        boolean hasMoves = false;

        // Opposite player
        int op = 0;
        if (player == 1) {
            op = 2;
        } else {
            op = 1;
        }

        // Loop through each square of the grid
        for (int row = 0; row < pieces.length; row++) {
            for (int col = 0; col < pieces[row].length; col++) {
                if (pieces[row][col] == player) {
                    if (checkNorth(row, col, op)) {
                        hasMoves = true;
                    }
                    if (checkEast(row, col, op)) {
                        hasMoves = true;
                    }
                    if (checkSouth(row, col, op)) {
                        hasMoves = true;
                    }
                    if (checkWest(row, col, op)) {
                        hasMoves = true;
                    }
                    if (checkNW(row, col, op)) {
                        hasMoves = true;
                    }
                    if (checkNE(row, col, op)) {
                        hasMoves = true;
                    }
                    if (checkSE(row, col, op)) {
                        hasMoves = true;
                    }
                    if (checkSW(row, col, op)) {
                        hasMoves = true;
                    }
                } //if
            } //for (col)

        } //for (row)

        return hasMoves;
    } //checkForMoves

    /**
     *Checks for moves to the north.
     *
     *@param row  the row of a given piece
     *@param col the column of a given piece
     *@param op the opposite player
     *@return true if there are moves available, false otherwise
     */
    private boolean checkNorth(int row, int col, int op) {
        /*
         *Return false if it's the top 2 rows,
         *since the top 2 rows cannot have available northerly moves.
         */
        if (row <= 1) {
            return false;
        }
        // If the piece above is the same player or blank, cut it
        row--;
        if (pieces[row][col] != op) {
            return false;
        } else {
            while (pieces[row][col] == op && row > 1) {
                row--;

                if (pieces[row][col] == 0) {
                    return true;
                } //if
            }
        }
        return false;
    } //checkNorth

    /**
     *Checks for moves to the east.
     *
     *@param row  the row of a given piece
     *@param col the column of a given piece
     *@param op the opposite player
     *@return true if there are moves available, false otherwise
     */
    private boolean checkEast(int row, int col, int op) {
        // Return false if its the far right 2 cols
        if (row >= 6) {
            return false;
        }
        // If the piece above is the same player or blank, cut it
        col++;
        if (pieces[row][col] != op) {
            return false;
        } else {
            while (pieces[row][col] == op && row < 6) {
                col++;

                if (pieces[row][col] == 0) {
                    return true;
                } //if
            }
        }
        return false;
    } //checkEast

    /**
     *Checks for moves to the south.
     *
     *@param row  the row of a given piece
     *@param col the column of a given piece
     *@param op the opposite player
     *@return true if there are moves available, false otherwise
     */
    private boolean checkSouth(int row, int col, int op) {
        if (row >= 6) {
            return false;
        }
        // If the piece above is the same player or blank, cut it
        row++;
        if (pieces[row][col] != op && pieces[row][col] != 0) {
            return false;
        } else {
            while (pieces[row][col] == op && row < 6) {
                row++;

                if (pieces[row][col] == 0) {
                    return true;
                } //if
            }
        }
        return false;
    } //checkSouth

    /**
     *Checks for moves to the west.
     *
     *@param row  the row of a given piece
     *@param col the column of a given piece
     *@param op the opposite player
     *@return true if there are moves available, false otherwise
     */
    private boolean checkWest(int row, int col, int op) {
        if (col <= 1) {
            return false;
        }
        // If the piece above is the same player or blank, cut it
        col--;
        if (pieces[row][col] != op && pieces[row][col] != 0) {
            return false;
        } else {
            while (pieces[row][col] == op && col > 1) {
                col--;

                if (pieces[row][col] == 0) {
                    return true;
                } //if
            }
        }
        return false;
    } //checkWest

    /**
     *Checks for moves to the northwest.
     *
     *@param row  the row of a given piece
     *@param col the column of a given piece
     *@param op the opposite player
     *@return true if there are moves available, false otherwise
     */
    private boolean checkNW(int row, int col, int op) {
        if (col <= 1 || row <= 1) {
            return false;
        }
        // If the piece above is the same player or blank, cut it
        col--;
        row--;
        if (pieces[row][col] != op && pieces[row][col] != 0) {
            return false;
        } else {
            while (pieces[row][col] == op && col > 1 && row > 1) {
                col--;
                row--;

                if (pieces[row][col] == 0) {
                    return true;
                } //if
            }
        }
        return false;
    } //checkNW

    /**
     *Checks for moves to the northeast.
     *
     *@param row  the row of a given piece
     *@param col the column of a given piece
     *@param op the opposite player
     *@return true if there are moves available, false otherwise
     */
    private boolean checkNE(int row, int col, int op) {
        if (col >= 6 || row <= 1) {
            return false;
        }
        // If the piece above is the same player or blank, cut it
        col++;
        row--;
        if (pieces[row][col] != op && pieces[row][col] != 0) {
            return false;
        } else {
            while (pieces[row][col] == op && col < 6 && row > 1) {
                col++;
                row--;

                if (pieces[row][col] == 0) {
                    return true;
                } //if
            }
        }
        return false;
    } //checkNE

    /**
     *Checks for moves to the southeast.
     *
     *@param row  the row of a given piece
     *@param col the column of a given piece
     *@param op the opposite player
     *@return true if there are moves available, false otherwise
     */
    private boolean checkSE(int row, int col, int op) {
        if (col >= 6 || row >= 6) {
            return false;
        }
        // If the piece above is the same player or blank, cut it
        col++;
        row++;
        if (pieces[row][col] != op && pieces[row][col] != 0) {
            return false;
        } else {
            while (pieces[row][col] == op && col < 6 && row < 6) {
                col++;
                row++;

                if (pieces[row][col] == 0) {
                    return true;
                } //if
            }
        }
        return false;
    } //checkSE

    /**
     *Checks for moves to the southwest.
     *
     *@param row  the row of a given piece
     *@param col the column of a given piece
     *@param op the opposite player
     *@return true if there are moves available, false otherwise
     */
    private boolean checkSW(int row, int col, int op) {
        if (col <= 1 || row >= 6) {
            return false;
        }
        // If the piece above is the same player or blank, cut it
        col--;
        row++;
        if (pieces[row][col] != op && pieces[row][col] != 0) {
            return false;
        } else {
            while (pieces[row][col] == op && col > 1 && row < 6) {
                col--;
                row++;

                if (pieces[row][col] == 0) {
                    return true;
                } //if
            }
        }
        return false;
    } //checkSW

    /**
     * Goes through each {@code ReversiSquare} and un-highlights any highlighted squares.
     */
    private void hideMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getIsHighlighted()) {
                    board[i][j].removeHighlight();
                    board[i][j].setOnMouseClicked(setUnresponsive());
                } //if

                if (pieces[i][j] != 0) {
                    board[i][j].setOnMouseClicked(setResponsive(i, j));
                } //if
            }
        }
    } //hideMoves

    /**
     * Updates the score and changes the text indicating
     * which player's turn it is.
     *
     */
    private void updateScore() {

        // Reset score and iterate through the board tracking the pieces
        redPieces = 0;
        greenPieces = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int s = pieces[i][j];
                if (s == 1) {
                    redPieces++;
                    board[i][j].setRed();
                } else if (s == 2) {
                    greenPieces++;
                    board[i][j].setGreen();
                }
            }
        }

        // Set updated score display
        String text = String.format("Player %d's turn.\n"
                                    + "Player 1: %d\n"
                                    + "Player 2: %d\n",
                                    player, redPieces, greenPieces);
        score.setText(text);

        if (redPieces + greenPieces == 64) {
            popup.show(app.stage);
        } //if

        if (!checkForMoves()) {
            if (player == 1) {
                player = 2;
                if (!checkForMoves()) {
                    popup.show(app.stage);
                } //if
            } else {
                player = 1;
                if (!checkForMoves()) {
                    popup.show(app.stage);
                } //if
            } //if
        } //if
    } //updateScore

    /**
     * Creates the game over popup.
     */
    private void createGameOverPopup() {
        // pbox will hold components including the high score list
        popup = new Popup();
        VBox pbox = new VBox(5);

        Label gameOverLabel = new Label("Game Over");
        pbox.getChildren().add(gameOverLabel);


        // Set buttons for Restart, Game Select, and Back
        Button btnRestart = new Button("Restart");
        EventHandler<? super MouseEvent> restartHandler = r -> {
            popup.hide();
            app.setCentipedeScene();
        };
        btnRestart.setOnMouseClicked(restartHandler);

        Button btnSelect = new Button("Game Select");
        EventHandler<? super MouseEvent> selectHandler = d -> {
            popup.hide();
            app.setMainScene();
        };
        btnSelect.setOnMouseClicked(selectHandler);

        Button btnExit = new Button("Exit");
        EventHandler<? super MouseEvent> exitHandler = e -> {
            popup.hide();
            System.exit(0);
        };
        btnExit.setOnMouseClicked(exitHandler);

        HBox buttonBox = new HBox(5);
        buttonBox.getChildren().addAll(btnRestart, btnSelect, btnExit);
        pbox.getChildren().addAll(buttonBox);

        // Add score box
        Label initialInstructions = new Label("Enter your initials (no commas)!");
        initialInputField = new TextField("Initials");
        Button input = new Button("Enter");

        pbox.getChildren().addAll(initialInstructions, initialInputField, input);
        EventHandler<? super MouseEvent> highScoreHandler = h -> {
            String text = initialInputField.getText();
            if (text == null || text.isEmpty() || text.contains(",")) {
                return;
            } else {
                addHighScores(initialInputField.getText());
                input.setDisable(true);
            }
        };
        input.setOnMouseClicked(highScoreHandler);

        // Calculate high score and add to table
        readHighScores();

        popup.getContent().add(pbox);

    } //createGameOverPopup

    /**
     * Displays the high score alert.
     */
    private void highScoreDisplay() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("High Scores");
        alert.setHeaderText(null);

        String s = "";

        readHighScores();
        Map<String, Integer> sorted = sortHighScores();

        for (String key : sorted.keySet()) {
            s += key.replaceAll("\\s+", "") + ": " + sorted.get(key) + "\n";

        }

        alert.setContentText(s);
        alert.showAndWait();
    } //highScoreDisplay

    /**
     * Adds high scores to the list should they qualify.
     * @param newEntry the user's text from the input box.
     */
    private void addHighScores(String newEntry) {
        for (String key : highScores.keySet()) {
            if (key.equals(newEntry)) {
                newEntry += "\t\t";
            }
        }
        highScores.put(newEntry, Math.abs(redPieces - greenPieces));
        Map<String, Integer> sorted = sortHighScores();

        writeHighScores(sorted);
    } //addHighScores

    /**
     * Writes high scores to the score file from the hash map.
     * @param scores a map of key-value score pairs (name score).
     */
    private void writeHighScores(Map<String, Integer> scores) {
        String s = "";
        int n = 0;
        // Only write the top 5 high scores
        for (String key : scores.keySet()) {
            if (n < 5) {
                s += key.replaceAll("\\s+", "") + "," + scores.get(key) + "\n";
            }
            n++;
        }

        try {
            File f = new File("reversi_scores.txt");
            PrintWriter w = new PrintWriter(f);
            w.print(s);
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //writeHighScores

    /**
     * Reads high scores into a hash map from the score file.
     */
    private void readHighScores() {
        try {
            File f = new File("reversi_scores.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            Scanner s = new Scanner(f);
            highScores = new LinkedHashMap<>();

            while (s.hasNext()) {
                String line = s.next();
                String[] values = line.split(",");
                highScores.put(values[0], Integer.parseInt(values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //readHighScores

    /**
     * Sorts high scores descending according to their value.
     * @return a map with the user's name and score in descending order.
     */
    private Map<String, Integer> sortHighScores() {
        Map<String, Integer> result = highScores.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return result;
    } //sortHighScores

} //ReversiGame
