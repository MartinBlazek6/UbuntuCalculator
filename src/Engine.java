import java.util.Arrays;

class Engine {
    private String result = null;

    String computeSciFun(String funcName, String input) {
        double inputValue = Double.parseDouble(input);
        if (Arrays.asList("sin", "cos", "tan").contains(funcName)) {
            return "" + this.calculateTrigonometry(funcName, inputValue);
        }

        switch (funcName) {
            case "square" -> result = "" + (inputValue * inputValue);
            case "cube" -> result = "" + (inputValue * inputValue * inputValue);
            case "square_root" -> result = "" + (Math.sqrt(inputValue));
            case "one_by_n" -> result = "" + (1 / inputValue);
            case "ln" -> result = "" + Math.log(inputValue);
            case "log" -> result = "" + Math.log10(inputValue);
        }
        return result != null ? result : "Not implemented yet";
    }

    private double calculateTrigonometry(String functionName, double input) {
        double radianInput = Math.toRadians(input);

        switch (functionName) {
            case "sin" -> result = String.valueOf(Math.sin(radianInput));
            case "cos" -> result = String.valueOf(Math.cos(radianInput));
            case "tan" -> result = String.valueOf(Math.tan(radianInput));
        }
        return result != null ? Double.parseDouble(result) : input;
    }

    String compute(String operand1, String operand2, String operator) {
        try {
            double parsedOperand1 = Double.parseDouble(operand1);
            double parsedOperand2 = Double.parseDouble(operand2);
            switch (operator.toLowerCase()) {
                case "+" -> result = "" + (parsedOperand1 + parsedOperand2);
                case "\u00D7" -> result = "" + (parsedOperand1 * parsedOperand2);
                case "\u02D7" -> result = "" + (parsedOperand1 - parsedOperand2);
                case "mod" -> result = "" + (parsedOperand1 % parsedOperand2);
                case "\u00F7" -> result = "" + (parsedOperand1 / parsedOperand2);
            }

            return result != null ? result : "FAILED";
        } catch (Exception e) {
            return "ERROR";
        }
    }
}
