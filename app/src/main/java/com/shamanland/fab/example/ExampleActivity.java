package com.shamanland.fab.example;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class ExampleActivity extends Activity {
    @Override
    protected void onCreate(Bundle state) {
        setTheme(getSavedTheme());

        super.onCreate(state);

        setContentView(R.layout.a_example);

        final ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new ExampleAdapter());

        final View fab = findViewById(R.id.fab);

        final GestureDetector detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            float downY;
            boolean direction;
            boolean handling;

            @Override
            public boolean onDown(MotionEvent e) {
                downY = e.getY();
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (handling) {
                    return false;
                }

                if (direction != distanceY > 0) {
                    direction = !direction;
                    downY = e2.getY();
                }

                float distance = downY - e2.getY();

                if (distance < -50) {
                    // NOTE scroll down
                    if (fab.getVisibility() != View.VISIBLE) {
                        fab.setVisibility(View.VISIBLE);
                    }
                } else if (distance > 50) {
                    // NOTE scroll up
                    if (fab.getVisibility() == View.VISIBLE) {
                        fab.setVisibility(View.GONE);
                    }
                }

                return false;
            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Menu.NONE, R.id.switch_theme, Menu.NONE,
                getSavedTheme() == R.style.AppTheme_Dark ? R.string.light_theme : R.string.dark_theme);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_theme:
                switchTheme();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences("example", MODE_PRIVATE);
    }

    private void switchTheme() {
        SharedPreferences prefs = getPrefs();
        prefs.edit().putBoolean("theme", !prefs.getBoolean("theme", false)).commit();

        recreate();
    }

    private int getSavedTheme() {
        return getPrefs().getBoolean("theme", false) ? R.style.AppTheme_Dark : R.style.AppTheme_Light;
    }

    @Override
    public void recreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.recreate();
        } else {
            finish();
            startActivity(getIntent());
        }
    }
}
