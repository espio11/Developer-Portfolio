#include <iostream>
using namespace std;

//Dean Loeafoe Homework 1
//Please feel free to contact me if anything goes wrong with my program.
//dean.loeafoe04@qmail.cuny.edu

int main()
{
    cout << "The following program counts how many times the digit 1 occurs in all number from 1 to n.\n\n";

    cout << "Please enter a number to represent n.\n\n";

    int n;
    int sum = 0;
    string report;
    
    cin >> n;
    cout << "\nThe following number will be tested: " << n; 
    cout << "\n\n";

    for(int i = 1; i <= n; i++)
    {
      int test = i;
      if((test < 10) && (test == 1))
      {
        sum++;
      }
      else
      { 
        while( test > 0)
        {
          if (test % 10 == 1)
          {
            sum++;
          }

          test = test / 10;
        }
      }
    }

    cout << "\nThe number of times the digit 1 appears in the designated range is " << sum;
    cout << "\n\n";


    return 0;
}
