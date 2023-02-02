# Quiz maker
Welcome to your local quiz maker!

The application allows you to create, fill in, export and import quizzes.

### Example usage
The user will use a few basic commands to work with the application:
- `run_quiz` - starts the quiz by specifying the quiz number
- `create_quiz` - starts the quiz creation process
- `result` - prints the number of points obtained from the total number of points, the quiz number is asked in advance
- `import` - lists all files that can be imported and lets the user choose which file to import
- `export` - exports the quiz with the entered number to a file, gives the user the option to name the exported file
- `list` - displays a list of quiz names with their numbers
- `help` - lists all applicable commands and a short info about them
- `quit` - terminates the application

A quiz can contain multiple pages and one page can contain multiple questions.
The user can create the following types of questions:
- `text` - a question to which the user must answer with full text answer, this question can be:
     - `exact-match` - the exact wording of the answer is checked
     - `set` - the user must enter an option from the set of valid options
     - `contains-word` - the user's answer to the question must contain the word specified in the reference answer
- `single-choice` - a question that can always be answered with only one answer can be:
     - `with the correct answer` - one of the options will be the correct answer
     - `"jump" type` - none of the options will be the correct answer, but when creating the quiz, it is possible to specify the rules for individual options,
     which enable, for example, that if the user answers with answer no. 2, the quiz will continue by redirecting to page no. 4 etc.
- `multi-choice` - a question with the possibility of several correct answers
     - it will be evaluated as correct only if the user marks all the reference answers when filling it out
    
While creating the quiz, the user will be guided by various questions such as "Enter question type:", "Enter question:", "Enter options:", "Do you want to enter another option? Answer Y/N",
"Do you want to add another question?", "Should the next question go to a new page?" etc.. The user will thus fill in the names of the questions, answers, correct (reference) answers, specify the type of question,
specify the rules for "jump" questions, the type of evaluation for text questions and so on.

### Exports and imports
All export and import files are stored in examples/exports. The application itself can contain several different created/imported quizzes at once. Quiz export/import format is a mixed XML with checksums which contain the amount of options a question has. For example:
```
<quiz>
Keyboard shortcuts
<pages>
<page>
<question>
single-choice
What keyborad shortcut is used for copying?
<options>
3
ctrl+b
ctrl+v
ctrl+c
</options>
<rules_exist>
N
3
</question>
<question>
single-choice
...
```

### How to compile and start the application
Using the command line prompt `make` the aplication will create an executable file which can be run. Prompt `make test` runs all tests and prints a list of success/failed tests. By entering `make clean` command all unnecessary files, which are be created while compiling the program, will be deleted.

Have fun!