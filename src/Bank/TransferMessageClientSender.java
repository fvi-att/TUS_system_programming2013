package Bank;

import RSA.RSACrypter;

public class TransferMessageClientSender {

	private String[] sendMessage;
	private int step = 0;

	/**
	 * コンストラクタ
	 * 
	 * 
	 * @param command
	 * @param amount
	 * @param message
	 */
	public TransferMessageClientSender(String command, String account, int amount, String message) {
		sendMessage = new String[5];
		sendMessage[0] = BankTransferConfiguration.establish_connectionWord+":"+RSACrypter.getPublicKey();
		sendMessage[1] = command;
		sendMessage[2] = account;
		sendMessage[3] = String.valueOf(amount);
		sendMessage[4] = message;

		step = 0;
	}

	/**
	 * 
	 * @param input
	 */
	public void InputMessage(String input) {
		if ((!input.matches(BankTransferConfiguration.OK) || step == -1)) {
			step = -1;
			return;
		}

		step++;
	}

	/**
	 * 
	 * @return
	 */
	public String OutputMessage() {
		if (step == -1)
			return BankTransferConfiguration.QUIT;
		return sendMessage[step];
	}

	/**
	 * mainメソッド、確認用のメソッド
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
