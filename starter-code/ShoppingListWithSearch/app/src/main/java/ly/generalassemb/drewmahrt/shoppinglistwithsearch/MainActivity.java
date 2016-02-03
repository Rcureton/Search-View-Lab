package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView mShoppingListView;
    private CursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShoppingListView = (ListView)findViewById(R.id.shopping_list_view);

        Cursor cursor = ShoppingSQLiteOpenHelper.getInstance(MainActivity.this).getShoppingList();

        mCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,new String[]{ShoppingSQLiteOpenHelper.COL_ITEM_NAME},new int[]{android.R.id.text1},0);
        mShoppingListView.setAdapter(mCursorAdapter);

        handleIntent (getIntent() );

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent( Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query= intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, "You are searching for " + query, Toast.LENGTH_SHORT).show();


            Cursor cursor= ShoppingSQLiteOpenHelper.getInstance(this).getShoppingList2(query);
            mCursorAdapter.swapCursor(cursor);


        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_options_menu, menu);
        SearchManager searchManager= (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView= (SearchView)menu.findItem(R.id.search).getActionView();


        SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(info);

        return true;
    }
}
