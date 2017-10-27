#TongzhuoSDK
---------
[ ![Download](https://api.bintray.com/packages/felix0503/maven/tzopengame/images/download.svg) ](https://bintray.com/felix0503/maven/tzopengame/_latestVersion)
    
Tongzhuo open game sdk base on [X5 Core](http://x5.tencent.com/), To help developers to quickly integrate Tongzhuo game.    
 
-----------

#Get Start
----------

##Add the dependencies   
If you are building with Gradle, simply add the following line to the dependencies section of your build.gradle file:     

```Groovy
allprojects {
    repositories {
        maven {
            url "https://dl.bintray.com/felix0503/maven"
        }
    }
}


compile 'com.tongzhuo:tzopengame:0.1.0'
```

##Initialize  
```java
//Must init tzopengame in Application:onCreate()
TZManager.onInit(this, false);
```
##Usage
1\. Use [PlayGameFragment](https://github.com/chaichuanfa/TongzhuoSDK/blob/master/tzopengame/src/main/java/com/tongzhuo/tzopengame/ui/PlayGameFragment.java) to quickly open the game.    
  
```Java
//Context must implements GameResultCallback to listen game result
getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_view, PlayGameFragment.newInstance(game_url), tag).commit();
```                  
2\. Use [X5WebView](https://github.com/chaichuanfa/TongzhuoSDK/blob/master/tzopengame/src/main/java/com/tongzhuo/tzopengame/tencent_x5/X5WebView.java) to custom game window   
  
```Java                    
<com.tongzhuo.tzopengame.tencent_x5.X5WebView
            android:id="@+id/mGameView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            >
</com.tongzhuo.tzopengame.tencent_x5.X5WebView>

X5WebView mGameView = (X5WebView) findViewById(R.id.mGameView);
//add js call native object
mGameView.addJavascriptInterface(this, "TzOpen");
mGameView.loadUrl(mGameUrl); 

/**
* the game result callback
*/
@JavascriptInterface
public void getResult(String value) {
	TZLog.d("game result : "+value);
    if (mResultCallback != null) {
	    mResultCallback.onResult(value);
    }
}
```
#ProGuard
Depending on your ProGuard (DexGuard) config and usage, you may need to include [proguard-tencent-x5.cfg](https://github.com/chaichuanfa/TongzhuoSDK/blob/master/app/proguard-tencent-x5.cfg) in your build.gradle   

```Groovy
proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro',
                    'proguard-tencent-x5.cfg'
```   
include the following lines in your proguard.cfg    

```
-keep class com.tongzhuo.tzopengame.** { *; }
-dontwarn com.tongzhuo.tzopengame.**
```
#Thanks
* [Tongzhuo](http://www.tongzhuogame.com/) 
* [X5 Core](http://x5.tencent.com/) 

#Author 
chaichuanfa@tongzhuogame.com


