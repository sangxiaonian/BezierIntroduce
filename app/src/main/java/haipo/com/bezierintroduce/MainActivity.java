package haipo.com.bezierintroduce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private BasicPathView basic;
    private AdvancePathView advance;
    private HearView hearView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        basic = (BasicPathView) findViewById(R.id.basic);
        advance = (AdvancePathView) findViewById(R.id.advance);
        hearView= (HearView) findViewById(R.id.high);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.basic:
                basic.setVisibility(View.VISIBLE);
                hearView.setVisibility(View.GONE);
                advance.setVisibility(View.GONE);
                break;
            case R.id.advance:
                advance.setVisibility(View.VISIBLE);
                hearView.setVisibility(View.GONE);
                basic.setVisibility(View.GONE);
                break;
            case R.id.high:
                hearView.setVisibility(View.VISIBLE);
                advance.setVisibility(View.GONE);
                basic.setVisibility(View.GONE);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
