package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;

import static com.learnjava.util.CommonUtil.stopWatch;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {
    List<String> inputList;

    // Create a Constructor since we are extending the class RecursiveTask
    // so we can create variable of that
    public ForkJoinUsingRecursion(List<String> list){
        this.inputList = list;
    }

    public static void main(String[] args) {
        stopWatch.start();
        List<String> names = DataSet.namesList();
        // Create fork join pool and fork join recursion
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
        List<String> outputList = forkJoinPool.invoke(forkJoinUsingRecursion); // Submit the task in Work Queue and thread will pick up the task

        stopWatch.stop();
        log("Final result is: " + outputList);
        log("total time taken is: " + stopWatch.getTime());
    }

    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }

    @Override
    protected List<String> compute() {

        if(inputList.size()<=1){
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name-> resultList.add(addNameLengthTransform(name)));
            return resultList;
        }
        int midpoint = inputList.size()/2;
        ForkJoinTask<List<String>> leftInputList=  new ForkJoinUsingRecursion(inputList.subList(0,midpoint)).fork();
        inputList = inputList.subList(midpoint, inputList.size());
        List<String> rightResult = compute(); //recursion happens
        // join operation that we do
        List<String> leftResult = leftInputList.join();
        leftResult.addAll(rightResult);
        return leftResult;
    }
}
