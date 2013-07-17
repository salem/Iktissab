package othaim.iktissab.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class HttpJson extends AsyncTask<Notifier, Void, String>{
	private Exception exception;
	
	private Notifier notifier;
	@Override
	protected String doInBackground(Notifier... params) {
		this.notifier = params[0];
		try{
			
			DefaultHttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(IktissabConstant.SERVICE_URL);
			post.setHeader("Accept","*/*");
			post.setHeader("Accept-Encoding", "gzip, deflate");
			post.setHeader("Accept-Language", "en-US,en;q=0.5");
			post.setHeader("Cache-Control",	"no-cache");
			post.setHeader("Connection", "keep-alive");
			//post.setHeader("Content-Length","67");
			post.setHeader("Content-Type","text/json; charset=UTF-8");
			post.setHeader("input-content-type", "text/json");
			post.setHeader("output-content-type", "text/json");
			post.setHeader("language", "en");
			
			StringEntity str = new StringEntity(this.notifier.jsonString);
			
			post.setEntity(str);
			HttpResponse response = client.execute(post);
			
			InputStream is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"), 8);
			
			StringBuilder sb = new StringBuilder();
			
			String line;
			while((line = reader.readLine()) != null){
				sb.append(line+"\n");
			}
			
			Log.i("HttpRequest", notifier.jsonString);
			Log.i("HtppResponse", sb.toString());
			Log.i("HtppResponse", response.toString());
			Log.i("HtppResponse", "Status = " +response.getStatusLine().getStatusCode()+ " Description: "+response.getStatusLine().getReasonPhrase());
			return sb.toString();
			
		}			
		catch(Exception e){
			this.exception = e;
			return null;
		}			
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(this.exception !=null){
			this.notifier.activity.handleException(this.exception);
		}
		else{
			this.notifier.activity.handleResult(result);
		}
		
	}
	
	
}
