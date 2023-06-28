/*
Dean Loeafoe
Project 2

Note: The progression of the states of my 5 programs is as follows:
0 -> 1 -> 2 -> 3 -> 0

I did it this way because it made the math easier for me.
I still have only 4 states as per the directions.

You took off points last time because there was too much repeated code.
This is my revised version with less bloat.
*/

import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math; 
import java.io.FileWriter;   
import java.io.IOException;  

class Event
{
  int program_number;
  double transition_time;
  int state;

  //Parameterized constructor
  Event(int a, double b, int c) 
  {
    program_number = a;
    transition_time = b;
    state = c;
  }//Event
}//class Event

class Event_order 
{
  public static void main(String[] args) 
  {
    double u = 0.0; //Random number between 0 and 1
    double time = 0.0; //Used to determine the next transition time of the program
    ArrayList<Event> ordered_event_list = new ArrayList<Event>(); //Array List to hold all event objects in order
    ArrayList<int[]> storedValues = new ArrayList<int[]>(); //Will be used to save arrays with state values

    //Create an event object for each program
    for(int i = 0; i < 5; i++)
    {
      u = Math.random();
      time = ((-1) * (Math.log(1 - u)))/.1; //Caluculate the duration of time at which the next state transition will occur
      
      //Create event object
      Event eventObject = new Event((i + 1), time, 0);

      //Add event Object to list
      ordered_event_list.add(eventObject);
      
      //Arrange newly added event by transition time
      for(int x = (ordered_event_list.size() - 1); x > 0; x--)
      {
        if(ordered_event_list.get(x).transition_time > ordered_event_list.get(x - 1).transition_time)
        { break; } //End the loop early if the events are ordered
      
        //Swap obejects if not ordered by transition_time
        if(ordered_event_list.get(x).transition_time < ordered_event_list.get(x - 1).transition_time)
        {
          Collections.swap(ordered_event_list, x, (x - 1));
        }//if(ordered_event_list.get(x).transition_time < ordered_event_list.get(x - 1).transition_time)

      time = 0.0; //Reset time so it can be reused
      eventObject = null; ////Reset object pointer so it can be reused
      }//for(int x = (ordered_event_list.size() - 1); x > 0; x--) */
    }//for(int i = 0; i < 5; i++)

    try 
    {
      //Print results to output file
      FileWriter scribe = new FileWriter("output.txt");

      scribe.write("Dean Loeafoe\nProject 2\n\n");
      scribe.write("Note: The progression of the states of my 5 programs is as follows:\n");
      scribe.write("0 -> 1 -> 2 -> 3 -> 0\n\n");
      scribe.write("I did it this way because the math was easier for me.\n");
      scribe.write("I still have only 4 states as per the directions.\n");
      scribe.write("Below are the first 20 transitions in order.\n");
      scribe.write("Under that is the stage of each program after every 500 state changes.\n\n");
      scribe.write("Transitions Completed: 0\n");

      //Print the fields of the event objects on the list
      for(int i = 0; i < (ordered_event_list.size()); i++)
      {
        scribe.write("Program: " + ordered_event_list.get(i).program_number + " | State: " + ordered_event_list.get(i).state + " | Transition Time: " + ordered_event_list.get(i).transition_time + "\n");
      }//for(int i = 0; i < ordered_event_list.size(); i++)

      scribe.write("\n"); //Extra line for spacing and organization

      for(int i = 0; i < 10000; i++)
      {
        u = Math.random(); //Get random number
        time = ((-1) * (Math.log(1 - u)))/.1; //Caluculate the duration of time at which the next state transition will occur
      
        //Create new event object based on the fields of the first event object on the list
        Event eventObject = new Event(ordered_event_list.get(0).program_number, (time + ordered_event_list.get(0).transition_time), (ordered_event_list.get(0).state + 1) % 4);

        ordered_event_list.add(eventObject); //Add the new event object to the list
        ordered_event_list.remove(0); //Pop first event on the list

        //Arrange newly added event by transition time
        for(int x = (ordered_event_list.size() - 1); x > 0; x--)
        {
          if(ordered_event_list.get(x).transition_time > ordered_event_list.get(x - 1).transition_time)
          { break; } //End the loop early if the events are ordered
      
          //Swap obejects if not ordered by transition_time
          if(ordered_event_list.get(x).transition_time < ordered_event_list.get(x - 1).transition_time)
          {
            Collections.swap(ordered_event_list, x, (x - 1));
          }//if(ordered_event_list.get(x).transition_time < ordered_event_list.get(x - 1).transition_time)
        }//for(int x = (ordered_event_list.size() - 1); x > 0; x--) */

        time = 0.0; //Reset time so it can be reused
        eventObject = null; ////Reset object pointer so it can be reused

        //Print the status of the 5 programs during the first 20 iterations
        if(i < 20)
        {
          scribe.write("Transitions Completed: " + (i + 1) + "\n");

          for(int j = 0; j < ordered_event_list.size(); j++)
          {
            scribe.write("Program: " + ordered_event_list.get(j).program_number + " | State: " + ordered_event_list.get(j).state + " | Transition Time: " + ordered_event_list.get(j).transition_time + "\n");
          }//for(int j = 0; j < ordered_event_list.size(); j++)

          scribe.write("\n"); //Extra line for spacing and organization
        }//if(i < 20)

        //Save each program's state during every 500 iterations.
        //Will be printed later
        if((i + 1) % 500 == 0)
        {
          int[] values = new int[5]; //Will be used to save state values;

          for(int x = 0; x < ordered_event_list.size(); x++)
          {
            values[(ordered_event_list.get(x).program_number) - 1] = ordered_event_list.get(x).state;
          }

          storedValues.add(values); //Add array of values to ArrayList
          values = null; //Delete array so it can be reused
        }//if((i + 1) % 500 == 0)
      }//for(int i = 0; i < 10000; i++)

      //Print the states
      for(int i = 0; i < storedValues.size(); i++)
      {
        scribe.write("Program States after " + ((i + 1) * 500) + " state changes:\n");
        for(int j = 0; j < 5; j++)
        {
          scribe.write("Program: " + (j + 1) + " | State: " + storedValues.get(i)[j] + "\n");
        }
        scribe.write("\n");
      }//for(int i = 0; i < storedValues.size(); i++)
      
      scribe.close();//Close text file
    }//try 
      
    catch (IOException e) 
    {
    System.out.println("An error occurred. File not created. Check for errors!");
    e.printStackTrace();
    }//catch
  }//public static void main(String[] args) 
}//loeafoe_project2
