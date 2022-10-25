public class AppRunner {
    public static void main(String[] args){
        Calculator calculator = new Calculator(450, 600,Helper.darkGrey);
        calculator.setLocationRelativeTo(null);
        calculator.setVisible(true);
    }
}
