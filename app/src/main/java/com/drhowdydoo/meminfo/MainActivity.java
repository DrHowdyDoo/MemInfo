package com.drhowdydoo.meminfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;

import com.drhowdydoo.meminfo.adapter.RecyclerViewAdapter;
import com.drhowdydoo.meminfo.databinding.ActivityMainBinding;
import com.drhowdydoo.meminfo.model.DisplayHeader;
import com.drhowdydoo.meminfo.model.MemInfo;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST";
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recyclerview;
        list = new ArrayList<>();

        String line;
        RandomAccessFile reader;
        LinkedHashMap<String,Long> map = new LinkedHashMap<>();

        try {
            long totalMem = 0L,availMem = 0L,usedMem ;
            reader = new RandomAccessFile("/proc/meminfo", "r");
            while ((line = reader.readLine()) != null) {
                String[] entry = line.split(":");
                map.put(entry[0],Long.parseLong(entry[1].substring(0, entry[1].length() - 2).trim()) * 1000);
            }

            if (map.containsKey("MemTotal")) totalMem = map.get("MemTotal") ;
            if (map.containsKey("MemAvailable")) availMem = map.get("MemAvailable") ;
            usedMem = totalMem - availMem;
            int memTrack = totalMem != 0 ? (int) (((double) usedMem / totalMem) * 100) : 0;

            DisplayHeader displayHeader = new DisplayHeader(format(totalMem),format(usedMem),format(availMem),memTrack);
            list.add(displayHeader);

            map.forEach((k,v) -> {
                if(k.equalsIgnoreCase("Buffers") || k.equalsIgnoreCase("Active")
                        || k.equalsIgnoreCase("Unevictable") || k.equalsIgnoreCase("SwapTotal") || k.equalsIgnoreCase("Dirty")
                || k.equalsIgnoreCase("Shmem") || k.equalsIgnoreCase("KernelStack") ||
                k.equalsIgnoreCase("VmallocTotal") || k.equalsIgnoreCase("GPUTotalUsed")) {

                    MemInfo memInfo = new MemInfo("","");
                    list.add(memInfo);
                }
                MemInfo memInfo = new MemInfo(k,format(v));
                list.add(memInfo);
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("TEST", "onCreate: " + list.size());


        recyclerViewAdapter = new RecyclerViewAdapter(list,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private String format(Long sizeBytes){
        return Formatter.formatFileSize(this,sizeBytes);
    }


}