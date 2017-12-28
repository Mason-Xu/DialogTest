package com.example.dialogtest;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] sexArry = new String[]{"不告诉你", "女", "男"};// 性别选择

    private Button changebirth_button;
    private Button exitaccount_button;
    private Button changesex_button;
    private Button changename_button;
    private Button changeaddress_button;


    private TextView changesex_textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changename_button = (Button) findViewById(R.id.changename_button);
        changebirth_button = (Button) findViewById(R.id.changebirth_button);
        exitaccount_button = (Button) findViewById(R.id.exitaccount_button);
        changesex_button = (Button) findViewById(R.id.changesex_button);
        changeaddress_button = (Button) findViewById(R.id.changeaddress_button);
        InitView();
    }

    private void InitView() {
        changename_button.setOnClickListener(this);
        changebirth_button.setOnClickListener(this);
        exitaccount_button.setOnClickListener(this);
        changesex_button.setOnClickListener(this);
        changeaddress_button.setOnClickListener(this);
    }

    /**
     * 日期选择器对话框监听
     */
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changebirth_button:
                Calendar nowdate = Calendar.getInstance();
                final int mYear = nowdate.get(Calendar.YEAR);
                final int mMonth = nowdate.get(Calendar.MONTH);
                final int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
                //调用DatePickerDialog
                new DatePickerDialog(MainActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.exitaccount_button:
                // Use the Builder class for convenient dialog construction
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
                break;
            case R.id.changesex_button:
                changesex_textview = (TextView) findViewById(R.id.changesex_textview);
                showSexChooseDialog();
                break;
            case R.id.changename_button:
                onCreateNameDialog();
                break;
            default:
                break;

        }
    }

    private void onSetAddress() {


    }

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


}
