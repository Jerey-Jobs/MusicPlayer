package com.example.mvpdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvpdemo.presenter.GetStringPresenter;
import com.example.mvpdemo.view.IGetStringView;

public class MainActivity extends AppCompatActivity implements IGetStringView{

    private EditText editText;
    private Button button;
    private GetStringPresenter presenter;
    private static Context mContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        mContext = getApplicationContext();

        presenter = new GetStringPresenter(this);
        presenter.showName();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveName();
            }
        });

    }

    @Override
    public void showName(String name) {
        editText.setText(name);
    }

    @Override
    public String getName() {
        return editText.getText().toString();
    }

    public static Context getAppContext()
    {
        return mContext;
    }

}
