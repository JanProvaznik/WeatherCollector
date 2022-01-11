# WeatherCollector

App that queries OpenWeatherMap.org for weather in Barcelona every 15 minutes.

module **sensor** produces a WeatherEvent every 15 minutes and sends it to Message broker.

module **datalake-builder** stores messages received from the broker in filesystem in a directory given as a parameter.

module **analytics** updates database with the message received from the broker, the directory of the database is given
as parameter then creates a chart and runs a Python script training a Linear regression model and predicting the weather
for next 15 minutes, 3 hours and 1 day.

How to run:

1. have ActiveMQ 5.15.12 running on the default port
2. Have python3 in PATH with numpy, sklearn and pandas installed
3. Get dependencies with Maven
4. run each module
