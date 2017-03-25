package com.ccj.smartsea.utils;

import android.support.annotation.NonNull;

import com.ccj.smartsea.bean.FishTank;
import com.ccj.smartsea.bean.OutEnvironment;
import com.ccj.smartsea.bean.SwitchBtn;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by chenchangjun on 17/3/24.
 */

public class HexTo10Utils {


    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayList<FishTank> fishTanks = new ArrayList<>();
    public static OutEnvironment inEnvironment;
    public static OutEnvironment outEnvironment;
    public static SwitchBtn electSwitchBtn;
    public static SwitchBtn lightSwitchBtn;
    public static SwitchBtn filterSwitchBtn;
    public static SwitchBtn foodSwitchBtn;

    public static int HexTo10(@NonNull String h) {
        BigInteger srch = new BigInteger(h, 16);

        return srch.intValue();
    }


    public static void getData(String sdata) {

        String strInput = "0X3A153221000000000000000000000000000000015601341821110000000000000000010123";
        arrayList = new ArrayList<>();
        fishTanks = new ArrayList<>();


        for (int i = 4; i < strInput.length(); i++) {
            if (i + 2 >= strInput.length() - 1) {
                break;
            }
            String str = strInput.substring(i, i + 2);
            arrayList.add(str);
            System.out.println(str);
            i++;

        }
//String strInput = "0X3A153221000000000000000000000000000000015601341821110000000000000000010123";

        for (int i = 0; i < 18; i++) {
            FishTank fishTank = new FishTank();
            fishTank.id = i + "";
            fishTank.temp = HexTo10(arrayList.get(i))+"";
            fishTank.depth = HexTo10(arrayList.get(i + 1))+"";
            fishTank.turbidness =HexTo10( arrayList.get(i + 2))/10+"";
            fishTanks.add(fishTank);
            i += 2;
            System.out.println(fishTank.id + "---" + fishTank.temp + "--" + fishTank.depth + "--" + fishTank.turbidness);


        }


        inEnvironment = new OutEnvironment();
        inEnvironment.id = 1 + "";
        inEnvironment.pm25 = HexTo10(arrayList.get(18))*256 + HexTo10(arrayList.get(19))/10+"";
        inEnvironment.pm10 = HexTo10(arrayList.get(20))*256 + HexTo10(arrayList.get(21))/10+"";
        inEnvironment.tempIn =  HexTo10(arrayList.get(22))+"";

        inEnvironment.temp =  HexTo10(arrayList.get(23))+"";
        inEnvironment.smoke =  HexTo10(arrayList.get(24))+"";
        System.out.println(inEnvironment.pm25 + "---" + inEnvironment.pm10 + "--" + inEnvironment.temp + "--" + inEnvironment.smoke);

        outEnvironment = new OutEnvironment();
        outEnvironment.id = 1 + "";
        inEnvironment.pm25 = HexTo10(arrayList.get(25))*256 + HexTo10(arrayList.get(26))/10+"";
        inEnvironment.pm10 = HexTo10(arrayList.get(27))*256 + HexTo10(arrayList.get(28))/10+"";


        outEnvironment.temp = HexTo10(arrayList.get(29))+"";
        outEnvironment.smoke = HexTo10(arrayList.get(30))+"";
        System.out.println(outEnvironment.pm25 + "---" + outEnvironment.pm10 + "--" + outEnvironment.temp + "--" + outEnvironment.smoke);



        electSwitchBtn = new SwitchBtn();
        electSwitchBtn.id = 1 + "";
        electSwitchBtn.state = getState(arrayList.get(31));
        System.out.println(electSwitchBtn.id + "---" + electSwitchBtn.state);


        lightSwitchBtn = new SwitchBtn();
        lightSwitchBtn.id = 2 + "";
        lightSwitchBtn.state = getState(arrayList.get(32));
        System.out.println(lightSwitchBtn.id + "---" + lightSwitchBtn.state);

        filterSwitchBtn = new SwitchBtn();
        filterSwitchBtn.id = 3 + "";
        filterSwitchBtn.state = getState(arrayList.get(33));
        System.out.println(filterSwitchBtn.id + "---" + filterSwitchBtn.state);

        foodSwitchBtn = new SwitchBtn();
        foodSwitchBtn.id = 4 + "";
        foodSwitchBtn.state = getState(arrayList.get(34));
        System.out.println(foodSwitchBtn.id + "---" + foodSwitchBtn.state);


    }

    public static Boolean getState(String state){

        if (state.equals("00")){
            return false;
        }else {
            return true;

        }
    }





}