package Bank;


import java.io.IOException;
import java.net.ServerSocket;
/**
 * 
 * このクラスはサーバソケットの管理を行いマルチスレッド化を行うことで一度に複数の受信に耐えられるように
 * 設計してあります
 * @inheritDoc BankTransferConfiguration
 * 
 * 
 * 
 * @since 2013/06/04
 * @author fvi
 * @version 1.00
 * 
 *
 * 
 * @see ServerSocket
 * 
 * 
 *
 */
public class ServerSocketManager extends Thread implements BankTransferConfiguration, NotificationCenter{

	ServerSocket serverSocket = null;
	
	public ServerSocketManager(){
		//これは難しいので無視して構わない
		EventManager.Put("server_manager", this);
	}
	
	/**
	 * Threadクラスからオーバライドしたメソッド
	 * ここでマルチスレッドで処理したい内容を記述する
	 * 
	 */
	public void run(){
		
		
		/*
		 * もしかしたらサーバソケットを構築できないかもしれない
		 * と考えると例外処理は必要と考える.
		 * 想像力を働かせてどのような問題が起こりうるか考える
		 * 
		 */
		try {
			/*
			SERVER_PORTはBankTransferConfigurationインターフェースは一括して通信に必要な設定を管理する。
			これによってこのインターフェースを実装することで設定情報を一括で取得することが出来る。
			*/
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("[ServerManager]サーバソケットを生成しましたポート番号："+String.valueOf(SERVER_PORT)+"で待機します");
			
		}  catch (IOException already_used){
			
			System.out.println("[ERROR]サーバの起動に失敗しました。ポート"+BankTransferConfiguration.SERVER_PORT+"がすでに使われているようです。"+
								"振込処理は行えません\n振り込みを行う場合システムの再起動を行なって下さい");
			String[] err_messageToView = {"[エラー]","サーバソケットの初期化に失敗しました。システムの再起動を行なって下さい"};
			EventManager.fireEvent("ATMView", err_messageToView);
			return;
			
		}
		
		while(true){
			
			try {
				
				BankServer bankServer = new BankServer(serverSocket.accept());
				new Thread(bankServer).start();
				System.out.println("[Server]接続を受けました");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * このメソッドは授業の範囲から外れるが通知センターの概念を用いている
	 * (非 Javadoc)
	 * @see Bank.NotificationCenter#NotificationCallfired(java.lang.Object[])
	 */
	@Override
	public void NotificationCallfired(Object[] args) {
		// TODO 自動生成されたメソッド・スタブ
		if((String.valueOf(args[0]).matches("closed"))){
			System.out.println("closing ServerSocket @ServerSocketManager");
			if(serverSocket.isClosed()){
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}
		
	}
}
