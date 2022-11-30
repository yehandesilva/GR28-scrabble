import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The ScrabbleFrameView class models the GUI (frame) of the
 * Scrabble game.
 *
 * @author Pathum Danthanarayana, 101181411
 * @author Yehan De Silva
 * @version 1.2
 * @date November 13, 2022
 */
public class ScrabbleFrameView extends JFrame implements ScrabbleView {

    /** Fields **/
    private ArrayList<PlayerCardView> playerCards;
    private Container contentPane;
    private JPanel boardPanel;
    private JPanel boardContainerPanel;
    private JPanel playerPanel;
    private JLabel currentTurn;
    private JButton playButton;
    private JButton redrawButton;
    private JButton skipButton;
    private FontManager fontManager;
    private JMenuItem quitMenuItem;
    private ScrabbleGameModel scrabbleModel;
    private ScrabbleController scrabbleController;
    private SetupMenuController setupMenuController;

    /** Constants **/
    // Colours
    public static final Color BOARD_COLOR = new Color(48,90,119);
    public static final Color ACCENT_COLOR = new Color(64, 187, 161);
    public static final Color SQUARE_BACKGROUND_COLOR = new Color(36, 96, 120);
    public static final Color DOUBLE_LETTER_SQUARE_BACKGROUND_COLOR = new Color(93, 195, 235);
    public static final Color TRIPLE_LETTER_SQUARE_BACKGROUND_COLOR = new Color(113, 210, 113);
    public static final Color DOUBLE_WORD_SQUARE_BACKGROUND_COLOR = new Color(255,  120,  120);
    public static final Color TRIPLE_WORD_SQUARE_BACKGROUND_COLOR = new Color(243, 170, 68);
    public static final Color SQUARE_BORDER_COLOR = new Color(34, 178, 194);
    public static final Color PLAYER_CARD_COLOR = new Color(28, 62, 91);
    public static final Color SELECTED_TILE_COLOR = new Color(223, 223, 223);
    public static final Color CENTER_SQUARE_COLOR = new Color(239, 240, 143);
    public static final Color SELECTED_BUTTON_COLOR = new Color(202, 91, 89);

    // Spacing dimensions
    public static final Dimension MENU_SPACING = new Dimension(0, 80);
    public static final Dimension BUTTON_SPACING = new Dimension(10, 0);
    public static final Dimension MAIN_MIDDLE_SPACING = new Dimension(30, 0);
    public static final Dimension PLAYER_CARD_SPACING = new Dimension(0, 30);
    public static final Dimension CURRENT_TURN_SPACING = new Dimension(0, 20);

    // JPanel dimensions
    public static final Dimension BOARD_DIMENSIONS = new Dimension(650, 650);
    public static final Dimension HORIZONTAL_BOARD_LABEL_DIMENSIONS = new Dimension(650, 25);
    public static final Dimension VERTICAL_BOARD_LABEL_DIMENSIONS = new Dimension(25, 650);
    public static final Dimension PLAYER_PANEL_DIMENSIONS = new Dimension(500, 740);
    public static final Dimension BUTTON_PANEL_DIMENSIONS = new Dimension(400, 100);
    public static final Dimension PLAYER_CARD_DIMENSIONS = new Dimension(500, 125);

