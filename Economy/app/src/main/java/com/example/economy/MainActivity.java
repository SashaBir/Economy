package com.example.economy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import Game.Data;
import Game.JSONHandler;
import Game.Tile;
import Game.TileType;
import Game.World;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private ImageView _labelWords;
    private ImageView _labelWordsDone;

    private World _world;
    private JSONHandler _json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map map = new HashMap<TileType, Tile>();
        map.put(TileType.Ancient,
                new Tile(
                    TileType.Slave,
                    findViewById(R.id.lock_slave),
                    findViewById(R.id.starter_slave)
                )
        );

        map.put(TileType.Slave,
                new Tile(
                        TileType.Slave,
                        findViewById(R.id.lock_slave),
                        findViewById(R.id.starter_slave)
                )
        );

        map.put(TileType.Feudalism,
                new Tile(
                        TileType.Feudalism,
                        findViewById(R.id.lock_feudalism),
                        findViewById(R.id.starter_feudalism)
                )
        );

        map.put(TileType.Proletariat,
                new Tile(
                        TileType.Proletariat,
                        findViewById(R.id.lock_proletariat),
                        findViewById(R.id.starter_proletariat)
                )
        );

        _labelWords = (ImageView) findViewById(R.id.label_worlds);
        _labelWordsDone = (ImageView) findViewById(R.id.label_worlds_are_done);

        // WORLDS
        _world = new World(map);
        _world.LockedAll();
        _json = new JSONHandler();

        // unlock tiles
        Data data = _json.TryFromJSON(this);
        if (data != null) {
            _world.UnlockedAble(data);

            if (data.IsGameDone == true) {
                _labelWordsDone.setVisibility(View.VISIBLE);
                _labelWords.setVisibility(View.INVISIBLE);
            }
            else {
                _labelWordsDone.setVisibility(View.INVISIBLE);
                _labelWords.setVisibility(View.VISIBLE);
            }
        }
        else {
            _labelWordsDone.setVisibility(View.INVISIBLE);
            _labelWords.setVisibility(View.VISIBLE);
        }

        Button starter_ancient = (Button) findViewById(R.id.starter_ancient);
        starter_ancient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AncientActivity.class);
                startActivity(intent);
            }
        });

        Button starter_slave = (Button) findViewById(R.id.starter_slave);
        starter_slave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SlaveActivity.class);
                startActivity(intent);
            }
        });

        Button starter_feudalism = (Button) findViewById(R.id.starter_feudalism);
        starter_feudalism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeudalismActivity.class);
                startActivity(intent);
            }
        });

        Button starter_proletariat = (Button) findViewById(R.id.starter_proletariat);
        starter_proletariat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CapitalismActivity.class);
                startActivity(intent);
            }
        });
    }
}