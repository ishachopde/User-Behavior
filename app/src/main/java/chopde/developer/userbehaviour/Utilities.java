package chopde.developer.userbehaviour;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by id on 4/9/2018.
 */

public class Utilities {
    /*
    *****Workplaces****
    Accounting 1
    admin area 1001,1002,1003
    airport 2
    bank 8
    govt office 57
    museum 66
    post-office 77
    ****Places of attractions*****
    amusement park 3
    aquarium 4
    art gallery 5
    museum 66
    zoo 96
    *****Self****
    Beauty salon 10
    hair care 45
    spa 85
    *****Food******
    bakery 7
    food 38
    meal delivery 60
    takeaway 61
    restaurant 79
    *****social leisure***
    bar 9
    bowling alley 13
    cafe 15
    casino 21
    movie 64
    night clun 67
    ****stores****
    bicycle store 11
    book store 13
    clothing 25
    convenience 26
    departmental 29
    electronic 32
    furniture 40
    jewelry 52
    ***Spiritual****
    cemetery 22
    church 23
    funeral home 39
    mosque 62
    ****Health*****
    dentist 28
    doctor 30
    health 47
    hospital 50
    ***fitness**
    gym 44
    stadium 86
    ****Study****
    library 55
    school 82
    university 94
     */
    public static final int[] ids = {1,1001,1002,1003,2,8,57,77,66,3,4,5,66,96,10,45,85,7,38,60,61,79
                                     ,9,13,25,26,29,32,40,52,15,21,64,67,11,13,22,23,39,62
                                     ,28,30,47,50,44,86,55,82,94};

    public static List<Integer> sortedids = new LinkedList<>();
    public static int currentAcitivity = 0;
    public static Map<Integer,String> dayToInt = new HashMap<>();
    public static Calendar calender = Calendar.getInstance();
    static{
        for(int x : ids)
        {
            sortedids.add(x);
        }
        dayToInt.put(1,"Walking");
        dayToInt.put(2,"Still");
        dayToInt.put(3,"Running");
        dayToInt.put(4,"Cycling");
        dayToInt.put(5,"Driving");
    }
}
