package justinlaykovich.umpirebuddy10;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;

import org.w3c.dom.Text;


public class UmpireBuddy extends AppCompatActivity {
    AlertDialog.Builder alert_dialog;
    SharedPreferences shared_pref;
    TextView strike_count;
    TextView ball_count;
    TextView out_count;
    int strike;
    int ball;
    int out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire_buddy);

        strike_count = (TextView) findViewById(R.id.strike_count);
        ball_count = (TextView) findViewById(R.id.ball_count);
        out_count = (TextView) findViewById(R.id.total_outs_count);

        shared_pref = this.getPreferences(Context.MODE_PRIVATE);
        out = shared_pref.getInt(getString(R.string.total_outs_count), 0);
        out_count.setText(String.format("%d",out));

        strike = 0;
        ball = 0;

        alert_dialog = new AlertDialog.Builder(UmpireBuddy.this).setPositiveButton("OK", new AlertDialog.OnClickListener(){
            public void onClick(DialogInterface D, int I) {
                reset();
                ball_count.setText(String.format("%d",ball));
                strike_count.setText(String.format("%d",strike));
            }
        });
    }

    public void ball_button_click(View v) {
        ball += 1;
        if(ball == 4) {
            alert_dialog.setMessage("WALK!");
            alert_dialog.show();
        }
        else
            ball_count.setText(String.format("%d",ball));
    }

    public void strike_button_click(View v) {
        strike += 1;
        if(strike == 3) {
            alert_dialog.setMessage("OUT!");
            out += 1;
            shared_pref.edit().putInt(getString(R.string.total_outs_count),out).commit();
            out_count.setText(String.format("%d",out));
            alert_dialog.show();
        }
        else
            strike_count.setText(String.format("%d",strike));
    }

    public void reset() {
        strike = 0;
        ball = 0;
        strike_count.setText(String.format("%d",strike));
        ball_count.setText(String.format("%d",ball));
    }

    public void about() {
        Intent intent = new Intent(this,About.class);
        startActivity(intent);
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.umpire_buddy_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                reset();
                return true;
            case R.id.about:
                about();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
