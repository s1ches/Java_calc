import java.io.IOException;
import java.io.SerializablePermission;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter expression: ");
        String exp = input.nextLine();

        System.out.println("Result: " + calc(exp));

    }

    public static String[] FormStr(String input) throws Exception{
        int k=0;
        //ИДЁМ ПО СТРОКЕ, ЦЕПЛЯЯ НУЖНЫЕ НАМ ЭЛЕМЕНТЫ, ИГНОРИРУЕМ ПРОБЕЛЫ
        String new_str[] = {"","",""};
        for(int i=0;i<input.length();i++){
            String symb = String.valueOf(input.charAt(i));
            if(symb.equals("+") | symb.equals("-") | symb.equals("*") | symb.equals("/"))
                new_str[k++] = symb;
            else if(!symb.equals(" ")){
                new_str[k]+=String.valueOf(input.charAt(i));
                if(i+1!=input.length()) {
                    String symb_1 = String.valueOf(input.charAt(i + 1));
                    if (symb_1.equals("+") | symb_1.equals("-") | symb_1.equals("*") | symb_1.equals("/") | symb_1.equals(" "))
                        k++;
                }
                else if(i+1==input.length()){
                    k++;
                }
            }
        }
        //ЕСЛИ НЕ НАБРАЛОСЬ 2 ОПЕРАНДОВ И 1 ОПЕРАТОРА, ВЫБРАСЫВАЕТ ИСКЛЮЧЕНИЕ
        if(k<3)
            throw new Exception();

        return new_str;
    }

    public static int[] IsArabic(String x)throws Exception{
        int roman_to_arabic = RomanToArabic(x);
        //ЕСЛИ СИСТЕМА СЧИСЛЕНИЯ НЕ РИМСКАЯ, ТО ПРОБУЕМ ПРЕОБРАЗОВАТЬ СИМВОЛ В int, ЕСЛИ x - НЕ ЧИСЛО, СРАБАТЫВАЕТ ИСКЛЮЧЕНИЕ
        if(roman_to_arabic==0){
            try{
                return new int[]{Integer.parseInt(x),1};
            }catch (Exception e){}
        }

        return new int[]{roman_to_arabic,0};
    }

    //ПРЕОБРАЗОВАНИЕ ИЗ РИМСКОЙ СИСТЕМЫ СЧИСЛЕНИЯ В АРАБСКУЮ
    public static int RomanToArabic(String x){
        String RomanNums[] = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        for(int i=0;i<10;i++){
            if(x.equals(RomanNums[i]))
                return i+1;
        }
        return 0;
    }

    //ПРЕОБРАЗОВАНИЕ ИЗ АРАБСКОЙ СИСТЕМЫ СЧИСЛЕНИЯ В РИМСКУЮ
    public static String ArabicToRoman(int x) throws Exception{
        if(x<1)
            throw new Exception();

        String RomanNums[] = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        String RomanDozen[] = {"X","XX","XXX","IL","L","LX","LXX","LXX","XC"};

        if(x==100)
            return "C";
        if(x==10)
            return "X";
        if(x<10){
            return RomanNums[x-1];
        }else{
            return RomanDozen[x/10-1]+RomanNums[x%10-1];
        }
    }

    public static String calc(String input) throws Exception{
        //ФУНКЦИЯ ВЫБИРАЕТ ЗНАЧАЩИЕ СИМВОЛЫ ИЗ СТРОКИ(ОПЕРАТОР И ДВА ОПЕРАНДА)
        String new_str[] = FormStr(input);
        String a = new_str[0], operator = new_str[1], b = new_str[2];

        //ПРОВЕРЯЕМ АРАБСКАЯ ЛИ СИСТЕМА СЧИСЛЕНИЯ ИЛИ НЕТ(0, ЕСЛИ РИМСКАЯ, ИНАЧЕ АРАБСКАЯ)
        //ТАК ЖЕ ВОЗВРАЩАЕМ ЗНАЧЕНИЯ ОПЕРАНДОВ, ПРИВЕДЁННЫХ К ТИПУ int
        int a_info[] = IsArabic(a);
        int b_info[] = IsArabic(b);

        int int_a = a_info[0];
        int int_b = b_info[0];

        int isArabicA = a_info[1];
        int isArabicB = b_info[1];

        //ЕСЛИ В ОДНОМ ВЫРАЖЕНИИ ИСПОЛЬЗУЮТСЯ РАЗНЫЕ СИСТЕМЫ СЧИСЛЕНИЯ ИЛИ ОПЕРАНДЫ > 10, ВЫБРАСЫВАЕТ ИСКЛЮЧЕНИЕ
        if(isArabicA!=isArabicB | int_a>10 | int_b>10)
            throw new Exception();

        switch (operator){
            case "+":
                if(isArabicA==1)
                    return String.valueOf(int_a+int_b);
                else
                    return ArabicToRoman(int_a+int_b);
            case "-":
                if(isArabicA==1)
                    return String.valueOf(int_a-int_b);
                else
                    return ArabicToRoman(int_a-int_b);
            case "*":
                if(isArabicA==1)
                    return String.valueOf(int_a*int_b);
                else
                    return ArabicToRoman(int_a*int_b);
            case "/":
                if(isArabicA==1)
                    return String.valueOf(int_a/int_b);
                else
                    return ArabicToRoman(int_a/int_b);
            default:
                throw new Exception();
        }
    }
}
