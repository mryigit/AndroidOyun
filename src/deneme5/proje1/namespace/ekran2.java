package deneme5.proje1.namespace;




import java.util.Random;

import javax.crypto.NullCipher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ekran2 extends Activity{
	TextView txtsoru;
	RadioGroup rg;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    Button onayla;
    String secilen;
    String cevap;
    Integer secid;
    Integer k=1;
    Cursor crsr;
    Cursor crsr2;
    Cursor crsr1;
    Integer sayac=0;
    int sayi = 1;
    int dizi []=new int [16];
    int seviye []={0,5,10,15};
    int j = 0;
    int degisken = 5;
    int toplam=0;
    int puanlama []={50,100,250};
	private DatabaseHelper databaseHelper;
	private void loadActivity() {
		
		secid=rastgele(seviye[j]);
		
		databaseHelper = new DatabaseHelper(this);
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		
		crsr = db.rawQuery("SELECT soru FROM sorubankasi where id =="+String.valueOf(secid), null);
		crsr.moveToFirst();
		
		crsr2 = db.rawQuery("SELECT cevap FROM cevap where cvp_id == "+String.valueOf(secid), null);
		crsr2.moveToFirst();
		cevap =crsr2.getString(crsr2.getColumnIndex("cevap")).toString();
		
		txtsoru=(TextView)findViewById(R.id.txt_soru);
		txtsoru.setText(String.valueOf(sayi)+")  "+crsr.getString(crsr.getColumnIndex("soru")));
		sayi++;
		
	    rb1 =(RadioButton)findViewById(R.id.radio1);
        rb2 =(RadioButton)findViewById(R.id.radio2);
        rb3 =(RadioButton)findViewById(R.id.radio3);
        rb4 =(RadioButton)findViewById(R.id.radio4);
        
        
        
        onayla = (Button)findViewById(R.id.onay);
		crsr1 = db.rawQuery("SELECT * FROM secenekler where sec_id=="+String.valueOf(secid), null);
		crsr1.moveToFirst();
		rb1.setText("A)  "+crsr1.getString(crsr1.getColumnIndex("secA")));
		rb2.setText("B)  "+crsr1.getString(crsr1.getColumnIndex("secB")));
		rb3.setText("C)  "+crsr1.getString(crsr1.getColumnIndex("secC")));
		rb4.setText("D)  "+crsr1.getString(crsr1.getColumnIndex("secD")));
		
		onayla.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String secilen=secilenibul();
				karsilastir(secilen);
				
			}
		});
	    
	}
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ekran2);
		
		loadActivity();
		
	}
	
    
    
    
	
	public String secilenibul(){
		if(rb1.isChecked()){
			
            secilen = (String) rb1.getText().toString().subSequence(4, rb1.getText().length());
            
        }else if(rb2.isChecked()){
        	
        	secilen = (String) rb2.getText().toString().subSequence(4, rb2.getText().length());
        	
        }else if(rb3.isChecked()){

        	secilen = (String) rb3.getText().toString().subSequence(4, rb3.getText().length());
        	
        }else if(rb4.isChecked()){

        	secilen = (String) rb4.getText().toString().subSequence(4, rb4.getText().length());
        	
        }
		return secilen;
		
	}
	

		public void karsilastir(String s){
			 final MediaPlayer baraj = MediaPlayer.create(ekran2.this , R.raw.baraj);
			 final MediaPlayer yanlis = MediaPlayer.create(ekran2.this , R.raw.yanliscevap);
			 final MediaPlayer dogru = MediaPlayer.create(ekran2.this , R.raw.dogrucevap);
			if (s.equalsIgnoreCase(cevap) ){
				++sayac;
				AlertDialog.Builder builder = new AlertDialog.Builder(ekran2.this);
				
				if(seviye[j+1] == sayac){
					
					if(j==2){
						baraj.start();
						builder.setMessage(" tebrikler kazandýnýz "+" toplam "+puan_hesapla(j)+ "  puan topladýnýz." ).
						setCancelable(false).setPositiveButton("Bitti",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
								
								finish();	
							}
						});	
						AlertDialog alert = builder.create();
						alert.show();
					}
					
					else {
					
					builder.setMessage("tebrikler "+(j+1)+" bölümü bitirdiniz"+"  toplam  puaniniz  "+puan_hesapla(j)).
					setCancelable(false).setPositiveButton((j+2)+".aþamayý baþlat",new DialogInterface.OnClickListener() {
				
						public void onClick(DialogInterface dialog, int id) { 
						j++;
						baraj.start();
						setContentView(R.layout.ekran2);
						loadActivity();
					}});	
					AlertDialog alert = builder.create();
					alert.show();
					}
				}
				
					
				if(sayac <= seviye[j+1]){
					dogru.start();
				builder.setMessage("doðru").
				setCancelable(false).setPositiveButton("sonraki soru",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) { 
					if(sayac!=seviye[j+1]){
						
						setContentView(R.layout.ekran2);
						loadActivity();}
					
					}
	
				});	
				AlertDialog alert = builder.create();
				alert.show();}
				
			}
				
				else{
				yanlis.start();
				AlertDialog.Builder builder = new AlertDialog.Builder(ekran2.this);
				builder.setMessage("yanlis . maalesef elendiniz "+"simdiye kadar "+puan_hesapla(j)+ "  puan topladýnýz. Bundan sonraki hayatýnýzda baþarýlar dileriz.  " ).
				setCancelable(false).setPositiveButton("Bitti",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					
						finish();	
					}
				});	
				AlertDialog alert = builder.create();
				alert.show();
			}
			
		}
		
		public int puan_hesapla(int sevi){
			toplam=(puanlama[sevi]*(sayac-seviye[sevi]))+ toplam;
			
			return toplam ;
		}
		
		
		
		
		
		public int rastgele(int seviye){
			Random randInt = new Random();
			int b = randInt.nextInt(5)+seviye+1;
			dizi[0]=0;
			
		    while (aynimi(b) == true)
		    	b = randInt.nextInt(5)+seviye+1;
		    
		    dizi[k]=b;
		    	k++;
		    
		    	
			return b;
		}
		
		public Boolean aynimi(int gelen){
			
			Boolean bulundu=false;
			int i=0;
			while(i<16 && !bulundu){
				if(gelen == dizi[i])
					bulundu = true;
				i++;
			}
			return bulundu;
		}
			
	
	}

	

