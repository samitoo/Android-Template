package com.hemingwaywest.utiliserve.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: utiliserve-reboot
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 4/28/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
public class Queue {

    private String mName;
    private String mStatus;

    public Queue(String name, String status){
        mName = name;
        mStatus = status;
    }


    //Getters
    public String getmStatus() { return mStatus;}
    public String getmName() { return mName;}

    //Setters
    public void setmStatus(String mStatus) { this.mStatus = mStatus;}
    public void setmName(String mName) { this.mName = mName;}

    //Loop through and generate dummy data
    //TODO Replace with a Room DB and live data
    public static ArrayList<Queue> createQueueListDummyData(int numList){
        ArrayList<Queue> queueArrayList = new ArrayList<>();

        for (int i = 0; i < numList; i++) {

            String name;
            String status;

            name = "Form ID: " + i;
            if((i % 2) == 0){
                status = "Sync Successful";
            }
            else{
                status = "Please Sync Again";
            }

            queueArrayList.add(new Queue(name, status));
        }

        return queueArrayList;

    }
}
