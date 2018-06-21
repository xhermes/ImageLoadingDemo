package xeno.imageloadingdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import xeno.imageloadingdemo.R;
import xeno.imageloadingdemo.model.LockModel;

/**
 * Created by xeno on 2018/6/21.
 */

public class LockActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        Button btn =(Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LockModel model = new LockModel();
                model.play();
            }
        });

    }
}
