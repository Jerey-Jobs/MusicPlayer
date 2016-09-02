package com.example.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    String[] datas = {"nihao1", "sdsda1", "sdsda2", "sdsda3", "sdsda4",
            "sdsda5", "sdsda6", "sdsda7", "nihao", "sdsda1", "sdsda2",
            "sdsda3", "sdsda4", "sdsda5", "sdsda6", "sdsda7", "nihao",
            "sdsda1", "sdsda2", "sdsda3", "sdsda4", "sdsda5", "sdsda6", "sdsda7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);

        //改变方向
        //mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter =  new RecyclerViewAdapter(datas);
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + "pressed", LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + "Longpressed", LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }
}