    /**
     * Constructs a Scrabble Frame.
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @author Amin Zeina, 101186297
     * @version 1.4
     * @date November 13, 2022
     */
    public ScrabbleFrameView()
    {
        // Configure JFrame
        super("Scrabble Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 350);
        this.setResizable(false);

        // Set icon for frame
        ImageIcon frameIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/frame_icon.jpg")));
        this.setIconImage(frameIcon.getImage());

        // Configure ContentPane
        contentPane = this.getContentPane();
        contentPane.setBackground(BOARD_COLOR);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        scrabbleModel = new ScrabbleGameModel();
        scrabbleModel.addScrabbleView(this);
        scrabbleController = new ScrabbleController(scrabbleModel, this);
        setupMenuController = new SetupMenuController(scrabbleModel, this);
        currentTurn = new JLabel();

        playButton = new JButton("Play");
        redrawButton = new JButton("Redraw");
        skipButton = new JButton("Skip");

        // Initialize the font manager
        fontManager = new FontManager();

        // Configure Menu
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menu = new JMenu("Quit");
        menuBar.add(menu);
        quitMenuItem = new JMenuItem("Quit Game");
        menu.add(quitMenuItem);
        quitMenuItem.addActionListener(scrabbleController);
        quitMenuItem.setEnabled(false);

        // Setup the menu to play the game
        this.setupMenu();
        this.setVisible(true);
    }

    /**
     * Returns the frame's play button.
     * @return frame's play button.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 13, 2022
     */
    public JButton getPlayButton() {return this.playButton;}

    /**
     * Returns the frame's redraw button.
     * @return frame's redraw button.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 13, 2022
     */
    public JButton getRedrawButton() {return this.redrawButton;}


    /**
     * Returns the frame's skip button.
     * @return frame's skip button.
     * @author Yehan De Silva
     * @version 1.0
     * @date November 17, 2022
     */
    public JButton getSkipButton() {return this.skipButton;}

    /**
     * The setupMenu method sets up the initial menu of the
     * Scrabble game.
     * This menu is what the user initially interacts with,
     * where they specify the number of players playing the Scrabble
     * game, and the name of each player.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     * @date November 11, 2022
     */
    private void setupMenu()
    {
        // Set up main header for the menu
        JLabel mainHeader = new JLabel("Welcome to Scrabble!");
        mainHeader.setFont(fontManager.getManropeBold().deriveFont(Font.BOLD, 38f));
        mainHeader.setForeground(Color.WHITE);
        mainHeader.setAlignmentX(CENTER_ALIGNMENT);
        mainHeader.setHorizontalAlignment(JLabel.CENTER);

        // Set up the tagline for the menu
        JLabel tagLine = new JLabel("Developed by Group 28");
        tagLine.setFont(fontManager.getManropeRegular().deriveFont(Font.PLAIN, 22f));
        tagLine.setForeground(Color.WHITE);
        tagLine.setAlignmentX(CENTER_ALIGNMENT);
        tagLine.setHorizontalAlignment(JLabel.CENTER);

        // Set up the play button for the menu
        JButton playButton = new JButton("Let's play!");
        playButton.setBackground(ACCENT_COLOR);
        playButton.setFocusPainted(false);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(fontManager.getManropeBold().deriveFont(Font.BOLD, 18f));
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        playButton.addActionListener(setupMenuController);

        contentPane.add(Box.createRigidArea(MENU_SPACING));
        contentPane.add(mainHeader);
        contentPane.add(Box.createRigidArea(CURRENT_TURN_SPACING));
        contentPane.add(tagLine);
        contentPane.add(Box.createRigidArea(PLAYER_CARD_SPACING));
        contentPane.add(playButton);
    }

    /**
     * The startGame method starts the Scrabble game by setting up
     * the main game window (which includes the Scrabble board,
     * player cards, etc.).
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @author Amin Zeina, 101186297
     * @version 1.3
     * @date November 13, 2022
     */
    public void startGame()
    {
        // Initialize ArrayList of player cards
        this.playerCards = new ArrayList<>();

        // Remove all elements from the ContentPane
        this.contentPane.removeAll();
        // Repaint the JFrame
        this.repaint();
        // Change the size of the JFrame
        this.setSize(1250, 800);
        // Apply a new layout to the ContentPane
        contentPane.setLayout(new FlowLayout());

        // JPanel #1a: Scrabble BoardModel
        boardPanel = new BoardPanelView(scrabbleModel.getGameBoard(), scrabbleController);

        // Add the horizontal and vertical labels to the Scrabble board
        this.addScrabbleBoardLabels();

        // JPanel #2: PlayerModel Panel
        playerPanel = new JPanel();
        playerPanel.setPreferredSize(PLAYER_PANEL_DIMENSIONS);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(BOARD_COLOR);
        playerPanel.add(Box.createRigidArea(CURRENT_TURN_SPACING));

        // Configure current turn label (initially Player 1)
        currentTurn.setFont(fontManager.getManropeRegular().deriveFont(Font.PLAIN, 22f));
        currentTurn.setAlignmentX(CENTER_ALIGNMENT);
        currentTurn.setHorizontalAlignment(JLabel.CENTER);
        currentTurn.setForeground(Color.WHITE);
        playerPanel.add(currentTurn);
        playerPanel.add(Box.createRigidArea(CURRENT_TURN_SPACING));

        // Add the player cards to the JPanel
        this.addPlayerCards();
        // Add the buttons to the JPanel
        JButton[] buttonsToAdd = new JButton[]{playButton, redrawButton, skipButton};
        this.addButtons(buttonsToAdd);

        // Add all the JPanels to ContentPane
        contentPane.add(boardContainerPanel);
        // Add spacing in between board panel and player panel
        contentPane.add(Box.createRigidArea(MAIN_MIDDLE_SPACING));
        contentPane.add(playerPanel);

        // setup first turn of game by enabling/disabling certain buttons
        scrabbleModel.setupFirstTurn();

        // enable quit menu now that the game is running.
        quitMenuItem.setEnabled(true);
        this.update();
    }

    /**
     * The addScrabbleBoardLabels method adds the horizontal and vertical
     * labels to the Scrabble board.
     *
     * @author Pathum Danthanarayana, 101181411
     * @version 1.0
     * @date November 22nd, 2022
     */
    private void addScrabbleBoardLabels()
    {
        // JPanel #1b: Horizontal labelling for scrabble board
        JPanel horizontalLabelPanel = new JPanel();
        horizontalLabelPanel.setBackground(BOARD_COLOR);
        horizontalLabelPanel.setPreferredSize(HORIZONTAL_BOARD_LABEL_DIMENSIONS);
        horizontalLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel horizontalLabel = new JLabel("");
        horizontalLabel.setFont(this.fontManager.getManropeRegular().deriveFont(Font.PLAIN, 14f));
        horizontalLabel.setForeground(Color.WHITE);

        // Spacing values
        String initialSpacing = " ".repeat(12);
        String singleDigitSpacing = " ".repeat(12);
        String doubleDigitSpacing = " ".repeat(10);
        String horizontalLabelStr = "";

        // Traverse through numbers 1 through 15
        for (int i = 1; i < BoardModel.SIZE + 1; i++)
        {
            // Add the initial offset/spacing
            if (i == 1)
            {
                horizontalLabelStr += initialSpacing;
            }

            // Add the number to the string
            horizontalLabelStr += i;

            // Check 1a: The number is less than 10 (single digit number)
            if (i < 10)
            {
                // Check if we are at 9
                if (i == 9)
                {
                    // If so, add the double digit spacing
                    horizontalLabelStr += doubleDigitSpacing;
                }
                else
                {
                    // Otherwise, add the generic spacing for a single digit number
                    horizontalLabelStr += singleDigitSpacing;
                }
            }
            else
            {
                // Otherwise, add the generic spacing for a double digit number
                horizontalLabelStr += doubleDigitSpacing;
            }
        }
        // Set the JLabel's text to the created string
        horizontalLabel.setText(horizontalLabelStr);
        // Add the JLabel to the horizontal label panel
        horizontalLabelPanel.add(horizontalLabel);

        // JPanel #1c: Vertical labelling for scrabble board
        JPanel verticalLabelPanel = new JPanel();
        verticalLabelPanel.setBackground(BOARD_COLOR);
        verticalLabelPanel.setPreferredSize(VERTICAL_BOARD_LABEL_DIMENSIONS);
        JLabel verticalLabel = new JLabel("");
        verticalLabel.setFont(this.fontManager.getManropeRegular().deriveFont(Font.PLAIN, 14f));
        verticalLabel.setForeground(Color.WHITE);
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};

        String verticalLabelStr;
        // Traverse through letters from A to O
        for (String letter : letters) {
            // Check if the current letter is A
            if (letter.equals("A"))
            {
                // If so, open the HTML tag, and add the custom p (paragraph) styling to add space above and below A
                verticalLabelStr = String.format("<HTML><p style=\"margin-top: 6px; margin-bottom:4px;\">%s</p><br>", letter);
            }
            // Check if the current letter is O (last letter)
            else if (letter.equals("O"))
            {
                // If so, add no styling to the p tag and close the HTML tag
                verticalLabelStr = String.format("<p>%s</p></HTML>", letter);
            }
            else
            {
                // Otherwise, add the generic p styling to the letter (a bottom margin spacing of 3px)
                verticalLabelStr = String.format("<p style=\"margin-bottom:4px;\">%s</p><br>", letter);
            }
            // Set the JLabel's text to its current text plus the current HTML snippet
            verticalLabel.setText(verticalLabel.getText() + verticalLabelStr);
        }
        // Add the JLabel to the vertical label panel
        verticalLabelPanel.add(verticalLabel);

        // Container JPanel for holding JPanel #1a, #1b, and #1c (board panel, horizontal label panel, and vertical label panel)
        boardContainerPanel = new JPanel();
        boardContainerPanel.setLayout(new BorderLayout());
        boardContainerPanel.add(boardPanel, BorderLayout.CENTER);
        boardContainerPanel.add(horizontalLabelPanel, BorderLayout.NORTH);
        boardContainerPanel.add(verticalLabelPanel, BorderLayout.WEST);
    }

