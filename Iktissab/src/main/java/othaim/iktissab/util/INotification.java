package othaim.iktissab.util;

public interface INotification {
	public void handleException(Exception e);
	public void handleResult(String jsonstring);
}
