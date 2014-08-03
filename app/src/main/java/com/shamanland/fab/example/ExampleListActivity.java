package com.shamanland.fab.example;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.shamanland.fab.ShowHideOnScroll;

public class ExampleListActivity extends Activity {
    @Override
    protected void onCreate(Bundle state) {
        setTheme(getSavedTheme());

        super.onCreate(state);

        setContentView(R.layout.a_example_list);

        View fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), R.string.action_clicked, Toast.LENGTH_SHORT).show();
            }
        });

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new ExampleAdapter());
        listView.setOnTouchListener(new ShowHideOnScroll(fab));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(view.getContext(), ExampleDetailsActivity.class));
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
