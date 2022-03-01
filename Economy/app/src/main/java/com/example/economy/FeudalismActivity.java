package com.example.economy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Game.Data;
import Game.JSONHandler;

class Point {

    public final TextView _score;
    public final Button _adderPeople;
    public final Button _adderLand;
    public final Button _cleaner;

    public final float _earthFertility;

    private int _count = 0;
    private float _countWin = 0;

    public Point(TextView score, Button adderPeople, Button adderLand, Button cleaner, float earthFertility, float countWin) {
        _score = score;
        _adderPeople = adderPeople;
        _adderLand = adderLand;
        _cleaner = cleaner;
        _earthFertility = earthFertility;

        _adderPeople.setOnClickListener((v) -> AddPeople(FeudalismActivity.People));
        _adderLand.setOnClickListener((v) -> AddLand(FeudalismActivity.Land));
        _cleaner.setOnClickListener((v) -> Remove());
        _countWin = countWin;
    }

    public boolean IsDone() {
        return _countWin <= _count;
    }

    private void AddPeople(int people) {
        AddCount(people);
    }

    private void AddLand(int land) {
        AddCount(land);
    }

    private void AddCount(int value) {
        if (FeudalismActivity.TitleScore - value < 0)
            return;

        FeudalismActivity.TitleScore -= value;
        FeudalismActivity.TextScore.setText(Integer.toString(FeudalismActivity.TitleScore));
        _count += value;
        _score.setText(Integer.toString(_count));
    }

    private void Remove() {
        int current = Integer.parseInt((String) FeudalismActivity.TextScore.getText());
        current += _count;
        FeudalismActivity.TextScore.setText(Integer.toString(current));

        FeudalismActivity.TitleScore = current;
        _score.setText("0");
        _count = 0;
    }
}

public class FeudalismActivity extends AppCompatActivity {

    public static int TitleScore = 100;
    public static final int People = 1;
    public static final int Land = 5;

    public static TextView TextScore;

    private Button _checkSuitable;
    private Button _returner;

    private Point _pointA;
    private Point _pointB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feudalism);

        TextScore = (TextView) findViewById(R.id.text_score);
        TitleScore = Integer.parseInt((String) TextScore.getText());

        _pointA = new Point(
                findViewById(R.id.text_score_point_a),
                findViewById(R.id.adder_people_a),
                findViewById(R.id.adder_land_a),
                findViewById(R.id.feudalism_returner),
                0.84f,
                45
        );

        _pointB = new Point(
                findViewById(R.id.text_score_point_b),
                findViewById(R.id.adder_people_b),
                findViewById(R.id.adder_land_b),
                findViewById(R.id.cleaner_point_b),
                0.63f,
                55
        );

        _returner = (Button) findViewById(R.id.feudalism_returner);
        _checkSuitable = (Button) findViewById(R.id.checher);

        _checkSuitable.setOnClickListener((v) -> TryWin());
        _returner.setOnClickListener((v) -> startActivity(new Intent(FeudalismActivity.this, MainActivity.class)));

        _returner.setVisibility(View.INVISIBLE);
    }

    private void TryWin() {
        if (_pointA.IsDone() == true && _pointB.IsDone() == true) {
            _returner.setVisibility(View.VISIBLE);

            JSONHandler jsonHandler = new JSONHandler();

            Data data = jsonHandler.TryFromJSON(this);
            if (data == null)
                data = new Data();

            data.IsAllowedProletarian = true;

            jsonHandler.TryToJSON(this, data);
        }
    }
}