
/*
Dean Loeafoe
Final Project - Elevator Simulator

Summary of Transition States of elevators:

Idle -> Travelling/ Pick Up
Travelling -> Travelling/ Pick Up/ Drop Off/ Idle
Drop Off -> Pick Up/ Travelling/ Idle
Pick Up -> Travelling
*/

import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

class Event //Base Event Class
{
  double time;
  int current_floor;
  int destination;

  // Getters
  public double getTime()
  {
    return time;
  }

  public int getFloor()
  {
    return current_floor;
  }

  public int getDestination()
  {
    return destination;
  }
}

class Passenger_Event extends Event
{
  double time; //Time of occurrence
  int destination; //Target floor for this passenger
  int current_floor; //The floor on which this passenger has arrived

  //Parameterized constructor
  Passenger_Event(double y)
  {
    double u = Math.random();
    time = ((-1) * (Math.log(1 - u)))/.1;

    time = time + y;

    double x;

    do
    {
      /*
      Both the current_floor and destination will be assigned an integer
      from 0 to 5 with the following probabilities

      0 - 50%
      1 - 10%
      2 - 10%
      3 - 10%
      4 - 10%
      5 - 10%
      */

      x = Math.random();

      if(x < .1) {this.current_floor = 1;}
      else if((x >= .1) && (x < .2)) {this.current_floor = 2;}
      else if((x >= .2) && (x < .3)) {this.current_floor = 3;}
      else if((x >= .3) && (x < .4)) {this.current_floor = 4;}
      else if((x >= .4) && (x < .5)) {this.current_floor = 5;}
      else if(x >= .5) {this.current_floor = 0;}

      x = Math.random();

      if(x < .1) {this.destination = 1;}
      else if((x >= .1) && (x < .2)) {this.destination = 2;}
      else if((x >= .2) && (x < .3)) {this.destination = 3;}
      else if((x >= .3) && (x < .4)) {this.destination = 4;}
      else if((x >= .4) && (x < .5)) {this.destination = 5;}
      else if(x >= .5) {this.destination = 0;}

    }while(this.destination == this.current_floor);
    //Reject objects with the same destination and current_floor
  }//Passenger_Event(double y)

  // Getters
  public double getTime()
  {
    return time;
  }

  public int getFloor()
  {
    return current_floor;
  }

  public int getDestination()
  {
    return destination;
  }

}//class Passenger_Event extends Event

class Elevator_Event extends Event
{
  double time; //Time of occurrence
  String type; //Event Type: Travelling/ Idle/ Pick Up/ Drop Off

  public void setTime(double x)
  {
    this.time = x;
  }

  public void setType(String x)
  {
    this.type = x;
  }
}

class Passenger
{
  double time; //Time of arrival
  int destination; //Target floor for this passenger
  int current_floor; //The floor on which this passenger has arrived
}

class Elevator
{
  int number; //Identifying number for this elevator
  int current_floor; //Current Location
  int destination; //Target Floor
  int total = 0; // Current number of people on this elevator
  boolean direction; //false = down, true = up
  boolean in_serviece = false; //Is this elevator currently on a job or not
  int[] p_info = new int[]{0,0,0,0,0,0};  //Passenger destination information
}

