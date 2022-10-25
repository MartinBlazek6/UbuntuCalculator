import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Map;

class ElementContainer extends JFrame {
   private Map<String, ButtonHolder> buttonHolderMap;

    public Map<String, ButtonHolder> getButtonHolderMap() {
        return buttonHolderMap;
    }

    public void setButtonHolderMap(Map<String, ButtonHolder> buttonHolderMap) {
        this.buttonHolderMap = buttonHolderMap;
    }

    private JPanel keyPad = new JPanel();
    private final JPanel footer = new JPanel();


    public JTextField getOutputDisplay() {
        return outputDisplay;
    }

    private final JTextField outputDisplay = new JTextField();
    private final JTextField inputDisplay = new JTextField();

    public JTextField getInputDisplay() {
        return inputDisplay;
    }

    ElementContainer(int width, int height, Color color) {
        this.setSize(width, height);
        this.setBackground(color);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.buttonHolderMap = ButtonHolder.getAll();
        this.setupDisplay();
        this.setupKeyPad();
        this.setupFooter();
    }

    private void setupDisplay() {
        JPanel screen = new JPanel();
        screen.setLayout(null);
        int screenHeight = (int) (this.getHeight() * Helper.SCREEN_RATIO);
        this.add(screen).setBounds(0, 0, this.getWidth(), screenHeight);
        //Input display
        screen.add(inputDisplay);
        int inputDisplayHeight = (int) (screen.getHeight() * Helper.INPUT_DISPLAY_RATIO);
        this.inputDisplay.setBounds(0, 0, screen.getWidth(), inputDisplayHeight);
        this.inputDisplay.setHorizontalAlignment(JTextField.LEFT);
        this.inputDisplay.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        this.inputDisplay.setText("0");
        this.inputDisplay.setEditable(false);
        this.inputDisplay.setBackground(Color.BLACK);
        this.inputDisplay.setForeground(Color.WHITE);

        //Output display
        screen.add(this.outputDisplay);
        int outputDisplayHeight = (int) (screen.getHeight() * Helper.OUTPUT_DISPLAY_RATIO);
        this.outputDisplay.setBounds(0, inputDisplayHeight, screen.getWidth(), outputDisplayHeight);
        this.outputDisplay.setHorizontalAlignment(JTextField.RIGHT);
        this.outputDisplay.setFont(Helper.SCREEN_FONT);
        this.outputDisplay.setText("0");
        this.outputDisplay.setEditable(false);
        this.outputDisplay.setBackground(Helper.darkGrey);
        this.outputDisplay.setForeground(Color.WHITE);
    }

    private void setupKeyPad() {
        //------------setting font, color and style of buttons-------
        ArrayList<String> mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "numeric");
        this.prepareButtons(mapKeys, Helper.GREY);

        mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "operator");
        this.prepareButtons(mapKeys, Helper.GREY);

        mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "number_modifier");
        this.prepareButtons(mapKeys, Helper.GREY);

        mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "answer");
        this.prepareButtons(mapKeys, Helper.GREEN);

        this.prepareButtonByKey("exit", Color.GRAY);
        this.prepareButtonByKey("clear", Color.GRAY);
        this.prepareButtonByKey("delete", Helper.RED);

        mapKeys = ButtonHolder.getMapKeysByType(this.buttonHolderMap, "single_operator");
        this.prepareButtons(mapKeys, Helper.GREY);

        this.prepareButtonByKey("percent", Helper.GREY);

        this.keyPad = new JPanel();
        int keyPadY = (int) (this.getHeight() * Helper.SCREEN_RATIO);
        int keyPadHeight = (int) (this.getHeight() * Helper.KEYPAD_RATIO);
        this.add(this.keyPad).setBounds(0, keyPadY, this.getWidth(), keyPadHeight);
        this.keyPad.setLayout(null);
        this.keyPad.setBackground(this.getBackground());
        this.keyPad.setForeground(Helper.WHITE);

        this.fixButtonsPlacement();

    }

    private void setupFooter() {
        int footerY = (int) (this.getHeight() * (Helper.SCREEN_RATIO + Helper.KEYPAD_RATIO));
        int footerHeight = (int) (this.getHeight() * Helper.FOOTER_RATIO);
        this.add(footer).setBounds(0, footerY, this.getWidth(), footerHeight);

        JLabel copyrightTag = new JLabel("\u00A9 " + Year.now().getValue() + "  CI- Technology");
        copyrightTag.setForeground(Helper.BLUE);
        this.footer.add(copyrightTag).setBounds(0, 0, this.footer.getWidth(), this.footer.getHeight());
    }

    private void prepareButtons(ArrayList<String> buttonHolderMapKeys, Color bgColor) {
        for (String key : buttonHolderMapKeys) {
            this.prepareButtonByKey(key, bgColor);
        }
    }

    private void prepareButtonByKey(String key, Color bgColor) {
        this.buttonHolderMap.get(key).setFont(Helper.KEY_FONT_MD);
        this.buttonHolderMap.get(key).setBackground(bgColor);
        this.buttonHolderMap.get(key).setFocusable(false);
        this.buttonHolderMap.get(key).setForeground(Helper.WHITE);
        this.buttonHolderMap.get(key).setBorder(new RoundedBorder(10));
    }

    private static class RoundedBorder implements Border {
        private int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }
        public boolean isBorderOpaque() {
            return true;
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    private void fixButtonsPlacement() {

        int btnWidth = this.keyPad.getWidth() / Helper.BUTTON_PER_LINE;
        this.keyPad.add(this.buttonHolderMap.get("clear")).setBounds(0, 0, btnWidth, Helper.BUTTON_HEIGHT);
        this.keyPad.add(this.buttonHolderMap.get("delete")).setBounds(btnWidth, 0, btnWidth, Helper.BUTTON_HEIGHT);

        String[] commonButtons = {"1", "2", "3", "add", "sub", "4", "5", "6", "mul", "div", "7", "8", "9", "mod", "0", "point", "percent", "equal"};
        this.setButtonByKeyList(commonButtons, Helper.BUTTON_HEIGHT);

        String[] scientificButtons = {"sin", "cos", "tan", "ln", "log", "one_by_n", "square", "cube", "sqrt", "exit"};
        this.setButtonByKeyList(scientificButtons, 6 * Helper.BUTTON_HEIGHT);
    }

    private void setButtonByKeyList(String[] buttonsKeys, int startY) {
        int btnPerLine = Helper.BUTTON_PER_LINE;
        int btnWidth = this.keyPad.getWidth() / btnPerLine;
        int btnHeight = Helper.BUTTON_HEIGHT;

        int rowCounter = 0, colCounter = 0, counter = 0;
        for (String buttonKey : buttonsKeys) {
            int x = colCounter * btnWidth, y = rowCounter * btnHeight + startY;


            if (buttonKey.equalsIgnoreCase("mod") || buttonKey.equalsIgnoreCase("equal")) {
                this.keyPad.add(this.buttonHolderMap.get(buttonKey)).setBounds(x, y, btnWidth * 2, btnHeight);
                counter += 2;
            } else {
                this.keyPad.add(this.buttonHolderMap.get(buttonKey)).setBounds(x, y, btnWidth, btnHeight);
                counter++;
            }
            rowCounter = counter % btnPerLine == 0 ? rowCounter + 1 : rowCounter;
            colCounter = counter % btnPerLine == 0 ? 0 : colCounter + 1;
        }
    }

}
