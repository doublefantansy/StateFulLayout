package hzkj.cc.statefullayout;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import hzkj.cc.stateful.StateFulLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final StateFulLayout layout = findViewById(R.id.lay);
        LinearLayout layout1 = findViewById(R.id.layout);
        layout.init(new StateFulLayout.RefreshListenner() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.showState(StateFulLayout.EMPTY);
                    }
                }, 1000);
            }
        }, layout1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.showState(StateFulLayout.EMPTY);
            }
        }, 1000);
    }
}
