# WeatherCollector
App that queries OpenWeatherMap.org for weather in Barcelona every 15 minutes and stores the result to csv and sends it to message broker (ActiveMQ)

More features will be added


Prerequisite: have ActiveMQ 5.15.12 running on the default port



  <component name="RunManager" selected="Application.Main">
    <configuration name="Main" type="Application" factoryName="Application">
      <option name="MAIN_CLASS_NAME" value="Main" />
      <module name="sensor" />
      <method v="2">
        <option name="Make" enabled="true" />
      </method>
    </configuration>
    <configuration default="true" type="JetRunConfigurationType">
      <module name="datalake-builder" />
      <method v="2">
        <option name="Make" enabled="true" />
      </method>
    </configuration>
    <configuration default="true" type="KotlinStandaloneScriptRunConfigurationType">
      <module name="datalake-builder" />
      <option name="filePath" />
      <method v="2">
        <option name="Make" enabled="true" />
      </method>
    </configuration>
    <configuration name="WeatherCollector" type="MavenRunConfiguration" factoryName="Maven" nameIsGenerated="true">
      <MavenSettings>
        <option name="myGeneralSettings" />
        <option name="myRunnerSettings" />
        <option name="myRunnerParameters">
          <MavenRunnerParameters>
            <option name="profiles">
              <set />
            </option>
            <option name="goals">
              <list />
            </option>
            <option name="pomFileName" />
            <option name="profilesMap">
              <map />
            </option>
            <option name="resolveToWorkspace" value="false" />
            <option name="workingDirPath" value="$PROJECT_DIR$" />
          </MavenRunnerParameters>
        </option>
      </MavenSettings>
      <method v="2" />
    </configuration>
    <list>
      <item itemvalue="Application.Main" />
      <item itemvalue="Maven.WeatherCollector" />
    </list>
  </component>
  <component name="SpellCheckerSettings" RuntimeDictionaries="0" Folders="0" CustomDictionaries="0" DefaultDictionary="application-level" UseSingleDictionary="true" transferred="true" />
  <component name="TaskManager">
    <task active="true" id="Default" summary="Default task">
      <changelist id="9ad8f7cc-90b1-41a9-aa74-2118b407efaa" name="Changes" comment="" />
      <created>1637319567611</created>
      <option name="number" value="Default" />
      <option name="presentableId" value="Default" />
      <updated>1637319567611</updated>
      <workItem from="1637319572574" duration="280000" />
      <workItem from="1637342040673" duration="16919000" />
      <workItem from="1638110549805" duration="18390000" />
      <workItem from="1638798673962" duration="3743000" />
      <workItem from="1639050631925" duration="1870000" />
      <workItem from="1639135510963" duration="413000" />
      <workItem from="1639135968438" duration="5585000" />
      <workItem from="1639495455613" duration="171000" />
      <workItem from="1639495657269" duration="897000" />
    </task>
    <task id="LOCAL-00001" summary="initial commit">
      <created>1638108647445</created>
      <option name="number" value="00001" />
      <option name="presentableId" value="LOCAL-00001" />
      <option name="project" value="LOCAL" />
      <updated>1638108647445</updated>
    </task>
    <task id="LOCAL-00002" summary="initial commit">
      <created>1638108684908</created>
      <option name="number" value="00002" />
      <option name="presentableId" value="LOCAL-00002" />
      <option name="project" value="LOCAL" />
      <updated>1638108684908</updated>
    </task>
    <task id="LOCAL-00003" summary="second practical done">
      <created>1638136339066</created>
      <option name="number" value="00003" />
      <option name="presentableId" value="LOCAL-00003" />
      <option name="project" value="LOCAL" />
      <updated>1638136339066</updated>
    </task>
    <task id="LOCAL-00004" summary="prettify">
      <created>1638265237023</created>
      <option name="number" value="00004" />
      <option name="presentableId" value="LOCAL-00004" />
      <option name="project" value="LOCAL" />
      <updated>1638265237023</updated>
    </task>
    <task id="LOCAL-00005" summary="refactor to use Builder Pattern and single responsibility principle">
      <created>1638276142893</created>
      <option name="number" value="00005" />
      <option name="presentableId" value="LOCAL-00005" />
      <option name="project" value="LOCAL" />
      <updated>1638276142893</updated>
    </task>
    <task id="LOCAL-00006" summary="refactor to use Builder Pattern and single responsibility principle">
      <created>1638801834668</created>
      <option name="number" value="00006" />
      <option name="presentableId" value="LOCAL-00006" />
      <option name="project" value="LOCAL" />
      <updated>1638801834668</updated>
    </task>
    <option name="localTasksCounter" value="7" />
    <servers />
  </component>
  <component name="TypeScriptGeneratedFilesManager">
    <option name="version" value="3" />
  </component>
  <component name="Vcs.Log.Tabs.Properties">
    <option name="TAB_STATES">
      <map>
        <entry key="MAIN">
          <value>
            <State />
          </value>
        </entry>
      </map>
    </option>
  </component>
