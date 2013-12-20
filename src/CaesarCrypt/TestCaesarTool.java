package CaesarCrypt;

public class TestCaesarTool {
	public static void main(String ages[]) {
		char c = 'Z';
		int key = -3;

		System.out.println("Before: " + c);
		c = CaesarTool.shiftChar(c, key);
		System.out.println("After:  " + c);
	}
}
