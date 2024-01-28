package pack.sor.carnote;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import pack.sor.carnote.historylist.HistoryAdapter;
import pack.sor.carnote.historylist.HistoryRemover;
import pack.sor.carnote.model.AutoData;
import pack.sor.carnote.model.CollisionRecord;
import pack.sor.carnote.model.RepairRecord;
import pack.sor.carnote.model.TankUpRecord;
import pack.sor.carnote.model.ViewHolderAdaptable;
import pack.sor.carnote.persistency.SharedPreferencesSaver;

/**
 * Okno menu głownego
 */
public class MenuActivity extends AppCompatActivity implements HistoryRemover
{
    public static final String SPECIAL_DATA = "SPECIAL_DATA";
    public static final int NEW_CAR_REQUEST_CODE = 111;
    public static final int TANK_REQUEST_CODE = 222;
    public static final int GPS_REQUEST_CODE = 333;
    private static final int COLLISTION_REQUEST_CODE = 444;
    private static final int REPAIR_REQUEST_CODE = 555;

    private Button goToCarInfoButton;
    private Button goToTankFormButton;
    private Button goToCollisionFormButton;
    private Button goToRepairFormButton;

    private Spinner autoChooseSpinner;
    private ArrayList<AutoData> autoList;
    private ArrayAdapter<AutoData> spinnerAdapter;
    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter historyAdapter;
    private RecyclerView.LayoutManager historyLayoutManager;
    private ArrayList<ViewHolderAdaptable> allItems;

    /**
     * Wykonuje się na utworzenie okna
     * Inicjalizujemy tu okno
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        setTitle(getResources().getString(R.string.app_name));
        initAutoList();
        initViews();
        if (autoList.isEmpty())
        {
            Intent intent = new Intent(MenuActivity.this, NewCarActivity.class);
            startActivityForResult(intent, NEW_CAR_REQUEST_CODE);
        }
    }

    /**
     * Zaczytuje auta zapisane w pamięci
     */
    private void initAutoList()
    {
        ArrayList<AutoData> newAutoList = SharedPreferencesSaver.loadFrom(getPreferences(MODE_PRIVATE));
        if (newAutoList != null)
        {
            autoList = newAutoList;
        } else
        {
            autoList = new ArrayList<>();
        }
    }

