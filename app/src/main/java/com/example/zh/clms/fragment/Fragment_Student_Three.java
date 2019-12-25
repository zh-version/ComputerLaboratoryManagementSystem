package com.example.zh.clms.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zh.clms.R;
import com.example.zh.clms.activity.LoginActivity;
import com.example.zh.clms.activity.MainActivity_Student;
import com.example.zh.clms.database.Student;
import com.example.zh.clms.database.StudentDao;
import com.example.zh.clms.database.StudentService;
import com.example.zh.clms.utils.sp;

import java.util.Map;

public class Fragment_Student_Three extends Fragment implements View.OnClickListener {
    private RelativeLayout re_personal_info, re_userPassword_update, re_exit_login, re_exit_app;
    private View view;
    private String string_getuser;
    private String string_getpassword;
    private String str_ed_first;
    private String str_ed_second;
    private static String t;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_three, container, false);
        init();
        return view;
    }

    private void init() {
        re_personal_info = view.findViewById(R.id.fragment_three_RelativeLayout_personal_info);
        re_userPassword_update = view.findViewById(R.id
                .fragment_three_RelativeLayout_userPassword_update);
        re_exit_login = view.findViewById(R.id.fragment_three_RelativeLayout_exit_login);
        re_exit_app = view.findViewById(R.id.fragment_three_RelativeLayout_exit_app);

        re_personal_info.setOnClickListener(this);
        re_userPassword_update.setOnClickListener(this);
        re_exit_login.setOnClickListener(this);
        re_exit_app.setOnClickListener(this);

        sp.getData(getContext());
        string_getuser = sp.sharedPreferences.getString("user", "");
        string_getpassword = sp.sharedPreferences.getString("password", "");
        t = string_getuser;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_three_RelativeLayout_personal_info:
                //用户信息
                showPersonalDialog();
                break;
            case R.id.fragment_three_RelativeLayout_userPassword_update:
                //用户名密码修改
                show_UserPassword_update_Dialog();
                break;
            case R.id.fragment_three_RelativeLayout_exit_login:
                //退出登录
                showExitLogin();
                break;
            case R.id.fragment_three_RelativeLayout_exit_app:
                //退出应用
                showExitApp();
                break;
        }
    }

    //用户信息
    private void showPersonalDialog() {

        sp.getData(getContext());

        string_getuser = sp.sharedPreferences.getString("user", "");
        string_getpassword = sp.sharedPreferences.getString("password", "");
        t = string_getuser;

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(view
                .getContext());
        builder.setTitle("用户信息");
        builder.setItems(new String[]{"用户名：" + string_getuser, "密码：" + string_getpassword}, null);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final android.app.AlertDialog dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.8f;
        window.setAttributes(layoutParams);
    }

    //账户密码修改
    private void show_UserPassword_update_Dialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

        final View view = View.inflate(getContext(), R.layout
                .fragment_student_three_password_update_alertdialog, null);
        final TextView password_update_textView_username = view.findViewById(R.id
                .fragment_student_three_password_update_TextView_username);
        final ImageView password_update_imageView_close = view.findViewById(R.id
                .fragment_student_three_password_update_imageView_Close);
        final EditText password_update_editText_first = view.findViewById(R.id
                .fragment_student_three_password_update_editText_first);
        final EditText password_update_editText_second = view.findViewById(R.id
                .fragment_student_three_password_update_editText_second);
        final Button password_update_button = view.findViewById(R.id
                .fragment_student_three_password_update_button);
        password_update_textView_username.setText(t);
        builder.setView(view);
        final android.app.AlertDialog dialog = builder.create();
        dialog.show();

        password_update_imageView_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        password_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_ed_first = password_update_editText_first.getText().toString().trim();
                str_ed_second = password_update_editText_second.getText().toString().trim();
                if ((str_ed_first).equals("")) {
                    Toast.makeText(getContext(), "输入不能为空", Toast.LENGTH_LONG).show();
                } else if ((str_ed_first).equals(str_ed_second)) {
                    sp.saveData(getContext(), t, str_ed_second);

                    Student student = new Student();
                    student.setUserName(t);
                    student.setPassword(str_ed_second);
                    Object[] params = {student.getPassword(), student.getUserName()};
                    boolean flag1 = new StudentDao(getContext()).updateStudent(student, params);
                    if (flag1) {
                        Toast.makeText(getContext(), "数据修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "数据修改失败", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "两次输入不相同，请您重新输入！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //退出登录
    private void showExitLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("您确认要退出登录？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        builder.setNegativeButton("取消", null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.85f;
        window.setAttributes(layoutParams);


    }

    //退出应用程序
    private void showExitApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("您确认要退出应用程序？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        builder.setNegativeButton("取消", null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.85f;
        window.setAttributes(layoutParams);
    }
}
