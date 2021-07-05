/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.termproject;

/**
 *
 * @author zachd
 */
// set up class Process
public class Process implements Comparable<Process> {
    private int id;
    private int priority;
    private int duration;
    private int arrivalTime;
    
    public Process() {
    }
    
    // return methods for Person class
    public int getId() { return id; }
    public int getPriority() { return priority; }
    public int getDuration() { return duration; }
    public int getArrivalTime() { return arrivalTime; }
    
    
    // method to decrease priority when needed
    public void setPriority(int priority){
        this.priority = priority;
    }
    
    // method to define Process class parameters
    public Process(int id, int priority, int duration, int arrivalTime) {
        this.id = id;
        this.priority = priority;
        this.duration = duration;
        this.arrivalTime = arrivalTime;
    } 
   
   // prints array with each value identified
   public String toString() {
	   String D =
                "\tID = " + id +
                "\tPriority = " + priority +
                "\tDuration = " + duration +
                "\tArrival Time = " + arrivalTime;
	   return D;
   }
   
   // custom comparator for ids, necessary when removing items from Q
   @Override
   public int compareTo (Process p) {
       // if current object is greater, return 1
       if (this.priority > p.priority) {
           return 1;
       }else if (this.priority < p.priority) {
           return -1;
       }else { return 0; }
   }    
}
