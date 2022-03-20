package com.kit.app.softier;


import android.app.*;
import android.os.*;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.content.DialogInterface;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import android.view.View.OnClickListener;
import android.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.graphics.Color;

public class MainActivity extends Activity 
{
	ListView ls;
	ArrayList <ItemListView> adap;
	AdapterList adaplist;
    EditText rot,def,edr,edd;
    boolean clickAgain=false,isRunning=false,isAuto=false;
    TextView main_r,main_d;
    ProgressBar pro;
    Button check,add,remove,rmall,edit,editconfirm,auto;
    String FILE_NAME = "key.txt";
    FileOutputStream fileout;
    OutputStreamWriter writefile;
    FileInputStream inp;
    BufferedReader buffer;
    InputStreamReader stream;
    int count=0;
    String arr[];
    int index;
    CountDownTimer cdt ;
    
      
    @Override
    protected void onCreate(Bundle savedInstanceState)  
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try
        {
             inp = openFileInput(FILE_NAME);

             buffer = new BufferedReader(new InputStreamReader(inp));
            try
            {
                arr = buffer.readLine().split(";");
            }
            catch (IOException e)
            {
               
            }
        }
        catch (FileNotFoundException e)
        {

            try
            {
                 fileout = openFileOutput(FILE_NAME, MODE_APPEND);
                 writefile = new OutputStreamWriter(fileout);
                try
                {
                    writefile.write("Hello,Xin chao;You,Ban");
                   
                    writefile.close();
                    
                }
                catch (IOException ee)
                {

                }
            }
            catch (FileNotFoundException eee)
            {
                
            }


            
        }
        main_r = findViewById(R.id.main_r);
        main_d = findViewById(R.id.main_d);
        pro= findViewById(R.id.pro_id);
		ls = findViewById(R.id.lsview);
       
       
		adap = new ArrayList<>();

        
        
        for (int i = 0 ; i < arr.length ; i++)
        {
            adap.add(new ItemListView(arr[i].split(",")[0], arr[i].split(",")[1]));

        }
        
		adaplist = new AdapterList(adap);

		ls.setAdapter(adaplist);
        
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
                {
                    runWord(p3);
                    
}
            });
		ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
                   
					index = p3;
                        showdig();

					return true;
				}



            });
        
    }
    private void showDialogCreat()
    {
        final Dialog dig = new Dialog(MainActivity.this);
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dig.setContentView(R.layout.dialog_creat);
        
        dig.show();
        rot = dig.findViewById(R.id.root_edt);
        def = dig.findViewById(R.id.def_edt);
        check = dig.findViewById(R.id.button_check);
        check.setOnClickListener(new View.OnClickListener(){
            String tempR,tempD;
                @Override
                public void onClick(View p1)
                {
                    if (rot.length() > 2 && def.length() > 2)
                    {
                        tempR = adap.get(0).getRoot();
                        tempD = adap.get(0).getDefine();
                        adap.get(0).setRoot(rot.getText().toString());
                        adap.get(0).setDefine(def.getText().toString());
                        
                         
                        adap.add(new ItemListView(tempR,tempD));

                        Toast.makeText(MainActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                        applySave();
                        dig.cancel();
                       
                        adaplist.notifyDataSetChanged();
                    }
                }


            });
    }
    




    
    private void showdig(){
        final Dialog menu = new Dialog(MainActivity.this);
        menu.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menu.setContentView(R.layout.choose);
        
       if(isRunning==false){
           
       
        menu.show();
        }
       edit = menu.findViewById(R.id.confirmedt);
        add = menu.findViewById(R.id.cre_id);
        remove = menu.findViewById(R.id.rem_id);
        rmall = menu.findViewById(R.id.rem_al);
        auto = menu.findViewById(R.id.auto_id);
        
        
        edit.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    final Dialog ed = new Dialog(MainActivity.this);
                    ed.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ed.setContentView(R.layout.editcards);
                      ed.show();
                      edr = ed.findViewById(R.id.edit_word_id);
                      edd = ed.findViewById(R.id.edit_define_id);
                      editconfirm = ed.findViewById(R.id.btn_confirm_ed_id);
                      edr.setText(adap.get(index).getRoot());
                    edd.setText(adap.get(index).getDefine());
                    
                    editconfirm.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View p1)
                            {
                            if(edd.length() > 2 && edr.length() > 2 ){
                                adap.get(index).setRoot(edr.getText().toString());
                                adap.get(index).setDefine(edd.getText().toString());
                           adaplist.notifyDataSetChanged();
                           Toast.makeText(MainActivity.this,"Renamed",Toast.LENGTH_LONG).show();
                           ed.cancel();
                           menu.cancel();
                           applySave();
                            }
                            }

                        
                    });
                }

            
        });
        add.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                        showDialogCreat();
                        
                        menu.cancel();
                }

            
        });
        remove.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    if(!(adap.size() <= 1 )){
                        adap.remove(index);
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        
                    }
                    
                    
                    adaplist.notifyDataSetChanged();
                   applySave();
                    menu.cancel();
                }


            });
        rmall.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    adap.clear();
                    
                    
                    adap.add(new ItemListView("😁","😀"));
                    
                    adaplist.notifyDataSetChanged();
                    applySave();
                    menu.cancel();
                }

            
        });
        auto.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    if(isAuto==false){
                        
                    
                    isAuto=true;
                    }
                    else {
                        isAuto=false;
                    }
                Toast.makeText(MainActivity.this,"Complete . Now is " +String.valueOf(isAuto),Toast.LENGTH_SHORT).show();
               menu.cancel();
                runWord(0);
                }

            
        });
      
    }

    private void applySave(){
        
        try
        {
            fileout = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writefile = new OutputStreamWriter(fileout);
            try
            {
                for(int i = 0 ; i < adap.size() ; i++){
                    writefile.write(adap.get(i).getRoot()+","+adap.get(i).getDefine()+";");

                    
                }
                writefile.close();
            }
            catch (IOException e)
            {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();

            }
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();



        }

        
        
    }
    private void runWord(int p3){
        if(p3==0 && isRunning==false){
            isRunning=true;
            clickAgain=false;
            main_r.setText(adap.get(0).getRoot());
            main_d.setText(adap.get(0).getDefine());
            main_d.setVisibility(View.INVISIBLE);
            cdt = new CountDownTimer(10000, 100){

                @Override
                public void onTick(long p1)
                {
                    if(p1 <= 6000){
                        main_d.setVisibility(View.VISIBLE);
                    }
                    pro.setProgress(pro.getProgress()+1,true);
                }

                @Override
                public void onFinish()
                {
                    pro.setProgress(0,true);
                    isRunning=false;
                    String temp1 = adap.get(0).getDefine();
                    String temp2 = adap.get(0).getRoot();
                    adap.remove(0);
                    adap.add(new ItemListView(temp2,temp1));
                    adaplist.notifyDataSetChanged();
                    applySave();
                    main_r.setText("Cheers");

                    count+=1;
                    main_d.setText("You've learnt "+ count );
                    if(isAuto==true){

            runWord(0);
                    }
                }


            };
            cdt.start();






        }
        else {
            if(clickAgain==false){

                clickAgain = true;
                Toast.makeText(MainActivity.this,"Đừng nhảy số lung tung",Toast.LENGTH_LONG).show();
            }

        }
    
    
    }

}

