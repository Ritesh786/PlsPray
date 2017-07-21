package plspray.infoservices.lue.plspray;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminMessage extends AppCompatActivity implements View.OnClickListener {

    TextView  mtitletext,mdesciptiontxt;
    Button mokbtn;

    String title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message);

        mtitletext = (TextView) findViewById(R.id.title);
        mdesciptiontxt = (TextView) findViewById(R.id.description);

        mokbtn = (Button) findViewById(R.id.ok_btn);
        mokbtn.setOnClickListener(this);

        title=getIntent().getStringExtra("title");
        description=getIntent().getStringExtra("description");


        mtitletext.setText(title);
        mdesciptiontxt.setText(description);

    }

    @Override
    public void onClick(View v) {

        AdminMessage.this.finish();

    }
}
