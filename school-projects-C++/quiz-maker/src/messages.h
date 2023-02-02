/** messages are used for printing to a user to know the state of an application or to ask for a command/question_type etc. */

const char * const INTRODUCTION = "Welcome to your local quiz making machine, this is the list of commands which you can use:";
const char * const MAIN_MENU = "Please input command, for list of commands type \"help\":";

const char * const COMMAND_RUN_Q = "run_quiz";
const char * const COMMAND_CREATE_Q = "create_quiz";
const char * const COMMAND_RESULT_Q = "result";
const char * const COMMAND_IMPORT = "import";
const char * const COMMAND_EXPORT = "export";
const char * const COMMAND_LIST = "list";
const char * const COMMAND_HELP = "help";
const char * const COMMAND_QUIT = "quit";

const char * const HELP_RUN_Q = "runs desired quiz from list of quizzes to answer the questions";
const char * const HELP_CREATE_Q = "starts process of making a quiz";
const char * const HELP_RESULT_Q = "counts result (correct answers) of quiz with specified id";
const char * const HELP_IMPORT = "imports quiz from file";
const char * const HELP_EXPORT = "exports quiz with specified id";
const char * const HELP_LIST = "lists all available quizzes";
const char * const HELP_HELP = "lists all commands and what they do";
const char * const HELP_QUIT = "quits the application";

const char * const PROMPT_Q_NUMBER = "Input number of quiz to be run: ";
const char * const PROMPT_Q_N_RESULT = "Input number of quiz to evalute it's result: ";
const char * const PROMPT_Q_N_EXPORT = "Input number of quiz which will be exported: ";
const char * const PROMPT_Q_N_IMPORT = "Choose which file you want to import (type number): ";
const char * const PROMPT_EXPORT_NAME = "Input how the exported file will be named:";
const char * const PROMPT_IMPORT = "List of files which you can import a quiz from:";

const char * const Q_TEXT = "text";
const char * const Q_SINGLE_CHOICE = "single-choice";
const char * const Q_MULTI_CHOICE = "multi-choice";
const char * const A_EXACT_MATCH = "exact-match";
const char * const A_SET = "set";
const char * const A_CONTAINS = "contains-word";

const char * const PROMPT_Q_NAME = "Input how your quiz will be named:";
const char * const PROMPT_Q_TYPES = "Types of questions are: text, single-choice, multi-choice.\n"
                                    "Single-choice questions can be without a correct answer and used as a \"jump\" question to another page.\n"
                                    "If you type in \"quit\" the creation will be stopped and quiz WON'T be saved. Quit can't be used while inserting options/answers.";
const char * const PROMPT_CREATE_TYPE = "Which type of a question will be created? (single-choice, multi-choice, text)";
const char * const PROMPT_CREATE_Q = "Input question to be asked:";
const char * const PROMPT_OPTIONS_AMOUNT = "How many options will this question have? Type number in range from 1 to ";
const char * const PROMPT_ADD_OPTION = "Add options to your question:";
const char * const PROMPT_ADD_QUESTION = "Do you want to add another question? If yes type \"y\" or \"yes\":";
const char * const PROMPT_CREATE_PAGE = "Will the next question be on a new page? If yes type \"y\" or \"yes\":";

const char * const PROMPT_ANSWER_TEXT_Q = "Type of answer correction in text question can be:\n"
                                          " -> exact-match - user's answer must be exactly the same as refference answer\n"
                                          " -> set - answer from a set of reference answers is correct\n"
                                          " -> contains-word - user's answer must contain specified word\n"
                                          "Input which type of answer correction will be used (by its name):";
const char * const PROMPT_SINGLE_CHOICE_ANSWER = "Does the question has correct answer or not? If not, at the end of creating of this quiz you will be asked to add rules for options.\n"
                                                 "If yes type \"y\" or \"yes\":";

const char * const PROMPT_ADD_RULE_A = "Type the number of answer which will have the rule:";
const char * const PROMPT_ADD_RULE_JUMP = "Type the number of page where will be jumped if user answers by inputted option:";
const char * const PROMPT_ADD_RULE_NEXT = "Do you want to add another rule for this question? If yes type \"y\" or \"yes\":";

const char * const PROMPT_MC_ALL_ANSWERS = "Every option is already checked as a correct answer.";

const char * const PROMPT_REF_ANSWER_STR = "Input reference answer (string): ";
const char * const PROMPT_REF_ANSWER_NUM = "Input reference answer (the number of correct option): ";
const char * const PROMPT_REF_ANSWER_NEXT = "Do you want add another reference answer? If yes type \"y\" or \"yes\":";

const char * const PROMPT_USER_ANSWER_STR = "Input your answer (string): ";
const char * const PROMPT_USER_ANSWER_NUM = "Input your answer (correct option number): ";
const char * const PROMPT_USER_MC_ANSWER_NEXT = "Do you want to check another option? If yes type \"y\" or \"yes\":";

const char * const SUCCESS_EXPORT = "Quiz was successfully exported to: ";
const char * const SUCCESS_CREATE = "Quiz was successfully created! You can run it by typing \"run_quiz\".";
const char * const SUCCESS_RUN = "Quiz was successfully filled! You can see your score by typing \"result\".";
const char * const SUCCESS_IMPORT = "Quiz was successfully imported! You can fill it by typing \"run_quiz\".";

const char * const ERROR_INVALID_COMMAND = "This command doesn't exist, type \"help\" for list of commands.";
const char * const ERROR_INVALID_NUMBER = "Inputted number is not valid! Try one more time.";
const char * const ERROR_DUPLICATION = "This was already inputted, can not contain duplicits. Try one more time.";
const char * const ERROR_OPEN_FILE = "This file can't be opened, please input a valid one!";
const char * const ERROR_OPEN_DIR = "Invalid directory!";
const char * const ERROR_INVALID_Q = "This type of question is not valid!";
const char * const ERROR_INVALID_A = "This number of answer is not valid! Try one more time.";
const char * const ERROR_A_TYPE = "This type of answer correction is not valid!";
const char * const ERROR_EXPORT_DUPLICATION = "File already exists! If you want to override it type \"y\" or \"yes\":";
