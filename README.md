# ASimpleWaitingDemo
一个简单的等待动画

- 可以给当前界面的任意一个view附上这个加载动画，显示在界面正中
```
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View yourView = findViewById(R.id.yourView);
        
        yourView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WaitingView.showWaitingView(MainActivity.this, v,width,height,circlecolor,circleRadius,backgroundColor);

            }
        });



    }

```
