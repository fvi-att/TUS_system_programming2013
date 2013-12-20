/*
 * @author fviAtt
 * @version 0.5  
 * 
 * 銀行システムATM画面
 */

package Bank;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**Bankシステムのうち、GUIでMyBankクラスを操作するためのクラス

 * @author fvi@
 * @verison 1.1
 *
 */
//@SuperWarningは気にしなくて良いです
@SuppressWarnings("serial")
public class AnserOfATMFrameView extends JFrame implements ActionListener, NotificationCenter {


	Container contentpane;
	
	JTextArea infotextArea;

	/**
	 * GUI上の情報を更新するメソッド
	 * 何らかしらの表示が終わった後GUIの情報を更新する際に呼び出す
	 * 
	 */
	public void modify_GUI_value() {
		//toString()を設定しておくと便利なことは何か？
		//this.setTitle("銀行システム    "+mybank.getAccount().toString());
		this.setTitle("銀行システム    UserID:" + MyBank.getAccountID() + "    残高："
				+ MyBank.getAccountAmount());
	}
	
	
	

	public void showResult(boolean result, String message) {
		if (result) {
			System.out.println("処理を完了させました");
			if (message != null) {
				System.out.println("message::" + message);
			}
		}
	}

	/**
	 * Frame内でのボタンの操作が行われた時呼び出されるメソッド
	 * ActionListenerをimplements(実装）することでオーバーライドされる
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * コードを見ることでどのようなコマンドで処理を呼び出すのか確認すること
	 * 
	 * arg0にどのようなデータが入っているのか？
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("受信ボタンコマンド:"+arg0.getActionCommand());
		String amount_string = null;
		
		switch(arg0.getActionCommand()){
		case "入金":
			 amount_string = JOptionPane.showInputDialog(this, "入金する金額を入力してください", "引き出し処理", JOptionPane.QUESTION_MESSAGE);
			
			//キャンセルボタンが押された時の回避処理
			if(amount_string == null){
				break;
			}
			
			if(MyBank.Deposit(Integer.parseInt(amount_string))){
				System.out.println("入金を行いました");
				infotextArea.append("\n[入金]"+amount_string+"\\の入金作業を行いました。");
				
			}else{
				System.err.println("入金に失敗しました");
				infotextArea.append("\\入金に失敗しました");
				
			}
		break;
		
			
		case "引き出し":
			 amount_string = JOptionPane.showInputDialog(this, "引き出す金額を入力してください", "引き出し処理", JOptionPane.QUESTION_MESSAGE);
			if(amount_string == null){
				break;
			}
			
			
			if(MyBank.Withdraw(Integer.parseInt(amount_string))){
				System.out.println("引き出しを行いました");
				infotextArea.append("\n[引き出し]"+amount_string+"\\の引き出し作業を行いました。");
				
			}else{
				System.err.println("引き出しに失敗しました");
				infotextArea.append("\n引き出しに失敗しました");
				
			}
			
			break;
		
		case "振込":
			String dst_ip = JOptionPane.showInputDialog(this, "送信先のipアドレスを入力してください", "振込", JOptionPane.QUESTION_MESSAGE);
			if(dst_ip == null){
				break;
			}
			
			String accountID = JOptionPane.showInputDialog(this, "振込先の口座IDを入力して下さい", "振込", JOptionPane.QUESTION_MESSAGE);
			if(accountID == null){
				break;
			}
			
			
			 amount_string = JOptionPane.showInputDialog(this, "送金する金額を入力してください", "振込", JOptionPane.QUESTION_MESSAGE);
			//キャンセル時の回避処理
			if(amount_string == null){
				break;
			}
			
			Integer transfer_amount = null;
			try{
				transfer_amount = Integer.parseInt(amount_string);
			}catch(NumberFormatException formatErr){
				//文字列をInteger型に変換することが出来ない場合。　例　r385など文字が含まれる場合など
				System.err.println("不正な文字列が振込作業中に入力されました");
				infotextArea.append("\n不正な文字列が入力されました作業を中断します");
				break;
			}
			
			String transfer_message = JOptionPane.showInputDialog(this, "振込先へのメッセージ", "振込", JOptionPane.QUESTION_MESSAGE);
			
			infotextArea.append("\n宛先："+dst_ip+",金額："+transfer_amount+",メッセージ："+transfer_message+"\nで振り込みます");
			if(MyBank.TransfarTo(dst_ip, accountID, transfer_amount, transfer_message)){
				infotextArea.append("\n振込が完了しました");
			}else{
				infotextArea.append("\n振込に失敗しました。");
			}
			
			break;
		
		case "口座確認":
			JOptionPane.showMessageDialog(contentpane, "残高:"+MyBank.getAccountAmount()+"円\n"+
													   "口座ID："+MyBank.getAccountID()+"\n"+
													   	"口座タイプ:"+MyBank.getAccountType()
													   	, "取引中の口座情報",JOptionPane.QUESTION_MESSAGE);
			
			break;
			
			
			
		}
		
		modify_GUI_value();
	}
/**
 * コンストラクタ
 * ATmViewクラスがインスタンス化されるときに呼び出される
 * 
 * 
 */
	public AnserOfATMFrameView() {
		//コンストラクタ
		super();

		//gui情報を更新
		modify_GUI_value();
		
		this.setSize(800,500);
		
		this.setLayout(new GridLayout(1,2));
		
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		leftPanel.setLayout(new GridLayout(4,1));
		
		rightPanel.setLayout(new GridLayout(1,1));



		JButton depositbutton = new JButton("入金");
		depositbutton.addActionListener(this);
		leftPanel.add(depositbutton);
		
		
		JButton withdrawbutton = new JButton("引き出し");
		withdrawbutton.setSize(100, 100);
		withdrawbutton.setLocation(50, 50);
		withdrawbutton.addActionListener(this);
		leftPanel.add(withdrawbutton);

		JButton transferbutton = new JButton("振込");
		transferbutton.setSize(100, 100);
		transferbutton.setLocation(50, 160);
		transferbutton.addActionListener(this);
		leftPanel.add(transferbutton);

		JButton statusButton = new JButton("口座確認");
		statusButton.setVisible(true);
		statusButton.addActionListener(this);
		leftPanel.add(statusButton);
		
		infotextArea =new  JTextArea(30,40);
		JScrollPane scrollpane = new JScrollPane(infotextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS , JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setVisible(true);
		
		rightPanel.add(scrollpane);
		
		contentpane = getContentPane();
		//レイアウトを実装するとFrameのサイズを可変にした時に対応したサイズにしてくれる。
		contentpane.setLayout(new GridLayout(1,2));
		contentpane.add(leftPanel);
		contentpane.add(rightPanel);
		
		this.addWindowListener(new ClosedListener());
		
		this.setVisible(true);
		
		//自身のIPアドレスを表示する
		try {
			infotextArea.append("このマシンのIPアドレス:：" + java.net.InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}


	/**
	 * mainメソッド
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//MyBankクラスに口座情報をセットする
		MyBank.initBankAccount(new GeneralBankAccount("sampleID", 40000));
		
		EventManager.Put("ATMView",new AnserOfATMFrameView());
		

	}
/**
 * (応用)通知センタ　イベント駆動型の処理を行う。
 * 受け取ったイベント情報をGUI上に反映させる
 * @param args[] args[0]　タイトル情報　args[1]　内容情報　(Object)
 */
	@Override
	public void NotificationCallfired(Object[] args) {
		// TODO 自動生成されたメソッド・スタブ 通知センターから呼び出しを受けた時
		//現時点は振込処理が完了した通知をATMFrameViewに行う。
		infotextArea.append("\n["+String.valueOf(args[0])+"]"+String.valueOf(args[1]));
		
	}


}