class elevator_simulator
{
  public static void main(String[] args)
  {
    //Keeps track of a floors with passenger that are currently being serviced by an elevator
    boolean[] targeted = new boolean[]{false,false,false,false,false,false};

    double clock = 0.0; //Keeps track of current time

    //Array to store elevator objects
    Elevator elevators[ ]= new Elevator[4];

    double total_time = 0.0; //Keeps track of the total wait time for all passengers

    int p_count; //Keeps track of the number of passengers who have entered an elevator

    //Will store events that have yet to happen
    ArrayList<Event> future_events_list = new ArrayList<Event>();

    /*
    This array of Passenger arraylists will serve as my delayed list.
    Whenever a passenger arrives, they will be placed on the arraylist that corresponds with the floor of their arrival.
    */
    ArrayList<Passenger>[] floors = new ArrayList[6];

    //Initialize arraylists in floors array
    for (int i = 0; i < 6; i++)
    {
      floors[i] = new ArrayList<Passenger>();
    }

    Random rand = new Random();

    //Create my 4 elevator objects and store them on the elevators array
    for(int i = 0; i < 4; i++)
    {
      Elevator e = new Elevator();
      e.number = i;

      int x = rand.nextInt(6);
      e.current_floor = x;

      elevators[i] = e;
      e = null;
    }

    //Create and add first passenger arrival to future events list
    Passenger_Event pe = new Passenger_Event(clock);
    future_events_list.add(pe);
    pe = null;

    try
    {
      //Print results to output file
      FileWriter scribe = new FileWriter("output.txt");

      scribe.write("Dean Loeafoe\n");
      scribe.write("Final Project - Elevator Simulator\n\n");
      scribe.write("Summary of Transition States of elevators:\n\n");
      scribe.write("Idle -> Travelling/ Pick Up\n");
      scribe.write("Travelling -> Travelling/ Pick Up/ Drop Off/ Idle\n");
      scribe.write("Drop Off -> Pick Up/ Travelling/ Idle\n");
      scribe.write("Pick Up -> Travelling\n\n");

      //I had to do a type conversion to get this to work
      scribe.write(String.format("%-22s", clock) + "- Current Time\n");
      String s = String.valueOf(future_events_list.get(0).getTime());

      //Print the initial status of each elevator and floor
      scribe.write(String.format("%-22s", s) + "- Passenger Arrives  Arrival Floor: " + future_events_list.get(0).getFloor() + "  Destination Floor: " + future_events_list.get(0).getDestination() + "\n\n");

      scribe.write("          E0    E1    E2    E3\n");
      scribe.write("         ______________________\n");
      scribe.write("          ||    ||    ||    ||\n");

      for(int f = 5; f > -1; f--)
      {
        scribe.write(f + "F: " + String.format("%2s", floors[f].size()) + "   ");
        for(int e = 0; e < 4; e++)
        {
          if(elevators[e].current_floor == f)
          {
            scribe.write("[" + String.format("%2s", elevators[e].total) + "]  ");
          }
          else
          {
            scribe.write(" ||   ");
          }

        }//for(int e = 0; e < 4; e++)

        if(f != 0)
        {
          scribe.write("\n          ||    ||    ||    ||\n");
        }
        else
        {
          scribe.write("\n         ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n\n");
        }
      }//for(int f = 0; f < 6; f++)

      //----------------------------------------------------------------------------------------------------

      //Execute first event on future_events_list
      //Check if there are any floors that have passengers in need of Pick up
      //Print the status of the floors and elevators
      for(int i = 0; i < 10; i++)
      {
        //Update current time with time of the first event on future_events_list
        clock = future_events_list.get(0).getTime();

        if(future_events_list.get(0) instanceof Passenger_Event)
        {
          //Generate passenger from executed Passenger_Event
          Passenger p = new Passenger();
          p.time = future_events_list.get(0).time;
          p.current_floor = future_events_list.get(0).current_floor;
          p.destination = future_events_list.get(0).destination;

          //Add that passenger to the appropriate floor
          floors[future_events_list.get(0).current_floor].add(p);
          p = null;

          //Remove first item on future_events_list. It is now executed
          future_events_list.remove(0);

          //Create new passenger event and arrange on future_events_list in time order
          pe = new Passenger_Event(clock);
          future_events_list.add(pe);
          pe = null;

          for(int x = (future_events_list.size() - 1); x > 0; x--)
          {
            if((future_events_list.get(x).time > future_events_list.get(x - 1).time) || (future_events_list.size() <= 1))
            { break; } //End the loop early if the events are ordered or if there is only 1 object on the list

            //Swap obejects if not ordered by transition_time
            if(future_events_list.get(x).time < future_events_list.get(x - 1).time)
            {
              Collections.swap(future_events_list, x, (x - 1));
            }
          }//for(int x = (future_events_list.size() - 1); x > 0; x--)
        }//if(future_events_list.get(0) instanceof Passenger_Event)

        //Print current time
        //Print future_events_list

        scribe.write(String.format("%-22s", clock) + "- Current Time\n");

        for(int j = 0; j < 1; j++)
        {
          if(future_events_list.get(0) instanceof Passenger_Event)
          {
            scribe.write(String.format("%-22s", future_events_list.get(0).getTime()) + "- Passenger Arrives  Arrival Floor: " + future_events_list.get(0).getFloor() + "  Destination Floor: " + future_events_list.get(0).getDestination() + "\n\n");
          }//if(future_events_list.get(0) instanceof Passenger_Event)
        }//for(int j = 0; j < 1; j++)

        //Print the current status of each elevator and floor

        scribe.write("          E0    E1    E2    E3\n");
        scribe.write("         ______________________\n");

        for(int f = 11; f > -1; f--)
        {
          for(int e = 0; e < 4; e++)
          {
            if(f % 2 == 1)
            {

            }
          }
        }

      }//for(int i = 0; i < 1; i++)



      scribe.close();//Close text file
    }//try

    catch (IOException e)
    {
    System.out.println("An error occurred. File not created. Check for errors!");
    e.printStackTrace();
    }//catch




    System.out.println("\nPlease check output.txt for results.");
  }//public static void main(String[] args)
}//Main
