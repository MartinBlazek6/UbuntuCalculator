import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Calculator extends ElementContainer implements ActionListener {
    private final Engine engine = new Engine();

    Calculator(int width, int height, Color bgColor) {
        super(width, height, bgColor);
        this.addActionListenerToAllButton();
    }

    private void addActionListenerToAllButton() {
        for (String key : this.getButtonHolderMap().keySet()) {
            this.getButtonHolderMap().get(key).addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ButtonHolder pressedButton = (ButtonHolder) e.getSource();
        String buttonType = pressedButton.getType();
        String pureName = pressedButton.getPureName();

        switch (buttonType) {
            case "single_operator" -> this.singleOperation(pressedButton);
            case "numeric" -> this.displayNumber(pressedButton);
            case "number_modifier" -> this.displayNumberModifier(pressedButton);
            case "operator" -> this.displayOperator(pressedButton);
            case "answer" -> this.getAnswer(this.getInputDisplay().getText());
        }
        switch (pureName) {
            case "exit" -> System.exit(0);
            case "clear" -> this.clearScreen();
            case "delete" -> this.deleteInput();
        }

    }

    private void clearScreen() {
        this.getInputDisplay().setText("0");
        this.getOutputDisplay().setText("0");
    }

    private void deleteInput() {
        String input = this.getInputDisplay().getText();
        ArrayList<String> operators = ButtonHolder.getScreenTextListByType(this.getButtonHolderMap(), "operator");
        ArrayList<String> numbers = ButtonHolder.getScreenTextListByType(this.getButtonHolderMap(), "numeric");
        if (input.length() == 1) {
            this.getInputDisplay().setText("0");
        } else if (Helper.isLastChar(input, operators) || Helper.isLastChar(input, numbers)) {
            this.getInputDisplay().setText(input.substring(0, input.length() - 1));
        }
    }

    private void displayNumber(ButtonHolder numericButton) {
        this.getInputDisplay().setText(this.isInputClear() ? numericButton.getScreenText() : this.getInputDisplay().getText() + numericButton.getScreenText());
    }

    private void displayNumberModifier(ButtonHolder numberModifierButton) {
        String inputText = this.getInputDisplay().getText();
        if (!inputText.contains(numberModifierButton.getScreenText())) {
            this.getInputDisplay().setText(this.getInputDisplay().getText() + numberModifierButton.getScreenText());
        }
    }

    private void displayOperator(ButtonHolder operatorButton) {

        if (this.hasInputOperator()) {
            this.getAnswer(this.getInputDisplay().getText());
        }
        this.getInputDisplay().setText(this.isOutputClear() ? this.getInputDisplay().getText() + operatorButton.getScreenText() : this.getOutputDisplay().getText() + operatorButton.getScreenText());
    }

    private void singleOperation(ButtonHolder operatorButton) {
        String input;

        if (this.hasInputOperator()) {
            this.getAnswer(this.getInputDisplay().getText());
        }

        input = !this.isOutputClear() ? this.getOutputDisplay().getText() : this.hasInputOperator() ? this.getOutputDisplay().getText() : this.getInputDisplay().getText();
        this.getInputDisplay().setText(operatorButton.getScreenText() + "(" + input + ")");

        String result = this.engine.computeSciFun(operatorButton.getPureName(), input);
        this.getOutputDisplay().setText(result);
    }

    private void getAnswer(String input) {
        String operator = this.getInputFirstOperator();
        if (operator.equals("")) {
            this.getOutputDisplay().setText(this.getInputDisplay().getText());
        } else {
            int operatorPosition = input.indexOf(operator);
            String operand1 = input.substring(0, operatorPosition);
            String operand2 = input.substring(operatorPosition + operator.length());
            String result = this.engine.compute(operand1, operand2, operator);
            this.getOutputDisplay().setText(result);
        }
    }

    private boolean isInputClear() {
        return this.getInputDisplay().getText().equals("0");
    }

    private boolean isOutputClear() {
        return this.getOutputDisplay().getText().equals("0");
    }

    private String getInputFirstOperator() {
        String input = this.getInputDisplay().getText();
        ArrayList<String> operatorList = ButtonHolder.getScreenTextListByType(this.getButtonHolderMap(), "operator");
        return Helper.getFoundOperator(input, operatorList);
    }

    private boolean hasInputOperator() {
        return !this.getInputFirstOperator().equals("");
    }
}
