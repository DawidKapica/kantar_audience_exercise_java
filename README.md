# Audience Exercise Instructions

## Assignment

This assignment involves transforming input statements from a home's set top box into output session records. The input statements represent viewing activities from thousands of homes (e.g., Sky/Virgin).
## Session Formation

A session is formed as follows:
- If a viewer is watching BBC1 at 1900 hrs and switches to ITV1 at 1930, a BBC1 viewing session record would be created from 1900 to 1929 with a duration of 30 minutes.
- The output session record would contain all the input record columns plus end time and duration columns which should be computed by your solution.
- The final session of the day for each home would occur from the last statement start time until the end of the day, i.e., 235959 hours.

## Input Format

The input statements are in pipe-separated format (PSV) and include the following columns:
- `HomeNo`: Home number
- `Channel`: Channel number
- `Starttime`: Start time in YYYYMMDDHHMMSS format
- `Activity`: Type of viewing (Live, Playback)

### Example Input

```
HomeNo|Channel|Starttime|Activity
1234|101|20200101180000|Live
1234|102|20200101183000|Live
45678|103|20200101190000|PlayBack
45678|104|20200101193000|Live
```
## Output Format

The output session records should include all input columns plus the following additional columns:
- `EndTime`: End time in YYYYMMDDHHMMSS format
- `Duration`: Duration in seconds

### Example Output

```
HomeNo|Channel|Starttime|Activity|EndTime|Duration
1234|101|20200101180000|Live|20200101182959|1800
1234|102|20200101183000|Live|20200101235959|19800
45678|103|20200101190000|PlayBack|20200101192959|1800
45678|104|20200101193000|Live|20200101235959|16200
```

## Example Usage

To run the solution using the provided test files, use the following commands:

```
cd <project-root>
mvn clean package
java -jar target/SessionsJob-1.0.jar 'src/test/resources/input-statements.psv' 'target/actual-sessions.psv'
```