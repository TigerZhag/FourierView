package github.tiger.fourierview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FourierView fourierView = (FourierView) findViewById(R.id.fourierView);

        fourierView.addCircle(new Circle(0xFFCDDC39));
        fourierView.addCircle(new Circle(0xFF03A9F4));
        fourierView.addCircle(new Circle(0xFFE91E63));
    }
}
