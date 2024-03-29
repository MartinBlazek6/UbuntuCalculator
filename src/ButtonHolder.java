import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ButtonHolder extends JButton {
    private final String type;
    private final String pureName;

    public String getType() {
        return type;
    }

    public String getPureName() {
        return pureName;
    }

    public String getScreenText() {
        return screenText;
    }

    private final String screenText;

    private ButtonHolder(String type, String pureName, String buttonText, String screenText) {
        super(buttonText);
        this.type = type;
        this.pureName = pureName;
        this.screenText = screenText;
    }

    public String toString() {
        return "{type: " + this.type + ", pureName: " + this.pureName + ", button:" + this.getText() + ", screenText: " + this.screenText + "}";
    }

    static Map<String, ButtonHolder> getAll() {
        Map<String, ButtonHolder> map = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            map.put("" + i, new ButtonHolder("numeric", "" + i, "" + i, "" + i));
        }
        //operators keys
        map.put("add", new ButtonHolder("operator", "add", "+", "+"));
        map.put("sub", new ButtonHolder("operator", "subtract", "\u02D7", "\u02D7"));
        map.put("mul", new ButtonHolder("operator", "multiply", "\u00D7", "\u00D7"));
        map.put("div", new ButtonHolder("operator", "divide", "\u00F7", "\u00F7"));
        map.put("mod", new ButtonHolder("operator", "mod", "MOD", "MOD"));
        //Answer keys
        map.put("equal", new ButtonHolder("answer", "equal", "=", "="));
        //number modifier keys
        map.put("point", new ButtonHolder("number_modifier", "point", ".", "."));
        //Command keys
        map.put("delete", new ButtonHolder("command", "delete", "⌫", ""));
        map.put("clear", new ButtonHolder("command", "clear", "C", ""));
        map.put("exit", new ButtonHolder("command", "exit", "EXIT", ""));

        //Percent keys
        map.put("percent", new ButtonHolder("percent", "percent", "%", "%"));

        //----------Single operator keys---------------
        map.put("one_by_n", new ButtonHolder("single_operator", "one_by_n", "1/n", "1/n"));
        map.put("square", new ButtonHolder("single_operator", "square", "x\u00B2", "x\u00B2"));
        map.put("cube", new ButtonHolder("single_operator", "cube", "x\u00B3", "x\u00B3"));
        map.put("sqrt", new ButtonHolder("single_operator", "square_root", "\u221A", "\u221A"));
        String[] strings = {"sin", "cos", "tan", "asin", "acos", "atan", "sinh", "cosh", "tanh", "ln", "log"};
        map.put("ten_power", new ButtonHolder("single_operator", "ten_power", "10^n", "10^n"));
        map.put("abs", new ButtonHolder("single_operator", "absolute", "abs", "abs"));
        for (String text : strings) {
            map.put(text, new ButtonHolder("single_operator", text, text, text));
        }
        return map;
    }

    static ArrayList<String> getMapKeysByType(Map<String, ButtonHolder> buttonHolderMap, String type) {
        ArrayList<String> keyList = new ArrayList<>();
        for (String mapKey : buttonHolderMap.keySet()) {
            ButtonHolder buttonHolder = buttonHolderMap.get(mapKey);
            if (buttonHolder.type.equalsIgnoreCase(type)) {
                keyList.add(mapKey);
            }
        }
        return keyList;
    }

    static ArrayList<String> getScreenTextListByType(Map<String, ButtonHolder> buttonHolderMap, String type) {
        ArrayList<String> textList = new ArrayList<>();
        for (String mapKey : buttonHolderMap.keySet()) {
            ButtonHolder buttonHolder = buttonHolderMap.get(mapKey);
            if (buttonHolder.type.equalsIgnoreCase(type)) {
                textList.add(buttonHolder.screenText);
            }
        }
        return textList;
    }
}
