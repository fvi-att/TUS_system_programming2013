package Bank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * (応用)取引の際に領収書データを作成し特定のフォルダに保存を試みます。
 * 
 * 
 * 
 * 
 * @version 1.0
 * @author Iwai_7
 * 
 */
public class Recipt {

	String title = null;

	SimpleDateFormat timeformat = null;
	Date time = null;

	AbstractAccount account = null;

	HashMap<String, Integer> transaction = null;

	String text = null;

	String comment = null;

	public Recipt() {
		title = "領収書";
		timeformat = new SimpleDateFormat("yyyy'年'MM'月'dd'日'HH'時'mm'分'ss'秒'SSS");
		transaction = new HashMap<String, Integer>();

		comment = "";

		time = new Date();

	}

	public void addTransaction(HashMap<String, Integer> transactions) {
		transaction.putAll(transactions);

	}

	public void addTransaction(String name, int amount) {

		transaction.put(name, amount);

	}

	public void setTime(Date fromTime) {
		time = fromTime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTime(SimpleDateFormat time) {
		this.timeformat = time;
	}

	public void setComment(String comment) {
		if (comment == null)
			return;
		this.comment = comment;
	}

	public void setAccount(AbstractAccount account) {
		this.account = account;
	}

	/**
	 * オブジェクトからの情報を元に領収書の文書を作成します
	 * 
	 */
	private void BuildText() {
		text = new String("-------------" + title + "-----------\n");

		text += timeformat.format(time) + "\n\n";

		text += "-------------取引内容-----------\n";

		Iterator<String> ite = transaction.keySet().iterator();

		while (ite.hasNext()) {
			String name = ite.next();

			text += "  " + name + ":" + transaction.get(name) + "\n";

		}

		if (account != null) {
			text += "\n\n------------------------------\n";
			text += "残高　　　:" + account.getCashAmount() + "\n";

		}
		// 備考欄にコメントの追加機能を実装
		text += "**************************備考**************************\n\n"
				+ comment + "\n\n\n\n\n\n\n\n\n TCBC-BankSystem";

	}

	private boolean Print_Recipt(){
		
		this.BuildText();
		
		try{
			File directory = new File("./Recipt");
			 if(!directory.exists())
				 directory.mkdir();
			 
			 
			 
				 File reciptFile = null;
				 String file_path = "./Recipt/"+timeformat.format(time);
				
				 
				 
				 //同時書込み防止処理[TODO]
				 if(new File(file_path).exists()){
					 String addWord = "("+String.valueOf(1)+")";
					 reciptFile = new File(file_path+addWord+".txt");
				 }else{
					 reciptFile = new File(file_path + ".txt");
				 }
				 
				 
			 
			 FileWriter writer = new FileWriter(reciptFile);
			 
			 writer.write(text);
			 
			 writer.close();
			 
			}catch(IOException e){
			  System.out.println(e);
			  return false;
			  
			}
		
		return true;
	}
	
	public static synchronized void Print(Recipt recipt){
		recipt.Print_Recipt();
	}

	
	


}
