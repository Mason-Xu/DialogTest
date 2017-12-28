# Android 自定义 Dialog ,实现 性别选择,日期选择和 自定义布局获取EditText内容

> Dialog类是对话框的基类,但是应该避免直接实例化Dialog,而是应该尽量使用下列子列之一 :    
> AlertDialog   
此对话框可显示标题、最多三个按钮、可选择项列表或自定义布局。
DatePickerDialog 或 TimePickerDialog  
此对话框带有允许用户选择日期或时间的预定义 UI。  
------------------------------------------------来自于Google Android Develop 开发手册

![](http://oz2u8kxpt.bkt.clouddn.com/17-12-28/753738.jpg?imageslim)

## 日期选择器

这里我们用到了Android原生提供的日期选择器对话框 DatePickerDialog

1. 首先你可以先设定一个date,让DatePickerDialog 点击时显示你设定的时间.通常我们都是获取当前date来显示.所以我们要用到calender来获取当前date

```java
Calendar nowdate = Calendar.getInstance();
int mYear = nowdate.get(Calendar.YEAR);
int mMonth = nowdate.get(Calendar.MONTH);
int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
```


2. 然后你可以在按钮 onClick() 点击事件中设置触发 DatePickerDialog
    - 传入五个参数 parent context ,监听器,年,月,日

```java
new DatePickerDialog(MainActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
```

3. 设置日期选择器对话框的监听器 , DatePickerDialog.OnDateSetListener
  - void onDateSet (DatePicker view,
                int year,
                int month,
                int dayOfMonth)
  - 将获取到的date转换成字符串显示到textview

```java
private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            TextView date_textview = (TextView) findViewById(R.id.changebirth_textview);
            String days;
            days = new StringBuffer().append(mYear).append("年").append(mMonth).append("月").append(mDay).append("日").toString();
            date_textview.setText(days);
        }
    };
```
> 参考自[Google Android Develop DatePickerDialog](https://developer.android.com/reference/android/app/DatePickerDialog.OnDateSetListener.html?hl=zh-cn)

## 性别选择器

1. 首先我们要创建一个数组,来提供选择的选项
  ```java
  private String[] sexArry = new String[]{"不告诉你", "女", "男"};// 性别选择
  ```
2. 设置点击事件后,实现性别选择器的方法 ,这里我们使用了AlertDialog


```java
private void showSexChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                changesex_textview.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }
```

## 自定义Dialog 布局,获取EditText输入框的数据
1. 使用使用LayoutInflater来加载dialog_setname.xml布局
2. 初始化AlertDialog,使用setView()方法将自定义View显示到dialog
3. 设置AlertDialog的按钮,设置点击事件来 获取EditText输入框的内容,然后设置TextView的内容.

- 布局文件
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">


    <EditText
        android:id="@+id/changename_edit"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:layout_marginTop="32dp"
        android:hint="昵称" />

</LinearLayout>
```

- 自定义Dialog获取输入框内容方法
```java
private void onCreateNameDialog() {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_setname, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);

        final EditText userInput = (EditText) nameView.findViewById(R.id.changename_edit);
        final TextView name = (TextView) findViewById(R.id.changename_textview);


        // 设置Dialog按钮
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview
                                name.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
```


##  通常的AlertDialog  实现退出账号效果

- 设置Yes和Cancel按钮和点击事件



```java
AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
builder1.setMessage("确定退出账号?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                  finish();  // 这里使用finish() 模拟下退出账号~
              }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                  // User cancelled the dialog
              }
        });
// Create the AlertDialog object and return it
builder1.show();
```

## 城市选择器  

目前还没有实现... 但是我在看Github上的[citypicker](https://github.com/crazyandcoder/citypicker)  目前还在学习中,如果有实现的可以讨论一下.



> [Google Android Develop Dialog](https://developer.android.com/guide/topics/ui/dialogs.html?hl=zh-cn)
