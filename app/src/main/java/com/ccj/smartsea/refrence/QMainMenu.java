package com.ccj.smartsea.refrence;
 
import android.content.Context;

import com.ccj.smartsea.R;

public class QMainMenu extends QButtonDialog{ 
	public QMainMenu(Context mct) {
		super(mct);
		// TODO Auto-generated constructor stub
	       this.addMenu(mct.getString(R.string.lbSet), R.drawable.menu_filemanager);
	       this.addMenu(mct.getString(R.string.lbAbout), R.drawable.menu_about);

	} 
	
	
}