package com.example.economy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Game.Data;
import Game.JSONHandler;

public class CapitalismActivity extends AppCompatActivity {

    private static final int COUNTDOWN = 10;
    private static final int TOTAL_COUNT = 50;
    private static final long DELAY = 1000L;

    private Button _producer;
    private Button _starter;
    private Button _returner;
    private TextView _countdown_text;
    private TextView _counter_text;
    private TextView _result;

    private Thread _countdown;

    private boolean _isPlayerDone = false;
    private boolean _isAIDone = false;
    private boolean _isRestart = false;

    private int _count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitalism);

        _producer = (Button) findViewById(R.id.producer);
        _starter = (Button) findViewById(R.id.start_capitalism);
        _returner = (Button) findViewById(R.id.return_menu_capitalism);
        _countdown_text = (TextView) findViewById(R.id.countdown_text);
        _counter_text = (TextView) findViewById(R.id.counter_text);
        _result = (TextView) findViewById(R.id.result_capitalism);

        _producer.setVisibility(View.INVISIBLE);
        _returner.setVisibility(View.INVISIBLE);
        _result.setVisibility(View.INVISIBLE);

        _producer.setOnClickListener((v) -> {
            _count++;
            _counter_text.setText(Integer.toString(_count));

            if (_count == TOTAL_COUNT)
            {
                _isPlayerDone = true;
                FinishPlayer();
            }
        });

        _starter.setOnClickListener((v) -> {
            if (_isRestart == true)
                RestartRound();

            _producer.setVisibility(View.VISIBLE);

            StartCountdown();

            _isRestart = true;
            _starter.setText("restart round");
        });

        _returner.setOnClickListener((v) -> {
            Intent intent = new Intent(CapitalismActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    private void StartCountdown() {
        _countdown = new Thread(() -> {
            try {
                for (int i = COUNTDOWN; i >= 0; i--)
                {
                    Thread.sleep(DELAY);
                    _countdown_text.setText(Integer.toString(i));
                }

                _isAIDone = true;
                FinishAI();
            } catch (InterruptedException e) {
                return;
            }
        });

        _countdown.start();
    }

    private void RestartRound() {
        Intent intent = new Intent(CapitalismActivity.this, CapitalismActivity.class);
        startActivity(intent);
    }

    private void FinishAI() {
        if (_isPlayerDone == true)
            return;

        _result.setVisibility(View.VISIBLE);
        _result.setText("Рынок порешал!");
    }

    private void FinishPlayer() {
        if (_isAIDone == true)
            return;

        _result.setVisibility(View.VISIBLE);
        _result.setText("Прибыль ваша!");

        _returner.setVisibility(View.VISIBLE);
        _countdown = null;

        JSONHandler jsonHandler = new JSONHandler();

        Data data = jsonHandler.TryFromJSON(this);
        if (data == null)
            data = new Data();

        data.IsGameDone = true;

        jsonHandler.TryToJSON(this, data);
    }
}