    /**
     * Inicjalizuje widoki: zasila listę aut i historię
     */
    private void initViews()
    {
        goToCarInfoButton = (Button) findViewById(R.id.go_to_car_info_button);
        goToTankFormButton = (Button) findViewById(R.id.go_to_tank_form_button);
        goToCollisionFormButton = (Button) findViewById(R.id.go_to_collision_button);
        goToRepairFormButton = (Button) findViewById(R.id.go_to_repair_button);
        autoChooseSpinner = (Spinner) findViewById(R.id.auto_choose_spinner);
        historyRecyclerView = (RecyclerView) findViewById(R.id.historyRecyclerView);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        initAutoListSpinner();
        initRecyclerView();

        goToRepairFormButton.setOnClickListener(goToNewRepairActivity());
        goToCollisionFormButton.setOnClickListener(goToNewCollisionActivity());
        goToCarInfoButton.setOnClickListener(goToCarInfoActivity());
        goToTankFormButton.setOnClickListener(goToTankUpActivity());
        autoChooseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                initRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void initRecyclerView()
    {
        historyLayoutManager = new LinearLayoutManager(this);
        historyRecyclerView.setLayoutManager(historyLayoutManager);

        historyRecyclerView.setHasFixedSize(true);

        updateAllHistoryItems();
        historyAdapter = new HistoryAdapter(allItems, this, this);
        historyRecyclerView.setAdapter(historyAdapter);
    }

    private ArrayList<ViewHolderAdaptable> updateAllHistoryItems()
    {
        allItems = new ArrayList<>();
        if (getCurrentAuto()!=null)
        {
            allItems.addAll(getCurrentAuto().getCollisionRecords());
            allItems.addAll(getCurrentAuto().getTankUpRecords());
            allItems.addAll(getCurrentAuto().getRepairRecords());
        }
        return allItems;
    }

    private void initAutoListSpinner()
    {
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, autoList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoChooseSpinner.setAdapter(spinnerAdapter);
    }

    /**
     * Wykonuje się po przywróceniu okna na pierwsze tło
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferencesSaver.saveTo(autoList, getPreferences(MODE_PRIVATE));
    }

    @Nullable
    private AutoData getCurrentAuto()
    {
        return (AutoData) autoChooseSpinner.getSelectedItem();
    }

    //region Przejścia i powroty między oknami, uprawnienia
    @NonNull
    private View.OnClickListener goToTankUpActivity()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuActivity.this, NewTankupActivity.class);
                intent.putExtra(SPECIAL_DATA, getCurrentAuto());
                startActivityForResult(intent, TANK_REQUEST_CODE);
            }
        };
    }

    private View.OnClickListener goToCarInfoActivity()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuActivity.this, CarInfoActivity.class);
                intent.putExtra(SPECIAL_DATA, getCurrentAuto());
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener goToNewCollisionActivity()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuActivity.this, NewCollisionActivity.class);
                intent.putExtra(SPECIAL_DATA, getCurrentAuto());
                startActivityForResult(intent, COLLISTION_REQUEST_CODE);
            }
        };
    }

    private View.OnClickListener goToNewRepairActivity()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuActivity.this, NewRepairActivity.class);
                intent.putExtra(SPECIAL_DATA, getCurrentAuto());
                startActivityForResult(intent, REPAIR_REQUEST_CODE);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.summary_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add_car:
                Intent carIntent = new Intent(MenuActivity.this, NewCarActivity.class);
                startActivityForResult(carIntent, NEW_CAR_REQUEST_CODE);
                return true;
            case R.id.action_gps:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1231);
                } else
                {
                    Intent intent = new Intent(this, GpsActivity.class);
                    intent.putExtra(MenuActivity.SPECIAL_DATA, getCurrentAuto());
                    startActivityForResult(intent, GPS_REQUEST_CODE);
                }
                return true;
            case R.id.action_logout:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                //finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Intent intent = new Intent(this, GpsActivity.class);
            intent.putExtra(MenuActivity.SPECIAL_DATA, getCurrentAuto());
            startActivityForResult(intent, GPS_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == NEW_CAR_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    AutoData newAutoData = (AutoData) data.getExtras().get(NewCarActivity.AUTO_DATA_NEW_CAR);
                    Boolean isNewCarMasterCar = (Boolean) data.getExtras().get(NewCarActivity.IS_NEW_CAR_MASTER_CAR);
                    if (isNewCarMasterCar != null && isNewCarMasterCar)
                    {
                        autoList.add(0, newAutoData);
                        spinnerAdapter.notifyDataSetChanged();
                        autoChooseSpinner.setSelection(0, false);
                    } else
                    {
                        autoList.add(newAutoData);
                        spinnerAdapter.notifyDataSetChanged();
                        autoChooseSpinner.setSelection(autoList.size() - 1, false);
                    }
                }
            }
        }
        if (requestCode == TANK_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    TankUpRecord newTankUp = (TankUpRecord) data.getExtras().get(NewTankupActivity.NEW_TANKUP_RECORD);
                    if (newTankUp != null)
                    {
                        getCurrentAuto().getTankUpRecords().add(0, newTankUp);
                        initRecyclerView();
                    }
                }
            }
        }
        if (requestCode == COLLISTION_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    CollisionRecord newCollision = (CollisionRecord) data.getExtras().get(NewCollisionActivity.NEW_COLLISION_TAG);
                    if (newCollision != null)
                    {
                        getCurrentAuto().getCollisionRecords().add(0, newCollision);
                        initRecyclerView();
                    }
                }
            }
        }
        if (requestCode == REPAIR_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    RepairRecord newRepair = (RepairRecord) data.getExtras().get(NewRepairActivity.NEW_REPAIR_RECORD);
                    if (newRepair != null)
                    {
                        getCurrentAuto().getRepairRecords().add(0, newRepair);
                        initRecyclerView();
                    }
                }
            }
        }
        if (requestCode == GPS_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_CANCELED)
            {
                if (data != null)
                {
                    Long newSpeedRecord = (Long) data.getExtras().get(GpsActivity.NEW_GPS_RECORD);
                    if (newSpeedRecord != null && getCurrentAuto() != null)
                    {
                        if (getCurrentAuto().getBestSpeed().compareTo(newSpeedRecord) < 0)
                        {
                            getCurrentAuto().setBestSpeed(newSpeedRecord);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void remove(ViewHolderAdaptable viewHolderAdaptable)
    {
        getCurrentAuto().getTankUpRecords().remove(viewHolderAdaptable);
        getCurrentAuto().getCollisionRecords().remove(viewHolderAdaptable);
        getCurrentAuto().getRepairRecords().remove(viewHolderAdaptable);
        historyAdapter.notifyDataSetChanged();
    }
    //endregion
}
