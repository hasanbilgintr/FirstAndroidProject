package com.hasan.ilk_mobil_proje;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import androidx.annotation.Nullable;

//üst klasörünün new=>java Classtan açıldı
public class Ayarlar extends PreferenceActivity   /*SeçeneklerAktivitesi demektir onun iiçin miras aldık yada kalıtıyoruz*/ {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ayarlar);


    }
}
