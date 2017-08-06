# GuidePage
快速实现APP欢迎页，一行代码搞定
<div style = "float:center">
    <img src="https://github.com/q296494327/GuidePage/blob/master/page1.png" width="240">
    <img src="https://github.com/q296494327/GuidePage/blob/master/page2.png" width="240">
<div/>
 
 
 
添加依赖

```xml
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    
dependencies {
	        compile 'com.github.q296494327:GuidePage:1.0'
	}
```

1：布局文件
```xml
 <com.xiemiao.guidepagelib.view.GuidePage
        android:id="@+id/guidePage"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </com.xiemiao.guidepagelib.view.GuidePage>
    
```
2:代码
```java
public class MainActivity extends AppCompatActivity {
    private GuidePage guidePage;
    private Integer[] images = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e, R
            .mipmap.f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guidePage = (GuidePage) findViewById(R.id.guidePage);
        //设置图片集合,圆点颜色大小
        guidePage.setLocalImageResList(Arrays.asList(images)).setOvalIndicator(Color.parseColor
                ("#00FF00"), Color.parseColor("#FFFFFF"), 10);

        //设置进入按钮（文字，颜色，大小，背景）点击事件
        guidePage.setOnEntryClickListener("立即体验", Color.parseColor("#000000"), 10, R.drawable
                .shape_radius_yellow_bg, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "进入主界面", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```
