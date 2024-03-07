package com.ph32165.and103;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    Context context=this;
    FirebaseFirestore database;
    String id="";
    Todo todo=null;
    TextView tvKQ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvKQ=findViewById(R.id.tvKQ);
        database=FirebaseFirestore.getInstance();//khoi tao
//        insertData();
//        updateData();
//        deleteData();
        selectData();
    }
    void insert()
    {
        id= UUID.randomUUID().toString();//lay 1 id ngau nhien
        todo=new Todo(id,"title 1 04","content 1 04");
        HashMap<String,Object> mapToDo=todo.convertToHashMap();//chuyen sang dang co the insert firebase
        database.collection("TODO").document(id)
                .set(mapToDo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Them thanh cong", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Them that bai", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    void update(){
        id="5ea47a2f-db33-44fd-bdf7-25169e573e3b";
        todo=new Todo(id,"title update 1","content update 1");
        database.collection("TODO")
                .document(todo.getId())
                .update(todo.convertToHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void delete()
    {
        id="74c9e5e9-3f47-4f2d-87b5-572b26a169a6";
        database.collection("TODO").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    String strKQ="";
    ArrayList<Todo> selectData()
    {
        ArrayList<Todo> list=new ArrayList<>();
        database.collection("TODO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            strKQ="";
                            for(QueryDocumentSnapshot doc: task.getResult())
                            {
                                Todo t=doc.toObject(Todo.class);//chuyen ket qua sang object
                                strKQ+="id: "+t.getId()+"\n";
                                strKQ+="title: "+t.getTitle()+"\n";
                                strKQ+="content: "+t.getContent()+"\n";
                                list.add(t);
                            }
                            Toast.makeText(context, "Đọc thành công", Toast.LENGTH_SHORT).show();
                            tvKQ.setText(strKQ);
                        }
                        else {
                            Toast.makeText(context, "Đọc không thành công", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
        return list;
    }
}