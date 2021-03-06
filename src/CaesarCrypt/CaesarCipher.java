package CaesarCrypt;

public class CaesarCipher { 
    public static void main(String args[]) {
        int key = 16;
        String org = "CONGURATULATION!";
       
        // 暗号化
        String cry = encrypt(org,key);
        System.out.println("暗号化結果：" + cry);

        // 復号
        String recover = decrypt(cry,key);
        System.out.println("復号結果：" + recover);
    }

    // Stringの暗号化
    public static String encrypt(String s, int key) {
        String value = "";
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            c = CaesarTool.shiftChar(c,key);
            value = value + String.valueOf(c);
        }
        return value;
    }

    // Stringの復号化
    public static String decrypt(String s, int key) {
        return encrypt(s, -key);
    }
}