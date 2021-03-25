# TDT4240-Brainstorming_the_game

## Naming conventions
- Class names: UpperCamelCase
- Variable names: lowerCamelCase (Should be descriptive)
- Commit messages: #issuenr - What has been done.
- Branch names: issuenr-issue-name

## Workflow
Please comment your code and clearly mark where the architecture/patterns/tactics is used.

When you start on an issue: Assign yourself to the issue and alert the others in slack.
When you're done with an issue, open a pull request and ask someone to merge it.

Never merge your own pull request! 
Never merge directly to main, merge to the development branch first!

## Testing
When setting up JUnit with libGDX we've followed [this guide](http://techduke.io/junit-testing-of-libgdx-game-in-android-studio/). 

### Running tests
To run all tests in a test folder: Right click on the folder and select `Run 'Tests in '<folder name>''`.

To run a single test-file: Right click on the file and select `Run '<file name>'`.

To run a single test: Left click on the green checkmark next to the name of the test in the code, and select `Run '<test name>'`.

### Writing a new test
To write a new unit test:
- Open the class you want to test
- Click on the file and press `Ctrl + Shift + T`
- When the 'Create Test' dialog comes up, click 'Create New Test...'
- Make sure the Testing library is set to 'JUnit4'
- Click 'Fix' link if it pops up
- Select appropriate checkboxes on what test to Generate
- Click 'OK'

The tests can be found in core/src/test/java. 
The IDE may display them under core/src/java, or core/java/java.

