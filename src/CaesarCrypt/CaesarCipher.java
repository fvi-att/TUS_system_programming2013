package CaesarCrypt;

public class CaesarCipher { 
    public static void main(String args[]) {
        int key = 16;
        String org = "CONGURATULATION!";
       
        // ˆÃ†‰»
        String cry = encrypt(org,key);
        System.out.println("ˆÃ†‰»Œ‹‰ÊF" + cry);

        // •œ†
        String recover = decrypt(cry,key);
        System.out.println("•œ†Œ‹‰ÊF" + recover);
    }

    // String‚ÌˆÃ†‰»
    public static String encrypt(String s, int key) {
        String value = "";
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            c = CaesarTool.shiftChar(c,key);
            value = value + String.valueOf(c);
        }
        return value;
    }

    // String‚Ì•œ†‰»
    public static String decrypt(String s, int key) {
        return encrypt(s, -key);
    }
}