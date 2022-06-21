package com.drhowdydoo.meminfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.Button;

import com.drhowdydoo.meminfo.adapter.RecyclerViewAdapter;
import com.drhowdydoo.meminfo.databinding.AboutDialogLayoutBinding;
import com.drhowdydoo.meminfo.databinding.ActivityMainBinding;
import com.drhowdydoo.meminfo.model.DisplayHeader;
import com.drhowdydoo.meminfo.model.MemInfo;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;


@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST";
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Object> list;
    private LinkedHashMap<String,Long> map;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        if (DynamicColors.isDynamicColorAvailable()) DynamicColors.applyToActivityIfAvailable(this);

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int progressBackgroundColor = MaterialColors.getColor(this, com.google.android.material.R.attr.colorBackgroundFloating,Color.WHITE);
        int progressIndicatorColor = MaterialColors.getColor(this, com.google.android.material.R.attr.colorPrimary,Color.BLACK);

        swipeRefreshLayout = binding.swipeRefreshLayout;
        recyclerView = binding.recyclerview;
        toolbar = binding.toolbar;
        list = new ArrayList<>();

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(progressBackgroundColor);
        swipeRefreshLayout.setColorSchemeColors(progressIndicatorColor);



        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.about){
                AboutDialogLayoutBinding aboutDialogLayoutBinding = AboutDialogLayoutBinding.inflate(getLayoutInflater());

                 new MaterialAlertDialogBuilder(this).setView(aboutDialogLayoutBinding.getRoot()).show();

                Button btn = aboutDialogLayoutBinding.btnKnowMore;

                btn.setOnClickListener(view -> {
                    Uri uri =   Uri.parse("https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/6/html/deployment_guide/s2-proc-meminfo");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                });
            }
            return true;
        });

        map = new LinkedHashMap<>();

        readProc();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            map.clear();
            list.clear();
            readProc();
            recyclerViewAdapter.updateList(list);
            swipeRefreshLayout.setRefreshing(false);
        });


        recyclerViewAdapter = new RecyclerViewAdapter(list,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
        new FastScrollerBuilder(recyclerView).useMd2Style().build();

    }

    private String format(Long sizeBytes){
        return Formatter.formatFileSize(this,sizeBytes);
    }

    private void readProc(){
        try {
            String line;
            long totalMem = 0L,availMem = 0L,usedMem ;
            RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
            while ((line = reader.readLine()) != null) {
                String[] entry = line.split(":");
                map.put(entry[0],Long.parseLong(entry[1].substring(0, entry[1].length() - 2).trim()) * 1000);
            }

            if (map.containsKey("MemTotal")) totalMem = map.get("MemTotal") ;
            if (map.containsKey("MemAvailable")) availMem = map.get("MemAvailable") ;
            usedMem = totalMem - availMem;
            int memTrack = totalMem != 0 ? (int) (((double) usedMem / totalMem) * 100) : 0;

            DisplayHeader displayHeader = new DisplayHeader(format(totalMem) ,format(usedMem) + " used",format(availMem) + " free",memTrack);
            list.add(displayHeader);

            map.forEach((k,v) -> {
                if(k.equalsIgnoreCase("Buffers") || k.equalsIgnoreCase("Active")
                        || k.equalsIgnoreCase("Unevictable") || k.equalsIgnoreCase("SwapTotal") || k.equalsIgnoreCase("Dirty")
                        || k.equalsIgnoreCase("Shmem") || k.equalsIgnoreCase("KernelStack") ||
                        k.equalsIgnoreCase("VmallocTotal") || k.equalsIgnoreCase("GPUTotalUsed")) {

                    MemInfo memInfo = new MemInfo("","", false);
                    list.add(memInfo);
                }
                MemInfo memInfo = new MemInfo(k,format(v), true);
                list.add(memInfo);
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}