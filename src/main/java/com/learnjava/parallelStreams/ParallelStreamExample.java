package com.learnjava.parallelStreams;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;
import static com.learnjava.util.CommonUtil.stopWatch;


public class ParallelStreamExample {
    private static List<String> stringTransform(List<String> namesList) {
        stopWatch.start();
        List<String> res = namesList.
                // stream().
                parallelStream().
                map(ParallelStreamExample::addNameLengthStringTransform)
                .collect(Collectors.toList());
        stopWatch.stop();
        log("Total Tie taken in stream: " + stopWatch.getTime());
        return res;
    }
    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();

        List<String> streamRes = stringTransform(namesList);
    }

    public static String addNameLengthStringTransform(String str){
        delay(500);
        return (str.length() + " - " + str);
    }
}