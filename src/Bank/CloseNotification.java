package Bank;

import java.io.IOException;
import java.util.EventListener;
/**
 * (応用)システムの終了を全体へ通知するためのプログラム
 * 
 * 
 * 
 * @author fvi@author Iwai_7
 * @version 1.0
 *
 */
public interface  CloseNotification extends EventListener {
	
	public void whenCloseSystem() throws IOException;
	
}
