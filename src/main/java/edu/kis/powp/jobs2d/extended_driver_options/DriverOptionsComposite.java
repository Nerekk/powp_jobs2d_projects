package edu.kis.powp.jobs2d.extended_driver_options;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;

import java.util.ArrayList;

public class DriverOptionsComposite {

    private final ArrayList<DriverOption> driverOptionList;
    private static Job2dDriver usingDriver;

    private static DriverOptionsComposite instance;


    private DriverOptionsComposite() {
        this.driverOptionList = new ArrayList<>();
    }


    public static DriverOptionsComposite getInstance() {
        if (instance != null)
            return instance;

        synchronized (DriverOptionsComposite.class) {
            if (instance == null)
                instance = new DriverOptionsComposite();

            return instance;
        }
    }


    public void setUsingDriver(Job2dDriver driver) {
        usingDriver = driver;
        refresh();
    }


    public void addOption(DriverOption driverOption) {
        driverOptionList.add(driverOption);
        refresh();
    }


    public void removeOption(DriverOption driverOption) {
        driverOptionList.remove(driverOption);
        refresh();
    }


    private void printOptionList() {
        System.out.println("\nOPTIONS:");
        driverOptionList.forEach(System.out::println);
    }


    private void refresh() {
        DriverFeature.getDriverManager().setCurrentDriver(usingDriver);

        if (driverOptionList.isEmpty())
            return;

        Job2dDriver compositeDriver = DriverFeature.getDriverManager().getCurrentDriver();

        for (DriverOption driverOption : driverOptionList) {
            driverOption.setDriver(compositeDriver);
            compositeDriver = driverOption;
        }

        DriverFeature.getDriverManager().setCurrentDriver(compositeDriver);
    }

}