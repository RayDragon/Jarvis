package com.hashbook.www.jarvis;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.hashbook.www.jarvis.jarvisextra.JarvisWork;

import java.util.Locale;
import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.Result;

import static java.lang.Thread.sleep;

/**
 * Created by ray on 11/5/17.
 */

public class JarvisServis extends Service implements AIListener
{
    TextToSpeech tspeak;
    private SpeechRecogManager SRManager;
    JarvisWork jw;
    private AIService aiService;
    String log;
    Intent i;

    public JarvisServis() {
        super();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.i = intent;

        final AIConfiguration config = new AIConfiguration("5444ff5dfb844256a67c31b2a9e62ded",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);
        jw = new JarvisWork("192.168.43.139", this);
        SRManager = new SpeechRecogManager(this){
            @Override
            public void onTriggered() {
                super.onTriggered();

                SRManager.mPocketSphinxRecognizer.cancel();
                SRManager.mPocketSphinxRecognizer.stop();
                aiService.startListening();
            }
        };


        tspeak = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tspeak.setLanguage(Locale.UK);
                }
            }
        });


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResult(ai.api.model.AIResponse result) {
        String fResult = jw.process(result);
        tspeak.speak(fResult, TextToSpeech.QUEUE_FLUSH, null);
        final Thread wait = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(tspeak.isSpeaking()){
                        sleep(1000);

                    }
                    SRManager.restartSearch(SRManager.KWS_SEARCH);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        wait.start();
    }

    @Override
    public void onError(ai.api.model.AIError error) {
        //tv.setText(error.toString());
        ///SRManager.mPocketSphinxRecognizer.startListening(SRManager.KWS_SEARCH);

        this.stopService(this.i);
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

}
