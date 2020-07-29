# SecureNative Java Demo

## Setting up testing environment
Add the following Environment Variables:
* SECURENATIVE_API_KEY
* SECURENATIVE_API_URL (defaults to "https://api.securenative-stg.com/collector/api/v1")
* BASE_URL (defaults to "http://localhost:8080")
* EXECUTE_DELAY (defaults to 0)
* EXECUTE_PERIOD (defaults to 1000 * 5 * 60)

## Running SecureNative java agent
[Download](https://mvnrepository.com/artifact/com.securenative) java agent jar file. Run your java program with the following:  
`java -javaagent:/path/to/agent.jar -jar /path/to/application.jar`  
