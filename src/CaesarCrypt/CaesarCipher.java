package CaesarCrypt;

public class CaesarCipher { 
    public static void main(String args[]) {
        int key = 16;
        String org = "CONGURATULATION!";
       
        // �Í���
        String cry = encrypt(org,key);
        System.out.println("�Í������ʁF" + cry);

        // ����
        String recover = decrypt(cry,key);
        System.out.println("�������ʁF" + recover);
    }

    // String�̈Í���
    public static String encrypt(String s, int key) {
        String value = "";
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            c = CaesarTool.shiftChar(c,key);
            value = value + String.valueOf(c);
        }
        return value;
    }

    // String�̕�����
    public static String decrypt(String s, int key) {
        return encrypt(s, -key);
    }
}