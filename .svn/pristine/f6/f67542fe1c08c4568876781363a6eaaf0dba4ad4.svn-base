package com.wangcai.lottery.component;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MyTextWatcher implements TextWatcher {

	private String TAG = "MyTextWatcher";
	private EditText numberEditText;
	int beforeLen = 0;  
    int afterLen = 0;
    private int afterflag=1;
    private onTextWatcherChangingListener textWatcherChanging;
    
	public MyTextWatcher(EditText numberEditText) {
		super();
		this.numberEditText = numberEditText;
		
	}

	 public String removeAllSpace(String str) {  
	       String tmpstr=str.replace(" ","");  
	       return tmpstr;  
	   } 
	
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		 String txt = numberEditText.getText().toString();  
		 Log.d(TAG, "mEditText = " + removeAllSpace(txt) + ".");
         afterLen = txt.length();  
         Log.d(TAG, "beforeLen = " + beforeLen + "afterLen = " + afterLen);
         if (afterLen > beforeLen) {  
             if (txt.length() == 5 || txt.length() == 10 || txt.length() == 15 || txt.length() == 20) {  
                 numberEditText.setText(new StringBuffer(txt).insert(txt.length() - 1, " ").toString());  
                 numberEditText.setSelection(numberEditText.getText().length());  
                 Log.d(TAG, "selection = " +numberEditText.getText().length());
             }
         } else {  
             if (txt.startsWith(" ")) {  
                 numberEditText.setText(new StringBuffer(txt).delete( afterLen - 1, afterLen).toString());  
                 numberEditText.setSelection(numberEditText.getText()  
                         .length());  
                 Log.d(TAG, "else start space");
             } 
         }
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		beforeLen = arg0.length();  
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		 if(afterLen > 0&&afterflag==1){
        	 afterflag=0;
        	 textWatcherChanging.onTextWatcherChanging(true);
         }else if(afterLen <=beforeLen && afterLen<=0){
        	 afterflag=1;
         }
	}

	public onTextWatcherChangingListener getTextWatcherChanging() {
		return textWatcherChanging;
	}

	public void setTextWatcherChanging(onTextWatcherChangingListener textWatcherChanging) {
		this.textWatcherChanging = textWatcherChanging;
	}


	public interface onTextWatcherChangingListener {
		public void onTextWatcherChanging(boolean textbool);
	}
}
