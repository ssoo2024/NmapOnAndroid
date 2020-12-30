package com.example.mynmap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UseShell extends AppCompatActivity {
    Button go, gomain;
    EditText input_command;
    TextView cmd_result;
    String cmd, mOutput;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shell);

        go = (Button)findViewById(R.id.go);
        gomain = (Button)findViewById(R.id.gomain);
        input_command = (EditText)findViewById(R.id.command);
        cmd_result = (TextView)findViewById(R.id.cmd_result);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShellExecuter exe = new ShellExecuter();
                mOutput = exe.Executer(input_command.getText().toString());
                cmd_result.setText("");
                cmd_result.setText(mOutput);
            }
        });

        gomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd_result.setText("");
                onBackPressed();
            }
        });

    }
}
