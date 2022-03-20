package com.kit.app.softier;

public class ItemListView
{
	String root;
	String define;

	public ItemListView(String root, String define)
	{
		this.root = root;
		this.define = define;
	}

	public void setRoot(String root)
	{
		this.root = root;
	}

	public String getRoot()
	{
		return root;
	}

	public void setDefine(String define)
	{
		this.define = define;
	}

	public String getDefine()
	{
		return define;
	}
}
