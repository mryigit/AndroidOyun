package deneme5.proje1.namespace;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BilbilebilirsenActivity extends Activity {
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final MediaPlayer acilisses = MediaPlayer.create(BilbilebilirsenActivity.this , R.raw.giris);
        acilisses.start();
        final MediaPlayer baslases = MediaPlayer.create(BilbilebilirsenActivity.this , R.raw.baraj);
        
        Button basla = (Button) findViewById(R.id.basla);
        basla.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				baslases.start();
				startActivity(new Intent("deneme5.proje1.namespace.EKRANIKI"));
				
			}
		});
        
        Button cikis = (Button) findViewById(R.id.cikis);
        cikis.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});
    }
}