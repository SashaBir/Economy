package Game;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Tile {

    private TileType _type;
    private ImageView _locked;
    private Button _starter;

    public Tile(TileType type, ImageView locked, Button starter) {
        _type = type;
        _starter = starter;
        _locked = locked;
    }

    public void Unlocked() {
        _starter.setVisibility(View.VISIBLE); // выключена кнопка
        _locked.setVisibility(View.INVISIBLE);
    }

    public void Locked() {
        _starter.setVisibility(View.INVISIBLE); // выключена кнопка
        _locked.setVisibility(View.VISIBLE); // выключена кнопка
    }
}
