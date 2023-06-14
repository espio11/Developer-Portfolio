"""
Dean Loeafoe
CS 381 Python Project

I created a game of Hangman. Standard rules apply. The user will be given a topic at random. The mystery phrase will be related to that topic. The user will guess a letter he or she believes is in the phrase. If the letter is in the phrase, all copies of that letter will be revealed in the mystery phrase. If the letter is not in the mystery phrase, the user will receive a strike. Another part of the hangman's body will be printed when the user receives a strike. The user wins the game if he or she is able to fully complete the phrase before receiving 6 strikes. If the user receives 6 strikes, the hangman will be printed, the user will receive a failure message and the game will terminate. Enjoy!

Side Note: All of the files I've provided must be in the same folder for this to work.
You should have one of each of the following files:

loeafoe_hangman.py
animals.txt
body.txt
countries.txt
hangman0.txt
hangman1.txt
hangman2.txt
hangman3.txt
hangman4.txt
hangman5.txt
hangman6.txt
victory.txt

"""
import random

#Will be printed when the user guesses correctly
def correct_guess(count):
  print("\nCorrect! The selected letter is in the phrase. You can make a total of", (6 - count), "more incorrect guesses before losing the game.")

#Will be printed when the user guesses incorrectly
def incorrect_guess(count):
  print("\nUnfortunately, the selected letter is not in the phrase. You have received a strike! You can make a total of", (6 - count), "more incorrect guesses before losing the game.")

#Checks if the current guessed letter is in the phrase
#Modifies tracker's values based on the player's guess
#Prints the current state of the phrase
def checker(tracker, selected_phrase, guess):
  for i in range(len(tracker)):
    if guess == selected_phrase[i].lower():
      tracker[i] = 1

  print()

  for j in range(len(tracker)):
    if tracker[j] == 0:
      print("_", end = " ")
    else:
      print(selected_phrase[j], end = " ")
  print()

#List of potential topics to pick from
topics = ["Animals!\n", "Countries!\n", "Parts of the Body!\n"]

#Keeps track of how many incorrect guesses the player has made
strikes = 0

#A list of phrases from the selected topic
#Will be populated once a topic is selected
phrases = []

#Random number to select a random topic
rand = random.randint(0, len(topics) - 1)

#Opening messages
print("Welcome to my version of Hangman!")
print("You have up until 6 incorrect guesses to complete the phrase.")
print("Each time you guess an incorrect letter, the Hangman will receive another body part.")
print("The Hangman will be complete after 6 incorrect guesses.")
print("The game will be over at that time.")
print("You will win the game if you can complete the phrase before the Hangman is completed.")
print("Good Luck and choose carefully!\n")
print("The topic of this game is", topics[rand])

#Create list of possible topics from which the actual word will be chosen
if rand == 0: 
  with open('animals.txt', 'r') as file:
    for line in file:
      for word in line.split():
        phrases.append(word)
elif rand == 1:
  with open('countries.txt', 'r') as file:
    for line in file:
      for word in line.split():
        phrases.append(word)
else:
  with open('body.txt', 'r') as file:
    for line in file:
      for word in line.split():
        phrases.append(word)

#Select a random word from the randomly selected topic
rand2 = random.randint(0, len(phrases) - 1)
selected_phrase = phrases[rand2]

#Close file
file.close()

#Used to keep track of the current state of the phrase with 1's and 0's
tracker = []

#Populate tracker with empty spaces represented by 0's
for i in range(len(selected_phrase)):
  tracker.append(0)

# Print underscores that will represent the word
for j in range(len(selected_phrase)):
  print("_", end=" ")
print("\n")

#Print the first hangman
f = open('hangman0.txt')
z = f.read()
print(z)
f.close()
print()

#Keeps track of incorrect guesses
count = 0

#Keeps track of the turns
turn = 1

#Keeps track of what letters the player has used already
used = set()

#Will continue until 6 incorrect guesses has been made
while count < 6:
  print("\n----------------------------------------------------------------------")
  print("Turn:", turn)
  print()

  #Ask player to guess a letter
  guess = (input("Please select a letter.\n"))
  
  #Ask the player for another input if the guess is not a letter or has been used before
  while guess.lower() in used or guess.lower() < 'a' or guess.lower() > 'z':
    
    #Warn the player if that letter has been used already
    if guess.lower() in used:
      print("\nThat letter was used already.")
      print("Please select another aside from the following:\n")
      print(used)
      print()
    
    #Remind the player that only 'a' to 'z' characters are acceptable
    if guess.lower() < 'a' or guess.lower() > 'z':
      print("\nImproper character input!")
      print("Please select a letter from A to Z.")
      if len(used) > 0:
        print("\nThe following letters have been used already.")
        print(used)
      print()

    checker(tracker, selected_phrase, guess)

    #Ask the player for a new input
    guess = (input("Please select a letter.\n"))
 
  #Once the input is accpeted, add it to the set of used letters
  used.add(guess)

  #Compare the values of the phrase's state before and after the player's guess
  #Print the current state of the phrase
  start_sum = sum(tracker)
  checker(tracker, selected_phrase, guess)
  end_sum = sum(tracker)

  #Print the appropriate message based on the player's guess
  if end_sum == start_sum:
    count += 1 #Increment the number of incorrect guesses
    incorrect_guess(count)
  else:
    correct_guess(count)

  #Print used letters
  if len(used) > 0:
    print("\nThe following letters have been used already.")
    print(used)
  print()

  #Print the proper hangman based on the number of incorrect guesses the player has made
  if count == 0:
    f = open('hangman0.txt')
    z = f.read()
    print(z)
    f.close()
    print()
  elif count == 1:
    f = open('hangman1.txt')
    z = f.read()
    print(z)
    f.close()
    print()
  elif count == 2:
    f = open('hangman2.txt')
    z = f.read()
    print(z)
    f.close()
    print()
  elif count == 3:
    f = open('hangman3.txt')
    z = f.read()
    print(z)
    f.close()
    print()
  elif count == 4:
    f = open('hangman4.txt')
    z = f.read()
    print(z)
    f.close()
    print()
  elif count == 5:
    f = open('hangman5.txt')
    z = f.read()
    print(z)
    f.close()
    print()
  else:
    f = open('hangman6.txt')
    z = f.read()
    print(z)
    f.close()
    print(" It turns out the proper word was", selected_phrase, "!")
    print()
    exit()

  #Print victory message if victory conditions are achieved
  if sum(tracker) == len(selected_phrase):
    f = open('victory.txt')
    z = f.read()
    print(z)
    f.close()
    print()
    exit()

  #Increment turn counter
  turn += 1
  

