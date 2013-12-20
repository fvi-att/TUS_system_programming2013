

package Bank;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BankServerクラスは相手とのデータのやり取りを実際に行うクラスオブジェクトを生成します。
 * ここからマルチスレッドの勉強なども必要になると思いますので頑張ってください。
 * @author fvi@
 * 
 * @version 0.5
 */
public class BankServer extends Thread {
	public final static int SERVER_PORT = BankTransferConfiguration.SERVER_PORT;

	//static ServerSocket ss ;
	private Socket act_socket = null;
	
	private BankTransferParser parser = null;

	/**
	 * StartCommunicationメソッドは１体１のTCP/IP通信を行うシンプルなメソッドです。
	 * ここでメッセージを受信をおこないメッセージの解析を行い様々な処理を行います。
	 * 
	 * メッセージ quit:通信を終了させます
	 * 
	 * 
	 * @see MyGeneralBank
	 * 
	 * @see BankSocket
	 * @see TransferMessageClientSender
	 */

	public BankServer(Socket accept) {
		//コンストラクタ
		// TODO Auto-generated constructor stub
		this.act_socket = accept;
		
		
		this.parser = new BankTransferParser();
		
		parser.setFrom(accept.getInetAddress().toString());
	}
	
	void OutputLog(String output){
		System.out.println(output);
		String[] messageToView = {"[Server]",output};
		EventManager.fireEvent("ATMView", messageToView);
	}
	
	/**BankSocketクラスに対して予め設計した通信プロトコルを用いてデータの送受信を試みる
	 * 
	 *通信内容を確認して他の機能も追加できるようにしよう。
	 */
	
	public void StartCommunication() {
		

		
		try {
			//Socket socket = ss.accept();
			System.out.println("[Server]"+act_socket.getInetAddress() + "から接続を受けました");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					act_socket.getInputStream()));

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					act_socket.getOutputStream()));

			while (true) {

				String inMes; 
				
				// 受信するまで待機
				inMes = reader.readLine();
				
				//エンコード文字列対策
				OutputLog("[Server]getMessage:" +inMes);

				// 向こうから終了するようにきたら終了させる 終了コマンドは"quit"
				if (inMes.matches(BankTransferConfiguration.QUIT) || 
						inMes.matches(BankTransferConfiguration.ABORT)) {
					break;
				}
				//まずデータを解釈し次にどのような動作を行うかをきめる。
				//キューにメッセージを追加
				
				
				String outMes =parser.ParseMessagefromClient(inMes);
				writer.write(outMes);
				writer.newLine();
				writer.flush();
				
				OutputLog(outMes);
				//System.out.println("[Server]sendMessage:" + out_mes);

			}

			System.out.println("[Server]終了コマンドを受信しました\nサーバソケットを終了させます");
			
			
			reader.close();
			writer.close();
			act_socket.close();
			
			
						

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[Server]メッセージ通信に失敗しました\n再度やり直してください");
			e.printStackTrace();
		}

	}

	/**BankServerクラスは　Threadクラスを継承しており丘のオブジェクト及びクラスからnew BankServer().start()で
	 * 開始処理を呼び出されるとマルチスレッド呼び出し、実行が行われる
	 * 
	 * 
	 * (非 Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		StartCommunication();

	}

	/**main関数
	 * @param args
	 * @throws IOException 
	 */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub デバッグ用
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
		new Thread(new BankServer(serverSocket.accept())).start();

	}
	/**
	 * 通信に問題が発生した場合このメソッドが呼び出されます
	 * 
	 * 
	 * @throws IOException
	 */
	public void FinishServer() throws IOException {
		System.out.println("通信を終了させます。To:"+act_socket.getInetAddress().toString());
		
	}

}
