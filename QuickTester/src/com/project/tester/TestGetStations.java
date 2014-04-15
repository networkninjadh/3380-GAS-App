package com.project.tester;

import java.util.ArrayList;
import com.project.classes.*;

public class TestGetStations {
    public static void Start()
    {
        ArrayList<GasStation> stations = StationLocator.NearbyGasStations(30.0,-91.0,10.0, "reg");
        //System.out.println(stations);
        for (GasStation s : stations)
            System.out.println(s.toString());
    }
}
