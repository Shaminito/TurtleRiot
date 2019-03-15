package com.turtleriot;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenFragment extends Fragment {

    private View v;

    private ImageView ivLOGOSplash;
    private TextView tvNomApp;

    public SplashScreenFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_splash_screen, container, false);


        ivLOGOSplash = v.findViewById(R.id.ivLOGOSplash);
        tvNomApp = v.findViewById(R.id.tvNomApp);

        Animation aparecer = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_pantalla_inicio);
        ivLOGOSplash.startAnimation(aparecer);
        tvNomApp.startAnimation(aparecer);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation despaparecer = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_app_name);
                tvNomApp.startAnimation(despaparecer);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvNomApp.setText("");
                    }
                }, 2205);

                Animation animTurtle = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_bienvenida);
                ivLOGOSplash.startAnimation(animTurtle);
            }
        }, 2500);

        return v;
    }



}
