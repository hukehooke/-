package cn.hukecn.speechbrowser.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.GeolocationPermissions.Callback;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.hukecn.speechbrowser.util.ToastUtil;

public class CutWebView extends WebView {

	ReceiveHTMLListener listener = null;
	String cookieStr = "";
	Context context = null;
	String instantUrl = "";
	ShouldOverrideUrlListener mShouldOverrideUrlListener = null;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) 
		{
			if(listener != null)
				listener.onReceiveHTML(instantUrl,(String)msg.obj);
		};
	};
	private ProgressBar progressbar;
	@SuppressLint("SetJavaScriptEnabled")
	public CutWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,0,0));
        addView(progressbar);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewCLient());
        WebSettings settings = getSettings();
        settings.setAllowFileAccess(true);
//        settings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        settings.setUserAgentString("Mozilla/5.0 (Linux; U; Android 5.1.1; zh-cn; PLK-UL00 Build/HONORPLK-UL00) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.3 Mobile Safari/537.36");
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(true); 
        addJavascriptInterface(new JSLinster(),"HTML");
        loadUrl("http://m.baidu.com");
	}
	
	

	public void setOnReceiveHTMLListener(ReceiveHTMLListener listener){
		this.listener = listener;
	}

	 public class WebChromeClient extends android.webkit.WebChromeClient {
	        @Override
	        public void onProgressChanged(WebView view, int newProgress) {
	            if (newProgress == 100) {
	                progressbar.setVisibility(GONE);
	            } else {
	                if (progressbar.getVisibility() == GONE)
	                    progressbar.setVisibility(VISIBLE);
	                progressbar.setProgress(newProgress);
	            }
	            super.onProgressChanged(view, newProgress);
	        }
	        
//	        @Override
//	        public void onReceivedTitle(WebView view, String title) {
//	        // TODO Auto-generated method stub
//	        	ToastUtil.toast(title);
//	        	view.loadUrl("javascript:window.HTML.getHtml(document.getElementsByTagName('html')[0].innerHTML);");
//
//	        super.onReceivedTitle(view, title);
//	        }
	        
	        @Override
	        public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
	        // TODO Auto-generated method stub
	        	callback.invoke(origin, true, false);
	        	super.onGeolocationPermissionsShowPrompt(origin, callback);
	        }

	    }
	 
	 public class WebViewCLient extends WebViewClient{
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            if(mShouldOverrideUrlListener != null)
	            	mShouldOverrideUrlListener.onShouldOverrideUrl(url);
	        	view.loadUrl(url);
	            return true;
	        }
//	        @Override
//	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//	        // TODO Auto-generated method stub
//	        	instantUrl = url;
//	        	CookieManager cookieManager = CookieManager.getInstance();
//	            cookieStr = cookieManager.getCookie(url);
//	        super.onPageStarted(view, url, favicon);
//	        }
	        @Override
	        public void onPageFinished(WebView view, String url) {
	        	instantUrl = url;
	        	view.loadUrl("javascript:window.HTML.getHtml(document.getElementsByTagName('html')[0].innerHTML);");
	        	CookieManager cookieManager = CookieManager.getInstance();
	            cookieStr = cookieManager.getCookie(url);
	        	super.onPageFinished(view, url);
	        }
	        
	    }
	 
//	 @Override
//	 protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//		 LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
//		 lp.x = l;
//		 lp.y = t;
//		 progressbar.setLayoutParams(lp);
//		 super.onScrollChanged(l, t, oldl, oldt);
//	 }
	
	 @SuppressLint("AddJavascriptInterface")
	    public class JSLinster{
	        @JavascriptInterface
	        public void getHtml(String html)
	        {
	        	html = "<html>"+html+"</html>";
				Message msg = handler.obtainMessage();
				msg.obj = html;
				handler.sendMessage(msg);
	        }
	    }
	 
	 public interface ReceiveHTMLListener{
		 public void onReceiveHTML(String url,String html);
	 }
	 
	 public String getCookie(){
		 return cookieStr;
	 }
	 
	 public void setOnShouldOverrideUrlListener(ShouldOverrideUrlListener listener)
	 {
		 this.mShouldOverrideUrlListener = listener;
	 }
	 
	 public interface ShouldOverrideUrlListener{
		 public void onShouldOverrideUrl(String url);
	 }
}