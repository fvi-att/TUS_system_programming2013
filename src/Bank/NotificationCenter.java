package Bank;
/**
 * 
 * 
 * (応用)通知センターインターフェース
 * 
 * インターフェースの具体的な用法について説明します
 * @author fvi@
 * @version 1.0
 *
 */
public interface NotificationCenter {
	
	/**
	 * 
	 * このインターフェースを実装するクラスはこのメソッドを必ず実装する必要があります
	 * @param args
	 */
	public void NotificationCallfired(Object[] args);

}
