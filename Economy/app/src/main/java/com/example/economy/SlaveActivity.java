package com.example.economy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import Game.Data;
import Game.JSONHandler;

enum SlaveType {
    Economy,
    Excess,
    Capture,
    Labour,
    Result,
    Assignment
}

class ControllerSlave {

    public Button button;
    public ImageView check;
    public SlaveType type;

    public ControllerSlave(Button b, ImageView c, SlaveType t) {
        button = b;
        check = c;
        type = t;
    }
}

public class SlaveActivity extends AppCompatActivity {

    private SlaveType[] _correctOrder = new SlaveType[] {
            SlaveType.Economy, SlaveType.Excess, SlaveType.Capture, SlaveType.Labour, SlaveType.Result, SlaveType.Assignment,
    };

    private ArrayList<SlaveType> _order = new ArrayList<SlaveType>();;

    private ControllerSlave[] _controllers;

    private Button _returner;

    private boolean _isEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slave);

        _returner = findViewById(R.id.returner_slave_menu);
        _returner.setOnClickListener((v -> {
            Intent intent = new Intent(SlaveActivity.this, MainActivity.class);
            startActivity(intent);
        }));
        _returner.setVisibility(View.INVISIBLE);

        _controllers = new ControllerSlave[] {
                new ControllerSlave(findViewById(R.id.economy_button), findViewById(R.id.economy_highlighting), SlaveType.Economy),
                new ControllerSlave(findViewById(R.id.excess_button), findViewById(R.id.excess_highlighting), SlaveType.Excess),
                new ControllerSlave(findViewById(R.id.capture_button), findViewById(R.id.capture_highlighting), SlaveType.Capture),
                new ControllerSlave(findViewById(R.id.labour_button), findViewById(R.id.labour_highlighting), SlaveType.Labour),
                new ControllerSlave(findViewById(R.id.result_button), findViewById(R.id.result_highlighting), SlaveType.Result),
                new ControllerSlave(findViewById(R.id.assignment_button), findViewById(R.id.assignment_highlighting), SlaveType.Assignment)
        };

        // отключение всех галочек и привязка кнопок
        for (ControllerSlave c : _controllers) {
            c.button.setOnClickListener((v) -> Click(c));
            c.check.setVisibility(View.INVISIBLE);
        }
    }

    private void Click(ControllerSlave c) {
        if (_isEnd == true)
            return;

        if (c.check.getVisibility() == View.INVISIBLE){
            c.check.setVisibility(View.VISIBLE);

            _order.add(c.type);

            Win();
        }
        else {
            c.check.setVisibility(View.INVISIBLE);

            _order.remove(c.type);
        }
    }

    private void Win() {
        if (_order.size() != _correctOrder.length)
            return;

        for (int i = 0; i < 6; i++) {
            if (_correctOrder[i] != _order.get(i))
                return;
        }

        JSONHandler jsonHandler = new JSONHandler();

        Data data = jsonHandler.TryFromJSON(this);
        if (data == null)
            data = new Data();

        data.IsAllowedFeudalism = true;

        jsonHandler.TryToJSON(this, data);

        _returner.setVisibility(View.VISIBLE);
        _isEnd = true;
    }
}