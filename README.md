# WeatherCollector
App that queries OpenWeatherMap.org for weather in Barcelona every 15 minutes 

module Sensor produces a WeatherEvent every 15 minutes and sends it to Message broker

module datalake-builder stores messages received from the broker in filesystem

module analytics updates database with the message recieved from the broker,
then creates a chart and predicts next datapoint

Prerequisite: have ActiveMQ 5.15.12 running on the default port
Have python in PATH with numpy and sklearn installed
