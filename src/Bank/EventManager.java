package Bank;

import java.util.HashMap;
/**
 * このクラスについての授業は行わないが通知センターとしてイベントを管理しイベント駆動型のプログラミングが行えるようにしている
 * 
 * 
 * 
 * @author Iwai_7
 *
 */
public class EventManager {
	
	private static HashMap<String,NotificationCenter> map = new HashMap<String,NotificationCenter>();
	
	
	public static void Put(String key,NotificationCenter event){
		map.put(key, event);
	}
	
	public static void RemoveEvent(String key){
		map.remove(key);
		
	}
	
	public static void fireEvent(String key,Object[] obj){
		if(map.get(key) != null)
			map.get(key).NotificationCallfired(obj);
	}

}
