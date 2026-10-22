package com.mycompany.battleships;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/** Main game window with the player's fleet and the enemy waters. */
public class MainGame extends javax.swing.JFrame {

    // Netbeans generated
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainGame.class.getName());

    // The buttons of both grids, as [x][y].
    private JButton[][] fleetButtons = new JButton[10][10];
    private JButton[][] enemyButtons = new JButton[10][10];
    private Game game; // The game shown in this window.
    private boolean orientation = true; // True if horizontal, false if vertical.
    private int shipNum = 0; // Number of the next ship to place.
    private int[] shipLength = {5, 4, 3, 3, 2}; // Lengths of the ships.

    // Creates the window for a game.
    public MainGame(Game game) {
        this();
        this.game = game;
        messageLabel.setText(game.getCurrentPlayer().getName() + ", place your ships");
    }

    // Creates new form MainGame.
    public MainGame() {
        initComponents();
        buildGrids();
    }

    /** Sets up both grids as 10x10 buttons. Called when the frame is built. */
    private void buildGrids() {

        // Sets both grids to a 10x10 layout.
        fleetGridPanel.setLayout(new GridLayout(10, 10));
        enemyGridPanel.setLayout(new GridLayout(10, 10));

        // Set a preferred size so the grids stay 250 by 250 pixels.
        fleetGridPanel.setPreferredSize(new java.awt.Dimension(250, 250));
        enemyGridPanel.setPreferredSize(new java.awt.Dimension(250, 250));

        // Adds all the buttons into a 2D array and then adds them to the grid.
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {

                // Create the new button.
                fleetButtons[x][y] = new JButton();
                enemyButtons[x][y] = new JButton();

                // When a button is pressed, call one of the two cellClicked methods.
                int cx = x;
                int cy = y;
                fleetButtons[x][y].addActionListener(e -> fleetCellClicked(cx, cy));
                enemyButtons[x][y].addActionListener(e -> enemyCellClicked(cx, cy));

                // Set colour to blue.
                fleetButtons[x][y].setBackground(java.awt.Color.BLUE);
                enemyButtons[x][y].setBackground(java.awt.Color.BLUE);

                // To set the colour of the buttons correctly.
                setupCell(fleetButtons[x][y]);
                setupCell(enemyButtons[x][y]);

                // Add the buttons to the grid.
                fleetGridPanel.add(fleetButtons[x][y]);
                enemyGridPanel.add(enemyButtons[x][y]);
            }
        }

        pack(); // Resize the window now that the grids are filled.
    }

    /** Sets how the cells look. */
    private void setupCell(JButton button) {
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusable(false);
        button.setMargin(new java.awt.Insets(0, 0, 0, 0)); // No space around the text.
        button.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 18));
        button.setForeground(java.awt.Color.BLACK);
    }

    /** Handles a click on the fleet grid. Only works during setup. */
    private void fleetCellClicked(int x, int y) {

        if (shipNum >= shipLength.length) {
            messageLabel.setText("All ships placed. Press Switch Player.");
            return;
        }

        if (this.game.getPhase() == GamePhase.SETUP) {
            if (this.game.canPlaceShipInGrid(shipLength[shipNum], x, y, this.orientation,
                    game.getCurrentPlayer())) {
                for (int i = 0; i < shipLength[shipNum]; i++) {
                    if (orientation == true) {
                        fleetButtons[x + i][y].setBackground(java.awt.Color.YELLOW);
                    } else {
                        fleetButtons[x][y + i].setBackground(java.awt.Color.YELLOW);
                    }
                }

                // Ask the player to confirm.
                int answer = javax.swing.JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to place this ship here?",
                        "Confirm Ship",
                        javax.swing.JOptionPane.YES_NO_OPTION);

                if (answer == javax.swing.JOptionPane.YES_OPTION) {

                    // Place the ship.
                    this.game.placeShipInGrid(shipLength[shipNum], x, y, this.orientation,
                            shipNum, game.getCurrentPlayer());

                    // Change cells to grey.
                    for (int i = 0; i < shipLength[shipNum]; i++) {
                        if (orientation == true) {
                            fleetButtons[x + i][y].setBackground(java.awt.Color.GRAY);
                        } else {
                            fleetButtons[x][y + i].setBackground(java.awt.Color.GRAY);
                        }
                    }
                    shipNum++;
                    messageLabel.setText("Ship placed!");

                } else {

                    // Restore blue colours.
                    for (int i = 0; i < shipLength[shipNum]; i++) {
                        if (orientation == true) {
                            fleetButtons[x + i][y].setBackground(java.awt.Color.BLUE);
                        } else {
                            fleetButtons[x][y + i].setBackground(java.awt.Color.BLUE);
                        }
                    }
                }

            } else {
                messageLabel.setText("Cannot place ship here!");
            }
        }
    }

    /** Handles a click on the enemy grid. Only works during the playing phase. */
    private void enemyCellClicked(int x, int y) {

        // Only continue if the game phase is PLAYING.
        if (this.game.getPhase() != GamePhase.PLAYING) {
            messageLabel.setText("Cannot fire yet");
            return;
        }

        // Checks if the cell has already been shot.
        if (this.game.getOpponent().getGrid().checkShot(x, y)) {
            messageLabel.setText("This cell has already been shot");
            return;
        }

        // Fires.
        String result = this.game.fire(x, y);

        // Logic for the different results: "HIT", "MISS", etc.
        if (result.equals("MISS")) {
            enemyButtons[x][y].setBackground(java.awt.Color.BLUE);
            messageLabel.setText(result);

        } else if (result.equals("HIT")) {
            enemyButtons[x][y].setBackground(java.awt.Color.RED);
            messageLabel.setText(result);

        } else if (result.equals("SUNK")) {
            enemyButtons[x][y].setBackground(java.awt.Color.RED);
            messageLabel.setText("HIT & SUNK");

        } else if (result.equals("ALREADY FIRED")) {
            messageLabel.setText("You already fired. Press Switch Player.");
            return;
        }
        enemyButtons[x][y].setText("X");

        // If the game is finished: update the leaderboard, show the winner, go back to start.
        if (this.game.getPhase() == GamePhase.FINISHED) {

            //Increments leaderboard
            Leaderboard.incrementWins(this.game.isOver());

            JOptionPane.showMessageDialog(this,
                    this.game.isOver() + " wins!",
                    "Game over",
                    JOptionPane.INFORMATION_MESSAGE);

            // Runs only after the popup is closed (OK or X). goes back to start screen
            Start start = new Start();
            start.setLocationRelativeTo(null);
            start.setVisible(true);
            this.dispose();
        }
    }

    /** Redraws both grids for the current player. */
    public void drawGrids() {
        Grid grid1 = this.game.getCurrentPlayer().getGrid(); // Current player's grid.
        Grid grid2 = this.game.getOpponent().getGrid(); // Opponent's grid.
        Cell ownCell;
        Cell enemyCell;

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {

                // Set default colour and text.
                fleetButtons[x][y].setBackground(java.awt.Color.BLUE);
                enemyButtons[x][y].setBackground(java.awt.Color.BLUE);
                fleetButtons[x][y].setText("");
                enemyButtons[x][y].setText("");

                ownCell = grid1.getCell(x, y);
                enemyCell = grid2.getCell(x, y);

                // If the cell has a ship.
                if (ownCell.hasShip()) {
                    fleetButtons[x][y].setBackground(java.awt.Color.GRAY);
                }

                // If the cell is shot, adds an X.
                if (ownCell.isShot()) {
                    fleetButtons[x][y].setText("X");
                }

                // If the enemy cell is shot, show it.
                if (enemyCell.isShot()) {
                    if (enemyCell.hasShip()) {
                        enemyButtons[x][y].setBackground(java.awt.Color.RED);
                        enemyButtons[x][y].setText("X");
                    } else {
                        enemyButtons[x][y].setText("X");
                    }
                }
            }
        }
    }

    // Generated by NetBeans. Do not touch.
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        battleships1 = new com.mycompany.battleships.Battleships();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        switchPlayerButton = new javax.swing.JButton();
        fleetPanel = new javax.swing.JPanel();
        fleetTitleField = new javax.swing.JTextField();
        fleetGridPanel = new javax.swing.JPanel();
        enemyPanel = new javax.swing.JPanel();
        enemyTitleField = new javax.swing.JTextField();
        enemyGridPanel = new javax.swing.JPanel();
        messageLabel = new javax.swing.JLabel();
        rotateButton = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 297, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        switchPlayerButton.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        switchPlayerButton.setText("Switch Player");
        switchPlayerButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        switchPlayerButton.addActionListener(this::switchPlayerButtonActionPerformed);

        fleetPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        fleetTitleField.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        fleetTitleField.setText("Your Fleet");

        javax.swing.GroupLayout fleetGridPanelLayout = new javax.swing.GroupLayout(fleetGridPanel);
        fleetGridPanel.setLayout(fleetGridPanelLayout);
        fleetGridPanelLayout.setHorizontalGroup(
            fleetGridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        fleetGridPanelLayout.setVerticalGroup(
            fleetGridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout fleetPanelLayout = new javax.swing.GroupLayout(fleetPanel);
        fleetPanel.setLayout(fleetPanelLayout);
        fleetPanelLayout.setHorizontalGroup(
            fleetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fleetPanelLayout.createSequentialGroup()
                .addGroup(fleetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fleetPanelLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(fleetTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fleetPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(fleetGridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        fleetPanelLayout.setVerticalGroup(
            fleetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fleetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fleetTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fleetGridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        enemyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        enemyTitleField.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        enemyTitleField.setText("Enemy Waters");

        javax.swing.GroupLayout enemyGridPanelLayout = new javax.swing.GroupLayout(enemyGridPanel);
        enemyGridPanel.setLayout(enemyGridPanelLayout);
        enemyGridPanelLayout.setHorizontalGroup(
            enemyGridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        enemyGridPanelLayout.setVerticalGroup(
            enemyGridPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout enemyPanelLayout = new javax.swing.GroupLayout(enemyPanel);
        enemyPanel.setLayout(enemyPanelLayout);
        enemyPanelLayout.setHorizontalGroup(
            enemyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(enemyPanelLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(enemyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, enemyPanelLayout.createSequentialGroup()
                        .addComponent(enemyTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, enemyPanelLayout.createSequentialGroup()
                        .addComponent(enemyGridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        enemyPanelLayout.setVerticalGroup(
            enemyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(enemyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(enemyTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enemyGridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        messageLabel.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        messageLabel.setText("THIS MESSAGE DISPLAY - E.G. \"MISS\", \"HIT\", \"HIT AND SUNK\"");
        messageLabel.setToolTipText("");
        messageLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        messageLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        rotateButton.setText("Rotate");
        rotateButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rotateButton.addActionListener(this::rotateButtonActionPerformed);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(fleetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enemyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(switchPlayerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rotateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(switchPlayerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(rotateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fleetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enemyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Switches to the other player and shows the handover screen. */
    private void switchPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchPlayerButtonActionPerformed

        // During setup, all ships must be placed before handing over.
        if (game.getPhase() == GamePhase.SETUP) {
            if (!game.getCurrentPlayer().getGrid().shipsPlaced()) {
                messageLabel.setText("Place all your ships first");
                return;
            }
            // Switches to the other player (and starts the battle after player 2).
            game.finishPlacement();
            this.shipNum = 0;

        } else if (game.getPhase() == GamePhase.PLAYING) {

            // During the battle, the player must have fired this turn.
            if (!game.hasFired) {
                messageLabel.setText("Fire a shot first");
                return;
            }

            // Switches to the other player and resets game.hasFired.
            game.switchTurn();

        // Game is over, nothing to switch.
        } else {
            return;
        }

        // So the new player doesn't see the previous message.
        messageLabel.setText("");

        // Hide this window and show the handover screen.
        this.setVisible(false);

        StartTurn screen = new StartTurn(this);
        screen.setLocationRelativeTo(null); // Centre it on the screen.
        screen.setVisible(true);
    }//GEN-LAST:event_switchPlayerButtonActionPerformed

    /** Switches the ship orientation between right and down. */
    private void rotateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateButtonActionPerformed

        // Orientation is true if horizontal, false if vertical.
        if (this.orientation) {
            this.orientation = false;
            messageLabel.setText("The orientation is now down");
        } else {
            this.orientation = true;
            messageLabel.setText("The orientation is now to the right   ");
        }
    }//GEN-LAST:event_rotateButtonActionPerformed

    // Opens only this screen (NetBeans template). The game starts from Battleships.main.
    public static void main(String[] args) {
        // Use the Nimbus look and feel if it is available.
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // Create and display the form.
        java.awt.EventQueue.invokeLater(() -> new MainGame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton rotateButton;
    private javax.swing.JButton switchPlayerButton;
    private com.mycompany.battleships.Battleships battleships1;
    private javax.swing.JPanel enemyGridPanel;
    private javax.swing.JPanel fleetGridPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel fleetPanel;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel enemyPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField fleetTitleField;
    private javax.swing.JTextField enemyTitleField;
    private javax.swing.JLabel messageLabel;
    // End of variables declaration//GEN-END:variables
}
