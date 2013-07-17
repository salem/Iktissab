package othaim.iktissab.util;


public class Notifier {

	public NotificationActivity activity;
	public String jsonString;
	public Notifier(NotificationActivity activity, String json){
		this.activity = activity;
		this.jsonString = json;
	}
}
