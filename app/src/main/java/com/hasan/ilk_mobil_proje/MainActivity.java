package com.hasan.ilk_mobil_proje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener /*Arka plan rengi değiştiğindedeğişmiyo program kapanıp açıldığından değişiyo onun için bunu yaptık*/ {
    int count;
    Button btn;         //metot içide illa final istiyo globalde gerek kalmıyo
    SharedPreferences preferences, ayarlar;
    RelativeLayout arkaplan;
    Boolean ses_durumu, titresim_durumu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        btn = (Button) findViewById( R.id.button );
        arkaplan = (RelativeLayout) findViewById( R.id.rl );

        //sayaç verisi kaybolmaması için (dönme ve çıkıllınca siliniyo) aşağıdaki kodlargirilir
        preferences = PreferenceManager.getDefaultSharedPreferences( getApplicationContext() );            //bu sayfadaki context belirlenir
        ayarlar = PreferenceManager.getDefaultSharedPreferences( getApplicationContext() );

        final MediaPlayer ses = MediaPlayer.create( getApplicationContext(), R.raw.btnsesi );
        final Vibrator titresim = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE );

        ayarlariYukle();
        count = preferences.getInt( "count_anahtarı", 0 );     //0 varsayılan değerdir  hiç açılmamışsa diyerekten bu değer                 //kaydedilen veriyi counta aktarıyo
        btn.setText( "" + count );                                                                                                                                //tekrar butona yazdırıyo


        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ses_durumu) {
                    ses.start();
                }     //SoundPool da dene       https://www.youtube.com/watch?v=Cdc9Y6o-qeM
                //ses.start();   ses.Stop();      ses.Pouse(); metotlarıda vardır


                if (titresim_durumu) {
                    titresim.vibrate( 250 );
                }       //1000 1sndir 250 200ideal

                //  count=count+1;          //yada
                count++;
                //btn.setText("" + count);                        //yada
                btn.setText( String.valueOf( count ) );

            }
        } );


    }

    private void ayarlariYukle() {
        String pos = ayarlar.getString( "arkaplan", "3" );
        switch (Integer.valueOf( pos )) {
            case 0:
                arkaplan.setBackgroundResource( R.drawable.background );
                break;

            case 1:
                arkaplan.setBackgroundColor( Color.GREEN );
                break;
            case 2:
                arkaplan.setBackgroundColor( Color.BLUE );
                break;
            case 3:
                arkaplan.setBackgroundColor( Color.DKGRAY );
                break;
            case 4:
                arkaplan.setBackgroundColor( Color.LTGRAY );
                break;
            default:
                break;
        }
        ses_durumu = ayarlar.getBoolean( "ses", false );
        titresim_durumu = ayarlar.getBoolean( "titresim", false );
        ayarlar.registerOnSharedPreferenceChangeListener( MainActivity.this );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // int id=item.getItemId()                 böledealınabilirdi
        if (item.getItemId() == R.id.action_setting) {

            Intent intent = new Intent( getApplicationContext(), Ayarlar.class );
            // startActivities( new Intent[]{intent} );       //yada
            startActivity( intent );

            return true;
        } else if (item.getItemId() == R.id.sifirla) {
            count = 0;
            btn.setText( "" + count );
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt( "count_anahtarı", count );                                                                                                    //int veri olduğu için isim ve değişken girildi
        editor.commit();                                                                                                                                      //vekaydedildi      kaydedildi ama tekrar açıldığındao veriyi dayaça atmamızlazım
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {           //programarka plan rengi direk değiştiğinde işleme konması için dinleyici koyduk
        ayarlariYukle();
    }
}

