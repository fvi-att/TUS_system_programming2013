package Bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;



/**BankSocketクラスは振込などの外部にデータを送信する際に呼び出されるクラスです。
 * 
 * 
 * 
 * @author fvi@
 * @version 0.51
 * 
 * 
 * @param SERVER_PORT TCP/IPソケット通信上で用いる統一ポート番号 15000<BR>
 * 
 * 
 */
public class BankSocket implements BankTransferConfiguration {


	Socket socket;

	/** BankSocketクラスはBankServerクラスに対してTCP/IP通信を試みます。
	 * 各種コマンドはBankServerクラス参照のこと
	 * @param dst_ip 送信先のipアドレス
	 * @param money 送金額
	 * @param message 送信するメッセージ
	 * 
	 * 
	 * @see BankServer
	 * @see BankTransferConfiguration
	 * 
	 */
	public BankSocket(String dst_ip, String account, int money, String message) {
		/*サーバにどのようなメッセージを送信するか決定するオブジェクト
		*ソケットごとに作成され、サーバからの返答によって次にどのようなメッセージを
		*送信するか決定する
		*/
		TransferMessageClientSender sender = new TransferMessageClientSender("send",account,money,message);
		
		
		
		System.out.println("接続開始");
		try {
			
			
			
			socket = new Socket(dst_ip, SERVER_PORT);
			/*
			 * NetworkInterface ni = NetworkInterface.getByName("wlan0");
				socket.bind(bindpoint)
			*/
			
			
		    /* 準備：データ入力ストリームの定義--ソケットからデータを
		       取ってくる．sok → in  */  
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
			/* 準備：データ出力ストリームの定義--ソケットにデータを
		       書き込む．  sok ← out */

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					socket.
					getOutputStream()));
			
			//最初の接続の振込み処理開始を告げるメッセージ　ここから
			
			writer.write(sender.OutputMessage());
			writer.newLine();
			writer.flush();
			//ここまで

			System.out.println("送信完了");
			
			
			//ここから先　実際のサーバとの通信
			
			
			while (true) {

				// 送信先からの戻り値を受信
				System.out.println("メッセージ受信中");
				String response = reader.readLine();
				System.out.println("受信したメッセージ： " + response);
				sender.InputMessage(response);
				
				
				//サーバへ送信するメッセージの生成
				String q_command = sender.OutputMessage();
				writer.write(q_command);
				writer.newLine();
				writer.flush();
				
				if(q_command.matches(BankTransferConfiguration.QUIT)){
					break;
				}
				
				
			}
			
		
			//以降、通信処理の後片付け
			System.out.println("[Socket]転送システムを終了します");

			reader.close();
			writer.close();

			socket.close();
			
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("通信ソケットの確立に失敗しました。FWの設定or すでにポートが専有されているかも");
			e.printStackTrace();
		}

	}

	/**main ではlocalhost =127.0.0.1にアクセスします。
	 * @param dest_ip = 'localhost'
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//localhostに引数指定した金額の送信を行う
		new BankSocket("localhost", "sampleID", Integer.parseInt(args[1]),"test送金処理from localhost");

	}

}
