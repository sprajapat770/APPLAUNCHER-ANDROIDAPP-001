package com.example.surajlauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GetApps extends AppCompatActivity {

    PackageManager packageManager;
    public static List<AppInfo> apps;
    GridView grdView;
    ListView listView;
    public static ArrayAdapter<AppInfo> adapter;
    boolean listViewSelected = false;
    ImageView imgList, imgGrid,imgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist);
        findViewById();
        init();
    }

    void findViewById() {
        listView = findViewById(R.id.listAllApps);
        grdView = findViewById(R.id.grdAllApps);
        imgGrid = findViewById(R.id.imgGrid);
        imgList = findViewById(R.id.imgList);
        imgHome = findViewById(R.id.imgHome);
    }

    void init() {
        apps = null;
        adapter = null;
        loadApps();
        loadListView();
        addGridListeners();
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = null;
                listViewSelected = false;
                loadItems();
            }
        });
        imgList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = null;
                listViewSelected = true;
                loadItems();
            }
        });


    }

    void loadItems() {
        if (listViewSelected) {
            if (listView.getVisibility()== View.VISIBLE){
                return;
            }
            listView.setVisibility(View.VISIBLE);
            grdView.setVisibility(View.GONE);
            loadListView();
            addGridListeners();
        } else {
            if (grdView.getVisibility()== View.VISIBLE){
            return;
            }
            grdView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            loadGridListView();
            addGridListeners();
        }
    }

    public void addGridListeners() {
        try {
            grdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = packageManager.getLaunchIntentForPackage(apps.get(i).name.toString());
                    if ((apps.get(i).name.toString()).equals("com.example.surajlauncher")) {
                        return;
                    } else {
                        GetApps.this.startActivity(intent);

                    }
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = packageManager.getLaunchIntentForPackage(apps.get(i).name.toString());
                    if ((apps.get(i).name.toString()).equals("com.example.surajlauncher")) {
                        return;
                    } else {
                        GetApps.this.startActivity(intent);

                    }


                }
            });
        } catch (Exception ex) {
            Toast.makeText(GetApps.this, ex.getMessage().toString() + " Grid", Toast.LENGTH_LONG).show();
            Log.e("Error Grid", ex.getMessage().toString() + " Grid");
        }

    }

    private void loadListView() {
        try {
            if (adapter == null) {
                adapter = new ArrayAdapter<AppInfo>(this, R.layout.row_list_items, apps) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        ViewHolderItem viewHolder = null;

                        if (convertView == null) {
                            convertView = getLayoutInflater().inflate(
                                    R.layout.row_list_items, parent, false
                            );
                            viewHolder = new ViewHolderItem();
                            viewHolder.icon = convertView.findViewById(R.id.img_icon);
                            viewHolder.label = convertView.findViewById(R.id.txt_label);

                            convertView.setTag(viewHolder);
                        } else {
                            viewHolder = (ViewHolderItem) convertView.getTag();
                        }

                        AppInfo appInfo = apps.get(position);
                        if (appInfo != null) {
                            viewHolder.icon.setImageDrawable(appInfo.icon);
                            viewHolder.label.setText(appInfo.label);
                        }
                        return convertView;

                    }

                    final class ViewHolderItem {
                        ImageView icon;
                        TextView label;
                    }
                };
            }
            listView.setAdapter(adapter);
            listView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        } catch (Exception ex) {
            Toast.makeText(GetApps.this, ex.getMessage().toString() + " loadListView", Toast.LENGTH_LONG).show();
            Log.e("Error loadListView", ex.getMessage().toString() + " loadListView");
        }
    }

    private void loadGridListView() {
        try {
            if (adapter == null) {
                adapter = new ArrayAdapter<AppInfo>(this, R.layout.row_grid_items, apps) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        ViewHolderItem viewHolder = null;

                        if (convertView == null) {
                            convertView = getLayoutInflater().inflate(
                                    R.layout.row_grid_items, parent, false
                            );
                            viewHolder = new ViewHolderItem();
                            viewHolder.icon = (ImageView) convertView.findViewById(R.id.img_icon);
                            viewHolder.name = (TextView) convertView.findViewById(R.id.txt_name);
                            viewHolder.label = (TextView) convertView.findViewById(R.id.txt_label);

                            convertView.setTag(viewHolder);
                        } else {
                            viewHolder = (ViewHolderItem) convertView.getTag();
                        }

                        AppInfo appInfo = apps.get(position);

                        if (appInfo != null) {
                            viewHolder.icon.setImageDrawable(appInfo.icon);
                            viewHolder.label.setText(appInfo.label);
                            viewHolder.name.setText(appInfo.name);
                        }
                        return convertView;

                    }

                    final class ViewHolderItem {
                        ImageView icon;
                        TextView label;
                        TextView name;
                    }
                };
            }

            grdView.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(GetApps.this, ex.getMessage().toString() + " loadListView", Toast.LENGTH_LONG).show();
            Log.e("Error loadListView", ex.getMessage().toString() + " loadListView");
        }

    }

    private void loadApps() {
        try {
            packageManager = getPackageManager();
            if (apps == null) {
                apps = new ArrayList<AppInfo>();
                Intent i = new Intent(Intent.ACTION_MAIN, null);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> availableApps = packageManager.queryIntentActivities(i, 0);
                for (ResolveInfo ri : availableApps) {
                    AppInfo appinfo = new AppInfo();
                    appinfo.label = ri.loadLabel(packageManager);
                    appinfo.name = ri.activityInfo.packageName;
                    appinfo.icon = ri.activityInfo.loadIcon(packageManager);
                    apps.add(appinfo);

                    Collections.sort(apps, new Comparator<AppInfo>() {
                        @Override
                        public int compare(AppInfo appInfo, AppInfo t1) {
              /*
                            char l = Character.toUpperCase(appInfo.label.charAt(0));

                            if (l < 'A' || l > 'Z')

                                l += 'Z';

                            char r = Character.toUpperCase(t1.label.charAt(0));

                            if (r < 'A' || r > 'Z')

                                r += 'Z';*/

                            String s1 = /*l +*/ appInfo.label.toString();

                            String s2 = /*r +*/ t1.label.toString();

                            return s1.compareTo(s2);

                        }
                    });
                }
            }

        } catch (Exception ex) {
            Toast.makeText(GetApps.this, ex.getMessage().toString() + " loadApps", Toast.LENGTH_LONG).show();
            Log.e("Error loadApps", ex.getMessage().toString() + " loadApps");
        }

    }
}