package com.example.administrator.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.sina.weibo.sdk.constant.WBConstants;

import java.security.PrivateKey;
import java.util.List;
import java.util.logging.LogRecord;
//import com.avos.avoscloud.im.v2.AVIMMessageManager;
//import com.avos.avoscloud.im.v2.AVIMTypedMessage;



public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private WebView mWebView;
    public Handler myHandler=null;

    //private MyHandler handler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        AVOSCloud.initialize(this,"GTHvSIdoWaFRx1Gnf6Sff1pa-gzGzoHsz","UvjOV4h8HvbAnIEfram2te8C");
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //test
                    //新建数据表
                   /* AVObject testObject = new AVObject("TestObject");
                    testObject.put("name", "sb");
                    testObject.saveInBackground();*/
                   /* AVObject html=new AVObject("TestHtml");
                    //这一句能不能这么用？
                    html.put("name","sb");
                    html.saveInBackground();*/

                    new Thread(networkTask).start();
                    //新线程读取数据库数据，否则报错


                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        //如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
        // AVOSCloud.initialize(this, "GTHvSIdoWaFRx1Gnf6Sff1pa-gzGzoHsz", "UvjOV4h8HvbAnIEfram2te8C");

        AVAnalytics.trackAppOpened(getIntent());
        //云服务端查看打开次数
       /* AVObject testObject = new AVObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/
        //新建数据表
        //这一句能不能这么用？
        mWebView = (WebView)findViewById(R.id.wv1);
        ProxyBridge pBridge = new ProxyBridge();
        //自定义的函数
        mWebView.addJavascriptInterface(pBridge, "AliansBridge");
        //上句代码是实例化一个方法为的是在html中的js中调用，第二个参数则是 实例化方法的别名，如果要使用这个pBridge，则在js中使用的名字就是AliansBridge。

        WebSettings wSet = mWebView.getSettings();
        wSet.setJavaScriptEnabled(true);
        //上面两句就是允许使用js的意思

        mWebView.loadUrl("file:///android_asset/index2.html");


        /*// 设置支持JavaScript等
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setLightTouchEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebView.setHapticFeedbackEnabled(false);
        // mWebView.setInitialScale(0); // 改变这个值可以设定初始大小

        //重要,用于与页面交互!

        mWebView.addJavascriptInterface(new Object() {
            public void oneClick(final String locX, final String locY) {//此处的参数可传入作为js参数
                mHandler.post(new Runnable() {
                    public void run() {

                        mWebView.loadUrl("javascript:shows(" + locX + "," + locY + ")");
                    }
                });
            }
        }, "demo");
        //此名称在页面中被调用,方法如下:
        //<body onClick="window.demo.clickOnAndroid(event.pageX,event.pageY)">

        final String mimeType = "text/html";
        final String encoding = "utf-8";
        final String html = "";
        // TODO 从本地读取HTML文件

        mWebView.loadDataWithBaseURL("file:///sdcard/", html, mimeType,
                encoding, "");*/


    }
    Runnable testrunnable=new Runnable() {
        @Override
        public void run() {
            myHandler.obtainMessage();
        }
    };
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
              AVQuery<AVObject> query=new AVQuery<AVObject>("TestObject");
                    query.whereEqualTo("name","sb");
                    query.findInBackground(new FindCallback<AVObject>() {
                        public void done(List<AVObject> avObjects, AVException e) {
                            if (e == null) {
                                Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                            } else {
                                Log.d("失败", "查询错误: " + e.getMessage());
                            }
                        }
                    });
               /*  AVQuery<AVObject> query = AVQuery.getQuery("TestObject");
                    query.orderByDescending("createdAt");
                   AVObject TestName;
                    String NameStr="";
                    try {
                      TestName = query.get("570880ae79bc44004c6148af");
                        NameStr=TestName.getString("name");
                    } catch (AVException e) {
                        // e.getMessage()

                    }
                    Log.d("succeed","ok"+NameStr);*/
        }
    };

    // ProxyBridge pBridge = new ProxyBridge();
    public class ProxyBridge {
        @JavascriptInterface
        //
        public int one () {
            return 1;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
