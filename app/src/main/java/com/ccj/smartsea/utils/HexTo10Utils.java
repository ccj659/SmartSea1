package com.ccj.smartsea.utils;

import android.support.annotation.NonNull;

import com.ccj.smartsea.bean.FishTank;
import com.ccj.smartsea.bean.OutEnvironment;
import com.ccj.smartsea.bean.SwitchBtn;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by chenchangjun on 17/3/24.
 */

public class HexTo10Utils {


    public static ArrayList<String> arrayList;
    public static ArrayList<FishTank> fishTanks ;
    public static OutEnvironment inEnvironment;
    public static OutEnvironment outEnvironment;
    public static SwitchBtn electSwitchBtn;
    public static SwitchBtn lightSwitchBtn;
    public static SwitchBtn filterSwitchBtn;
    public static SwitchBtn foodSwitchBtn;

    public static int HexTo10(@NonNull String h) {
       // BigInteger srch = new BigInteger(h, 16);

       // return srch.intValue();
        return Integer.parseInt(h);
    }

    public static Double intToDouble(@NonNull int h) {
        double turbidness=h/10;
        new java.text.DecimalFormat("#.00").format(turbidness);
        return turbidness;
    }
    public static void getData(ArrayList<String> strInput) {
       // 50 33 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 86 1 52 24 33 17 0 0 0 0 0 0 0 0 1 1 35
       // String strInput = "1X3A153221111111111111111111111111111111115601341821111111111111111111010123";
        arrayList = strInput;
        fishTanks = new ArrayList<>();


//String strInput = "0X3A153221000000000000000000000000000000015601341821110000000000000000010123";
        System.out.println( "arrayList---" +arrayList.toString());

        int index=1;
        for (int i = 0; i < 18; i++) {
            FishTank fishTank = new FishTank();
            fishTank.id = index + "号鱼缸";
            fishTank.temp = HexTo10(arrayList.get(i))+"℃";
            fishTank.depth = HexTo10(arrayList.get(i + 1))+"cm";
            fishTank.turbidness =intToDouble(HexTo10( arrayList.get(i + 2)))+"";
            fishTanks.add(fishTank);
            i += 2;
            index++;
            System.out.println(fishTank.id + "---" + fishTank.temp + "--" + fishTank.depth + "--" + fishTank.turbidness);


        }


        inEnvironment = new OutEnvironment();
        inEnvironment.id = 1 + "";
        inEnvironment.pm25 = (HexTo10(arrayList.get(18))*256 + HexTo10(arrayList.get(19))/10)/10+"ug/m³";
        inEnvironment.pm10 = (HexTo10(arrayList.get(20))*256 + HexTo10(arrayList.get(21))/10)/10+"ug/m³";
        inEnvironment.temp =  HexTo10(arrayList.get(22))+"℃";
        inEnvironment.tempIn =  HexTo10(arrayList.get(23))+"%RH";
        inEnvironment.smoke =  HexTo10(arrayList.get(24))+"";

        System.out.println(inEnvironment.pm25 + "---" + inEnvironment.pm10 + "--" + inEnvironment.temp + "--" + inEnvironment.tempIn);

        outEnvironment = new OutEnvironment();
        outEnvironment.id = 1 + "";

        outEnvironment.pm25 = (HexTo10(arrayList.get(25))*256 + HexTo10(arrayList.get(26))/10)/10+"ug/m³";
        outEnvironment.pm10 = (HexTo10(arrayList.get(27))*256 + HexTo10(arrayList.get(28))/10)/10+"ug/m³";


        outEnvironment.temp = HexTo10(arrayList.get(29))+"℃";
        outEnvironment.tempIn = HexTo10(arrayList.get(30))+"%RH";
        System.out.println(outEnvironment.pm25 + "---" + outEnvironment.pm10 + "--" + outEnvironment.temp + "--" + outEnvironment.tempIn);



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
        System.out.println(  "state---" + state);

        if (state.equals("0")){
            return false;
        }else if (state.equals("1")) {
            return true;

        }else {
            return false;
        }

    }





}
