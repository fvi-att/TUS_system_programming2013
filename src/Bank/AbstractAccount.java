package Bank;

import java.util.Arrays;

/**抽象的な銀行口座アカウントクラス
 * このクラスは様々な銀行口座の親となります。
 * このクラスを通して基本的な処理を定義するメソッドの定義を行います。
 * 
 * @author fvi@
 *
 */

public abstract class AbstractAccount {
	//継承先では書き換えができるようにする。
	//protectedは継承先ではアクセスすることが出来る。アクセス修飾子無記の場合よりも強く、privateよりも弱い制限力を持つ
	protected int cashAmount;
	private String accountID;
	private String accountType;
	
	/**
	 * Getterメソッド
	 * 
	 * 
	 * @return　預金金額のint値
	 */
	int getCashAmount() {
		return cashAmount;
	}
	
	/**
	 * Getterメソッド
	 * 
	 * 
	 * @return　ユーザIDのString値
	 */
	 String getAccountID() {
		return accountID;
	}
	 
		
	/**
		 * Getterメソッド
		 * 
		 * 
		 * @return　講座の種類
		 * @see setAcountType
		 */
	 String getAccountType() {
		return accountType;
	}
	 
	/**
	 * 銀行講座の種類変更メソッド
	 * @param type 講座の種類を定義する　"general","special","other"から選択
	 * @return
	 */
	protected boolean setAccountType(String type){
		 //銀行口座の種類は以下の３つに限定する
		 String[] bank_type = {"general","company","government","other"};
		 
		 if(Arrays.asList(bank_type).contains(type)){
			 accountType = type;
			 return true;
		 }
			 
		 return false;
		 
	 }
	
	/**
	 * 抽象メソッド群 お金のやり取り
	 * @param withdraw_cash
	 * @return 処理が成功したかの boolean値
	 */
	public abstract boolean Withdraw(int withdraw_cash);
	
	/**
	 * 抽象メソッド群 お金のやり取り　多重定義メソッド
	 * @param withdraw_cash
	 * @param message 引き出しに対して備考、コメントを与える
	 * @return 処理が成功したかの boolean値
	 */
	public abstract boolean Withdraw(int withdraw_cash,String message);
	/**
	 * 抽象メソッド群　預金処理
	 * @param deposit_cash　預金金額
	 * @return　処理が成功したかのboolean値
	 */
	public abstract boolean Deposit(int deposit_cash);
	
	/**
	 * 抽象メソッド群 預金処理　多重定義メソッド
	 * @param withdraw_cash 預金金額
	 * @param message 預金処理に対して備考、コメントを与える
	 * @return 処理が成功したかの boolean値
	 */
	public abstract boolean Deposit(int deposit_cash,String message);
	
	
	/**
	 * 
	 * toStringメソッドはObjectクラスから継承される。
	 * これはこのオブジェクトに関する情報をString値の文字列にして返す
	 * (非 Javadoc)
	 * @see java.lang.Object#toString()
	 * @return 講座のuserID、預金残高、を連結した文字列で返す
	 */
	@Override
	public String toString() {
		return "userID:"+ getAccountID()+", 残高:"+getCashAmount()+"\\";
	}
	
	
	/**
	 * コンストラクタ
	 * @param accountId　アカウントに与えるString値で与えるID
	 * @param init_cash 口座オブジェクトを作成する際に定義する初期預金金額
	 */
	public AbstractAccount(String accountId,int init_cash){
		this.accountID  = accountId;
		this.cashAmount = init_cash;
		
		accountType = "normal";
		
		
	}


}