    /**
     * The addPlayerCards method adds player cards (which contains
     * the player's name, their score, and their rack) to the JPanel
     * that will hold all the player cards.
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.1
     */
    private void addPlayerCards()
    {
        for (PlayerModel player : scrabbleModel.getPlayers()) {
            PlayerCardView playerCard = new PlayerCardView(player, scrabbleController);
            // Keep a reference to the player card
            this.playerCards.add(playerCard);
            playerPanel.add(playerCard);
            playerPanel.add(Box.createRigidArea(PLAYER_CARD_SPACING));
        }
    }

    /**
     * The addButtons method adds the given buttons to the player panel in
     * the Scrabble game.
     *
     * @param buttons - An array of Buttons to be added
     *
     * @author Pathum Danthanarayana, 101181411
     * @author Yehan De Silva
     * @version 1.2
     * @date November 13, 2022
     */
    private void addButtons(JButton[] buttons)
    {
        // Create and configure JPanel to store the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setMaximumSize(BUTTON_PANEL_DIMENSIONS);
        buttonsPanel.setBackground(BOARD_COLOR);

        for (JButton button : buttons) {
            // Create and configure a new JButton
            button.setBackground(ACCENT_COLOR);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            button.setFont(fontManager.getManropeBold().deriveFont(Font.BOLD, 18f));
            button.addActionListener(scrabbleController);

            buttonsPanel.add(button);
            buttonsPanel.add(Box.createRigidArea(BUTTON_SPACING));
        }
        // Add the button panel to the player panel
        playerPanel.add(buttonsPanel);
    }

    /**
     * Updates the current turn text with the name of the current player.
     *
     * @author Yehan De Silva
     * @version 1.1
     * @date November 11, 2022
     */
    @Override
    public void update() {
        currentTurn.setText("Current turn:   " + scrabbleModel.getCurrentPlayer().getName());
    }

    /** Main method **/
    public static void main(String[] args)
    {
        new ScrabbleFrameView();
    }
}
