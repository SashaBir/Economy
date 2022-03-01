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

enum GamesType {
    Plant,
    Wheel,
    Cow,
    Hoe
}

class ControllerAncient {

    public Button button;
    public ImageView check;
    public GamesType type;

    public ControllerAncient(Button b, ImageView c, GamesType t) {
        button = b;
        check = c;
        type = t;
    }
}

public class AncientActivity extends AppCompatActivity {

    private GamesType[] _correctOrder = new GamesType[] {
            GamesType.Plant, GamesType.Hoe, GamesType.Cow, GamesType.Wheel,
        };

    private ArrayList<GamesType> _order = new ArrayList<GamesType>();;

    private ControllerAncient[] _controllers;

    private Button _returner;

    private boolean _isEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancient);

        _returner = findViewById(R.id.returner_menu);
        _returner.setOnClickListener((v -> {
            Intent intent = new Intent(AncientActivity.this, MainActivity.class);
            startActivity(intent);
        }));

        _returner.setVisibility(View.INVISIBLE);

        _controllers = new ControllerAncient[] {
                new ControllerAncient(findViewById(R.id.plant), findViewById(R.id.check_plant), GamesType.Plant),
                new ControllerAncient(findViewById(R.id.hoe), findViewById(R.id.check_hoe), GamesType.Hoe),
                new ControllerAncient(findViewById(R.id.cow), findViewById(R.id.check_cow), GamesType.Cow),
                new ControllerAncient(findViewById(R.id.wheel), findViewById(R.id.check_wheel), GamesType.Wheel),
        };

        // отключение всех галочек и привязка кнопок
        for (ControllerAncient c : _controllers) {
            c.button.setOnClickListener((v) -> Click(c));
            c.check.setVisibility(View.INVISIBLE);
        }
    }

    private void Click(ControllerAncient c) {
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

        for (int i = 0; i < 4; i++) {
            if (_correctOrder[i] != _order.get(i))
                return;
        }

        JSONHandler jsonHandler = new JSONHandler();

        Data data = jsonHandler.TryFromJSON(this);
        if (data == null)
            data = new Data();

        data.IsAllowedSlave = true;

        jsonHandler.TryToJSON(this, data);

        _returner.setVisibility(View.VISIBLE);
        _isEnd = true;
    }
}