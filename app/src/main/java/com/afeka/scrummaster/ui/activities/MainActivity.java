package com.afeka.scrummaster.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import com.afeka.scrummaster.interfaces.NavigationHost;
import com.afeka.scrummaster.R;
import com.afeka.scrummaster.ui.fragments.TasksFragment;
import com.afeka.scrummaster.ui.fragments.TeamsFragment;
import com.afeka.scrummaster.data.Team;
import com.afeka.scrummaster.layout.User;
import com.afeka.scrummaster.logic.UserService;


public class MainActivity extends AppCompatActivity implements NavigationHost {
    private Team boardTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        User currentUser = UserService.getInstance(this).currentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, AuthActivity.class));
            return;
        }

        if (boardTeam == null) {
            navigateTo(new TeamsFragment(), false);
        } else {
            navigateTo(new TasksFragment(boardTeam), false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_op_item) {
            UserService.getInstance(this).logout();
            boardTeam = null;
            startActivity(new Intent(this, AuthActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public void setBoardTeam(Team team) {
        boardTeam = team;
    }
}