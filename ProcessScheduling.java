/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.termproject;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.PrintWriter;
/**
 *
 * @author zachd
 */

public class ProcessScheduling  {
    //intitializes maximum wait time for Q
    static int maxWaitTime() {
        return 30;
    }   
    
    // method to read items from a text file to ArrayList D
    // remove element from D and put in PriorityQueue Q
    public static void main(String[] args) throws IOException {
         // creates arraylist D to grab data from text file
        ArrayList<Process> D = new ArrayList(10);
            // create scanner to read text file
            Scanner scan = new Scanner(new File("process_scheduling_input.txt"));
            int id;
            int priority;
            int duration;
            int arrivalTime;
            int i = 0;
        
            // reads text file, delimiter is space, all items in file are ints    
            while (scan.hasNext()){
                String lineInput = scan.nextLine();  
                String[] splitLines = lineInput.split(" ");
                id = Integer.parseInt(splitLines[0]);
                priority = Integer.parseInt(splitLines[1]);
                duration = Integer.parseInt(splitLines[2]);
                arrivalTime = Integer.parseInt(splitLines[3]);
                // creates a new process p and adds to D for each line in text file (10 total)
                Process p = new Process(id, priority, duration, arrivalTime);
                D.add(p);
                i++;
            }scan.close();
            
        // initialize iterator for D, and print all Processes in D
        Iterator<Process> itr = D.iterator();
        while (itr.hasNext()) {
            // create value for next iterator in loop, so itr does not change
            Process p = itr.next();
            System.out.println(p);
        }
        // create Priority Queue
        PriorityQueue<Process> Q = new PriorityQueue();
        // prints max wait time
        System.out.println("\n" + 
                           "Maximum wait time = " + maxWaitTime());
    
        // initialize currentTime, running, total wait time, and wait time before process starts
        int currentTime = 0;
        boolean running = false;
        double totalWait = 0;
        Process p;
        itr = D.iterator();
        // reset iterator
        // initialize large process loop
        while(itr.hasNext() == true){
            // removes items from D when their arrival time matches currentTime, until something is put into Q
            if (itr.next().getArrivalTime() <= currentTime){
                // re-set iterator, since next was used in above if
                itr = D.iterator();
                // create new static itr.next variable
                Process nxt = itr.next();
                Q.add(nxt);
                D.remove(nxt);
                // re-set D iterator after remove
                itr = D.iterator();
                
            }
            // if Q is not empty and nothing is running, turns running to true and starts "running time true" nested loop
            else if (Q.isEmpty() == false && running == false){
                running = true;
                // assign p to Process removed with lowest priority
                p = Q.remove();
                // time-stamps when p was removed
                int removeTime = currentTime;
                currentTime++;
                // begin running time true nested loop
                while (running == true){
                    // runs following if time passed since removal = p process time
                    if (currentTime - p.getDuration() == removeTime) {
                        int waitTime;
                        waitTime = removeTime - p.getArrivalTime(); // calculates wait time
                        totalWait += waitTime; // increases total wait time by wait time
                        System.out.println("\n" +
                                           "Process removed from queue is: id = " + p.getId() + ", at time " +
                                            removeTime + ", wait time = " + waitTime + " Total wait time = " + totalWait);
                        System.out.println("Process id = " + p.getId() + "\n"
                                        + "       Priority = " + p.getPriority() + "\n"
                                        + "       Arrival = " + p.getArrivalTime() + "\n"
                                        + "       Duration = " + p.getDuration());
                        System.out.println("Process " + p.getId()+ " finished at time " + currentTime);
                        // calls updatePriority method
                        updatePriority(Q, currentTime);
                        // turns running to false - process finished
                        running = false;
                        
                        
                    // continues to move items from D while process is running, while items are left in D
                    }else if (D.isEmpty() == false){
                        // only moves items from D if currentTime >= item's arrival time
                        // re set iterator safety net
                        itr = D.iterator();
                        if (itr.next().getArrivalTime() <= currentTime){
                            // re-set iterator and nxt
                            itr = D.iterator();
                            Process nxt = itr.next();
                            // add D element to Q and remove from D
                            Q.add(D.get(0));
                            D.remove(D.get(0));
                            itr = D.iterator();
                            currentTime++;
                        }else{
                            currentTime++; // increases current time and re-starts nested loop
                        }    
                    }
                    else {
                        currentTime++; // increases current time and re-starts nested loop
                    }   
                }
            }
            // if nothing to be removed from D, but D is populated, and Q is empty, passes 1 time unit and reset iterator
            else{
                itr = D.iterator();
                currentTime++;
            }
        }
        // small loop to finish emptying Q and D while Q is full
        itr = Q.iterator();
        while (!Q.isEmpty()){
            // checks for empty D
            if (!D.isEmpty()){
                if (D.get(0).getArrivalTime() <= currentTime){
                    Q.add(D.get(0));
                    D.remove(D.get(0));
                    if (D.isEmpty()) {
                        System.out.println("\n" + 
                                            "D becomes empty at time " + currentTime);
                    }currentTime++;    
                }
            }
            if (running == false){
                running = true;
                // assign p to Process removed with lowest priority
                p = Q.remove();
                // time-stamps when p was removed
                int removeTime = currentTime;
                currentTime++;
                // begin running time true nested loop
                while (running == true){
                    // runs following if (time passed since removal = p process time)
                    if (currentTime - p.getDuration() == removeTime) {
                        int waitTime;
                        waitTime = removeTime - p.getArrivalTime(); // calculates wait time
                        totalWait += waitTime; // increases total wait time by wait time
                        System.out.println("\n" +
                                           "Process removed from queue is: id = " + p.getId() + ", at time "
                                          + removeTime + ", wait time = " + waitTime + " Total wait time = " + totalWait);
                        System.out.println("Process id = " + p.getId() + "\n"
                                        + "       Priority = " + p.getPriority() + "\n"
                                        + "       Arrival = " + p.getArrivalTime() + "\n"
                                        + "       Duration = " + p.getDuration());
                        System.out.println("Process " + p.getId()+ " finished at time " + currentTime);
                        // calls updatePriority method
                        updatePriority(Q, currentTime);
                        // turns running to false - process finished
                        running = false;
                    }
                    // check if D is empty
                    else if (D.isEmpty() == false){
                        // only moves items from D if currentTime >= item's arrival time
                        if (D.get(0).getArrivalTime() <= currentTime){
                            // add D element to Q and remove from D
                            Q.add(D.get(0));
                            D.remove(D.get(0));
                            if (D.isEmpty()) {
                                System.out.println("\n" + 
                                                   "D becomes empty at time " + currentTime);
                            }
                        }currentTime++;
                    }    
                    // if process still running, increase time by 1 and restart nested loop
                    else { 
                        currentTime++;
                    }
                }
            }
        }
        System.out.println("\n" +
                           "Total wait time = " + totalWait);
        System.out.println("Average wait time = " + totalWait / 10);
    }
    
    // method to update priorities in Q that have been waiting longer than max
    // input - priority queue and current time
    public static void updatePriority(PriorityQueue<Process> p, int currentTime) {
        Iterator<Process> it = p.iterator();
        System.out.println("\n" + 
                           "Update priority: ");
        while (it.hasNext()) {
            Process n = it.next();
            // stamps old priority before change is made for print statement
            int oldPrio = n.getPriority();
            // calculates how long process has been waiting
            int waitTime = currentTime - n.getArrivalTime();
            if (waitTime >= maxWaitTime() && n.getPriority() > 1){ 
                // decreases priority by 1 for every process waiting more than max, that isn't already 1
                n.setPriority(n.getPriority() - 1);
                System.out.println("PID = " + n.getId() + ", wait time = " + waitTime + ", current priority = " + oldPrio);
                System.out.println("PID = " + n.getId() + ", new priority = " + n.getPriority());
            }
        }
    }
}

