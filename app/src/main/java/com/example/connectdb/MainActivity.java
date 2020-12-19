package com.example.connectdb;

//package com.example.connectdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button createDataBase,addData;
    private EditText resultText;
    String driverName="net.sourceforge.jtds.jdbc.Driver";
    //String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=JDBCDemo";
    String dbURL="jdbc:jtds:sqlserver://8.129.60.123:11433;DatabaseName=YingSheng";//数据库连接url
    String userName="sa";//数据库用户名
    String userPwd="yingsheng@2014";//数据库密码
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();//初始化组件
        addData.setOnClickListener(this);
    }
    private void init() {
        createDataBase=(Button)findViewById(R.id.create_database);
        addData=(Button)findViewById(R.id.add_data);
        resultText=(EditText)findViewById(R.id.result_text);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_data:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Class.forName(driverName); //jdk版本6.0以上可以省略这句话
                            con= DriverManager.getConnection(dbURL,userName,userPwd);
                            String sql="select top 3 * from [YingSheng].[dbo].[YsCourses]";
                            Statement st=con.createStatement();
                            ResultSet rs=st.executeQuery(sql);
                            while(rs.next()){
                                Log.i("MainActivity",rs.getString("Title"));
                            }
                            rs.close();
                            st.close();
                            con.close();
                            System.out.println("连接数据库成功");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
}