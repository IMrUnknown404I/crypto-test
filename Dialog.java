package crypt;
//import java.util.Arrays;
//import java.util.Base64;
//import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class Dialog 
    {
        static Scanner quest = new Scanner(System.in, "CP1251");  //  EN   T   I
        static Scanner intscan = new Scanner(System.in, "CP1251"); //  TER  H   N
        static Scanner txtscan = new Scanner(System.in);            //  ING  E   FO
        
        static String text = new String ("Unicode is a computing industry standard designed to consistently\n"
                + "and uniquely encode characters used in written languages throughout the world.\n"
                + "The Unicode standard uses hexadecimal to express a character.\n"
                + "For example, the value 0x0041 represents the Latin character A.\n"
                + "The Unicode standard was initially designed using 16 bits to encode characters because the primary machines were 16-bit PCs.\n"
                + "When the specification for the Java language was created, the Unicode standard was accepted and the char primitive was defined as a 16-bit data type,\n"
                + "with characters in the hexadecimal range from 0x0000 to 0xFFFF."); //main txt for shift
        
        static String alfavit = ".:Q;WE1RTYU2IOP3ASDF!GH4JKLZ5XCV6BNM?qw7ert8yui9opas-dfghj0klzxc=vbnm #"; //an alfavite for shift-method
        static String key = new String ("Secret"); //the crypto-key for secret-using method
        
    public static void main(String[] args) throws IOException
    {
        //byte[] important = encode(text, key);
        System.out.print("Добро пожаловать в шифровальную машину Энигму! \nКакой текст будем обрабатывать: заготовленный (1) или ваш (2) ?  ");
        int text_in = intscan.nextInt(); //1 --- 2
        if(text_in == 2)
        {
            System.out.print("Введите текст: ");
            String text_new = txtscan.nextLine(); //1 +++
            text = text_new;
            System.out.println();
        } else if(text_in != 1){ throw new IllegalArgumentException("ВВЕДЕНЫ НЕВЕРНЫЕ ВХОДНЫЕ ДАННЫЕ"); }
        System.out.print("Теперь выберите метод шифрования: метод сдвига (1) или метод, исп. секретное слово (2) ?"
                + "\nВыбранный метод: ");
        int a = text.length();
        int metod = intscan.nextInt(); //2 +++
        System.out.println();
            switch (metod) 
            {
                case 1:
                    sdvig(a);
                    break;
                case 2:
                    secret();
                    break;
                default:
                    throw new IllegalArgumentException("ВВЕДЕНЫ НЕВЕРНЫЕ ВХОДНЫЕ ДАННЫЕ");
            }
        System.out.println();
    }
    
    public static void secret() throws IOException//при выборе метода шифрования с секретным словом
    {
        System.out.print("Использовать секретное слово по умолчанию (Secret)? yes/no :  "); //определение секретного слова
        String opred = quest.next(); //3 +++
        if(opred.compareTo("yes") == 0) //дефолт
        {
            System.out.println();
            System.out.println("Исходный текст: " + text + "\n");
            System.out.println("Закодированные текст: " + Crypt.encode(text, key).toString());
        }
        else if(opred.compareTo("no") == 0)//новое исходное слово
        {
            System.out.print("Введите секретное слово/текст: ");
            String key_dop = txtscan.nextLine(); //3 --- 2
            if (key_dop.trim().equalsIgnoreCase("")) { throw new IllegalArgumentException("ВВЕДЁН ПУСТОЙ КЛЮЧ"); } //проверка на пустой ключ 
            System.out.println();
            System.out.println("Исходный текст: " + text + "\n");
            System.out.println("Закодированный текст: " + Crypt.encode(text, key_dop).toString());
        }
        else//при чем-то левом
        {
            System.out.println("\nОШИБКА: введены неверные данные!");
            System.exit(0);
        }
        System.out.printf("\nДекодированный текст: " + Crypt.decode(Crypt.encode(text, key), key));
        System.out.println();
    }
    
    public static void sdvig(int a) throws IOException//при выборе метода сдвига
    {
        System.out.print("На какое кол-во позиций делаем сдвиг? ");
        int move = intscan.nextInt(); if(move >= a) {                                              //проверка на сдвиг свыше длины текста => ошибка при шифровке
                                                    //while (move>a){ move = a%move; } 
                                                    throw new IllegalArgumentException("СДВИГ ЧИСЛО, БОЛЬШЕЕ ДЛИНЫ ТЕКСТА!");
                                                    }
        System.out.printf("Работаем со сдвигом на %d элементов!\n\n", move); //2 --- 1
        System.out.println("Исходный текст: " + text);
        System.out.println("\nЗакодированный текст: " + Crypt.encrypt(text.getBytes(), move));
        System.out.printf("Декодированный текст: " + Crypt.decrypt(Crypt.encrypt(text.getBytes(), move).getBytes(), move));
        System.out.println();
    }
    
    public static class Crypt {
        
    private static String encrypt(byte[] text, int move) throws IOException //кодирование со сдвигом 
    {
        char[] result = new char[text.length];
        for(int i=0; i<text.length; i++)
        {
            if(alfavit.indexOf(text[i])!=-1) 
            {
                if(alfavit.indexOf(text[i])+move >= alfavit.length())
                {
                    int delta = Math.abs(alfavit.length() - (alfavit.indexOf(text[i])+move));
                    result[i]=(alfavit.charAt(delta));   
                }else 
                    result[i]=alfavit.charAt(alfavit.indexOf(text[i])+move);
            } else
                result[i]=(char)text[i];
        }
        return new String(result);
     }
        
    private static String decrypt(byte[] result, int move) throws IOException //декодирование после сдвига
    {
        //System.out.println(alfavit.indexOf('Q'));
        //System.out.println(alfavit.indexOf('B'));
        System.out.println();
        char[] itog = new char[result.length];
        for(int i=0; i<result.length; i++)
        {
            if(alfavit.indexOf(result[i])!=-1) 
            {
                if(alfavit.indexOf(result[i]) - move < 0)
                {
                    int delta = alfavit.length() - Math.abs(alfavit.indexOf(result[i])-move);
                    //if(alfavit.charAt(alfavit.indexOf(result[i])) == '.'){System.out.println("sdvig s . at position: "+delta);}
                    itog[i]=(alfavit.charAt(delta));   
                }else
                    itog[i]=alfavit.charAt(alfavit.indexOf(result[i])-move);
            } else
                itog[i]=(char)result[i];
        }
    return new String(itog);
    }
    
    public static String encode(String secret, String key) throws IOException
    {
	byte[] btxt = secret.getBytes();
	byte[] bkey = key.getBytes();

	byte[] result = new byte[secret.length()];

	for (int i = 0; i < btxt.length; i++) {
		result[i] = (byte) (btxt[i] ^ bkey[i % bkey.length]);
	}
        String res= new String(result);
	return new String(res.getBytes(), "UTF-8");
        //return new String(result);
    }
    
    public static String decode(String secret, String key) throws IOException
    {
        int leng = secret.length();
	byte[] result = new byte[leng];
        byte[] btxt = secret.getBytes();
	byte[] bkey = key.getBytes();

	for (int i = 0; i < btxt.length; i++) {
		result[i] = (byte) (btxt[i] ^ bkey[i % bkey.length]);
	}
	return new String(result);
    }
    
    public static String translate(String text) throws IOException
    {
        String result = new String(text.getBytes(), "UTF-8");
        return result;
    }
            
//    public static byte[] encrypt(byte[] text, byte[] keyWord) //кодирование с секретным словом
//        {
//            byte[] result = new byte[text.length];
//            for(int i = 0; i< text.length; i++) 
//                    result[i] = (byte) (text[i] ^ keyWord[i % keyWord.length]);
//            return result; 
//        }
//    public static String decrypt(byte[] text, byte[] keyWord) //декодирование с секретным словом
//    {
//        byte[] result = new byte[text.length];
//        for(int i = 0; i < text.length;i++) 
//                result[i] = (byte) (text[i] ^ keyWord[i% keyWord.length]);
//        return new String(result);
//    }
  }
}

//for(int i=0; i<main.length; i++)
//        if((int)main[i]>=1040 && (int)main[i] <=1071)
//        {
//            if((int)main[i]+move>1071)
//            {
//                delta = (int) main[i] + move;
//                System.out.print(delta);
//                xxx = delta - 1071;
//                System.out.print(xxx);
//                result[i]=(char) (1040+xxx);
//            }
//        }
//        else
//        { result[i]=(char)(main[i]+move); }