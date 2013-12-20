package Bank;


/**
 * 普通銀行口座クラス
 * @author fvi@
 * @since 2013/04
 * 
 * このクラスは普通預金口座クラスを作成します。
 * @param type:general 口座情報
 * 普通預金口座は以下の仕様です
 * 1.　預金額はマイナスにできない。
 * 2.　200000\以上は入金できない
 * 
 * 
 */
public class GeneralBankAccount extends AbstractAccount {
//大まかな変数定義はAbstractAccountにて定義して有ります

	public GeneralBankAccount(String accountId, int init_cash) {
		
		super(accountId, init_cash);
		//銀行口座のタイプをgeneralに指定
		super.setAccountType("general");
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *@param withdraw_cash 引き出し額
	 * 
	 * syncronized処理を施す
	 */
	@Override
	public synchronized boolean Withdraw(int withdraw_cash) {
		// TODO Auto-generated method stub
		// この銀行講座では引き出しが預金額を上回るときfalseを出力する。
		try {
			if(withdraw_cash < 0)
				return false;
			
			if (super.getCashAmount() < withdraw_cash)
				return false;
			
				//引き出し処理
				super.cashAmount -= withdraw_cash;
				
			return true;
		} catch (Exception err) {
			return false;
		}
	}
	/**
	 *@param deposit_cash 入金額(<20万)
	 * 普通預金口座は　一回あたり20万までしか入金できない
	 * syncronized処理を施す
	 */
	
	@Override
	public synchronized boolean Deposit(int deposit_cash) {
		// TODO Auto-generated method stub
		try{
			if(deposit_cash < 0)
				return false;
			//general口座では200000\以上は入金できないというふうにしてある
			if(deposit_cash > 200000)
				return false;
			
			super.cashAmount += deposit_cash;
			return true;
			
		}catch(Exception err){
			return false;
		}
		
	}

	
	/*同じメソッド名でも異なる引数を取る場合は別のメソッドとして処理することができる。
	 * 
	 * @see Bank.AbstractAccount#Withdraw(int, java.lang.String)
	 */
	@Override //通常の引き出しにコメントを付加する場合
	public synchronized boolean Withdraw(int withdraw_cash, String message) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	@Override //通常の預入にコメントを付加する場合　
	public synchronized boolean Deposit(int deposit_cash, String message) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	
	
	
	//以下junitテストコード
	public void testGeneralAccount(){
		assert new GeneralBankAccount("testID", 40000).Deposit(30000000) == false;
	}

}

