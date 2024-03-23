package ControlWork;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class Main {
    static Map<String, List<Program>> mapChannel = new HashMap<>();
    static List<Program> allProgrammes = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        List<String> list = Files.readAllLines(new File("C:/Users/Ралина/IdeaProjects/Infor/src/main/java/ControlWork/schedule.txt").toPath(), Charset.defaultCharset());
        //Заполнение Map
        int count = 0;
        String curChannel = "";
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).charAt(0) == '#'){
                count++;
            }
        }
        int k = 0;
        for (int i = 0; i < count; i++){
            curChannel = list.get(k).substring(1);
            k++;
            List<Program> pr = new ArrayList<>();
            while (k < list.size() && list.get(k).charAt(0) != '#'){
                Program p = new Program(curChannel, new BroadcastsTime(list.get(k)), list.get(k+1));
                pr.add(p);
                k+= 2;
            }
            mapChannel.put(curChannel, pr);
        }

        //Заполнение Lists
        for (List<Program> programs : mapChannel.values()) {
            allProgrammes.addAll(programs);
        }

        //Сортировка и вывод всех программ
        printAllProgrammes();


    }
    //методы
    public static void printAllProgrammes(){
        Collections.sort(allProgrammes, (p1, p2) -> p1.time.compareTo(p2.time));
        for (Program i: allProgrammes){
            System.out.println(i.name + " " + i.time.getHour() + ":" + i.time.getMinutes());
        }
    }
    public static void allProgrammesNow(String time){
        BroadcastsTime currentTime = new BroadcastsTime(time);
        for (String i: mapChannel.keySet()){
            for (int j = 0; j < mapChannel.get(i).size() - 1; j++){
                if (currentTime.between(mapChannel.get(i).get(j).time, mapChannel.get(i).get(j + 1).time)){
                    System.out.println(mapChannel.get(i).get(j) + "-" + mapChannel.get(i).get(j + 1).time);
                }
            }
        }
    }

    public static void channelProgrammeNow(String channel, String time){
        System.out.println("Программа на канале " + channel + " сейчас");
        BroadcastsTime currentTime = new BroadcastsTime(time);
        for (int j = 0; j < mapChannel.get(channel).size() - 1; j++){
            if (currentTime.between(mapChannel.get(channel).get(j).time, mapChannel.get(channel).get(j + 1).time)){
                System.out.println(mapChannel.get(channel).get(j) + "-" + mapChannel.get(channel).get(j + 1).time);
            }
        }
    }

    public static void search(String s){
        System.out.println("Программы, которые содержат \"" + s +"\" в названии");
        for (Program i: allProgrammes){
            if (i.name.contains(s)){
                System.out.println(i);
            }
        }
    }






}