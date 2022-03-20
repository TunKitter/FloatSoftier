package com.kit.app.softier;
import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.view.View;
import java.util.ArrayList;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.Context;

public class AdapterList extends BaseAdapter
{
	ArrayList<ItemListView> adap;
	
	
AdapterList(ArrayList<ItemListView> adap ){
	this.adap = adap;
}

@Override
public int getCount()
{
return adap.size();
}

@Override
public Object getItem(int p1)
{
    
return adap.get(p1);
}

@Override
public long getItemId(int p1)
{
return 0;
}

@Override
public View getView(int p1, View p2, ViewGroup p3)
{
    
	View viewPro;
    
	
	if(p2 == null){
		viewPro = View.inflate(p3.getContext(),R.layout.list_item,null);
        
	}
	else{
		viewPro = p2;
	}
	ItemListView item = (ItemListView) getItem(p1);
	((TextView) viewPro.findViewById(R.id.root_id)).setText(item.root);
	((TextView) viewPro.findViewById(R.id.define_id)).setText(item.define);
	
	
return viewPro;
}

	
}
