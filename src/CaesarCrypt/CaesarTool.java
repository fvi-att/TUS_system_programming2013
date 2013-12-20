package CaesarCrypt;

public class CaesarTool {

    
    public static char shiftChar(char c, int key) {
        if(c >= 'A' && c <= 'Z') c = rotate(c,key);
        return c;
    }

   
    public static char rotate(char c, int key) {
        key = key %26;
        int n = (c - 'A' + key) %26;
        if(n < 0) n = n + 26;
        return (char) (n+'A'); 
    }
}