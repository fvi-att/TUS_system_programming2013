package Bank;


/**このインターフェースは複数のBankシステムが通信を行う際の仕様を共通化するための
 * ”仕様の強制”を行うインターフェースです。定数などの情報をばらばらに書くのではなく、一定の場所に
 * 集めて管理することで書きやすいプログラムを作成します
 * 
 * 
 * 
 * @author fvi@author Iwai_7
 * @version 1.0
 *
 */
public interface BankTransferConfiguration {
	/**TCP/IP通信を行う際のBankシステムが用いるポート番号
	 * 
	 */
	final public int SERVER_PORT = 15000;
	/**
	 * 
	 * 通信を行う際の最初の合言葉、シェイクハンドコマンド
	 * 
	 */
	final public String establish_connectionWord ="BankTransfer";
	
	/**最初のコネクションのメッセージも含めて5つのコマンドを一回のやりとりで行うと定義
	 */
	final public int message_size = 5;
	
	
	/**通信メッセージの内容
	 * 内容に問題がなく次のコマンドを求めるときに送信するコマンド
	 * 
	 */
	final public String OK = "OK";
	
	/**通信メッセージの内容
	 * 予期しないエラーが発生したため強制的に終了させるときに送信するコマンドabort バルスコマンド
	 * 
	 */
	final public String ABORT = "abort";
	
	
	/**通信メッセージの内容
	 * 正常に取引を終了させるときに送信するコマンド
	 * 
	 */
	final public String QUIT = "quit";
	
	

}
