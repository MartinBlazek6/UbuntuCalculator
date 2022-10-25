import java.awt.*;
import java.util.ArrayList;

class Helper {
    final static Color darkGrey = new Color(28 , 28, 28);
    //---------------------Fonts & Colors------------------------
    final static Font SCREEN_FONT = new Font("Times New Roman", Font.BOLD, 35);
    final static Font KEY_FONT_MD = new Font("Times New Roman", Font.PLAIN, 20);

    final static int BUTTON_HEIGHT = 40;
    final static int BUTTON_PER_LINE = 5;

    final static Color GREEN = new Color(12, 108, 28);
    final static Color GREY = new Color(55, 55, 55);
    final static Color RED = new Color(170, 20, 36);
    final static Color WHITE = new Color(222, 222, 222);
    final static Color BLUE = new Color(0, 0, 255);

    //Full Calculator division
    final static double SCREEN_RATIO = 0.2;
    final static double KEYPAD_RATIO = 0.75;
    final static double FOOTER_RATIO = 0.05;

    final static double INPUT_DISPLAY_RATIO = 0.3;
    final static double OUTPUT_DISPLAY_RATIO = 0.7;

    static String getFoundOperator(String str, ArrayList<String> keys){
        for(String key:keys){
            if(str.contains(key)){
                return key;
            }
        }
        return "";
    }

    static boolean isLastChar(String string, ArrayList<String> elements){
        String lastChar = string.substring(string.length()-1);
        for(String element:elements){
            if(lastChar.equalsIgnoreCase(element)){
                return true;
            }
        }
        return false;
    }
}
