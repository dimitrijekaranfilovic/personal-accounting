# personal-accounting

### Introduction

Compact and standalone desktop Java app combined with 
SQLite database for keeping track of your finances.

### Getting Started
This section will be dedicated to explaining 
application's features and how to use them.

#### Initial setup

If you haven't set up the app, or it is your first time
starting it, you will be met with the following
screen

![picture](images/welcome.png)

Click on the next button, which will lead you to the configurations page

![picture](images/configurations.png)

Here, you enter currencies whose balances you wish to keep track of. Bear in mind, currency abbreviations
must be exactly 3 characters long. In addition, you have to enter at least one before proceeding.

If you entered everything correctly, click the add button:  ![picture](src/icons/plus-2.png)

After that, you can add more currencies, or you can finish the setup by clicking the next button: ![picture](src/icons/play.png)

Now, after the setup is complete, you are free to use the app however you want.

#### Panels

In this section, all app's and panels and functionalities will be explained.

##### Home Panel

After setup and every time after that, once you start the app you will be greeted with a home screen

![picture](images/home.png)

Here you can see the current balance of each of your currencies. Currently displayed currency can be changed by
selecting a desired one through a menu

![picture](images/menu.png)


###### Home Panel Legend
Home panel buttons will be explained. Other panels mentioned here will be explained shortly.

* add activity button ![picture](src/icons/plus-2.png) takes you to add activity panel
* activities history button ![picture](src/icons/stock-market-analysis.png) takes you to activities filter panel
* balances history button ![picture](src/icons/balance-2.png) takes you to balances filter panel
* add currency button ![picture](src/icons/currency-1.png) takes you to add currency panel
* settings button ![picture](src/icons/settings.png) takes you to settings panel
* help button ![picture](src/icons/help-2.png) will display help message which will take you to my github page

If at any time you forget which button is which, tooltips are provided for each button.


##### Add Activity Panel

If you click on the add activity button, you will be presented with the AddActivityPanel. 

![picture](images/add_activity.png)

Here you can choose which currency and type your activity has. After you've entered everything, you can confirm
by clicking the ok button ![picture](src/icons/accept.png). You can go back to home panel by clicking 
the back button ![picture](src/icons/skip-back.png)

##### Add Currency Panel

If you forgot to add a currency during the initial setup, it is no problem. You can always add additional currencies
by clicking on the home panel's add currency button

![picture](images/currency.png)


#### Activities History Panel

Here you can search and filter all of your past activities

![picture](images/activities_filter.png)

After clicking ok button, if there are no results or parameters were not specified correctly, warning or
error message will be displayed. Otherwise, you will be presented with a Display Activities Panel.


#### Display Activities Panel

Here all filtered activities are displayed and can be sorted at will.

![picture](images/display_activities.png)

Plus represents income and minus represent expense.


#### Balances History Panel

The same way you can filter activities, you can filter balances.

![picture](images/balances_filter.png)

After clicking ok button, if there are no results or parameters were not specified correctly, warning or
error message will be displayed. Otherwise, you will be presented with a Display Balances Panel.


#### Display Balances Panel

Here all filtered balances are displayed and can be sorted at will.

![picture](images/display_balances.png)


#### Settings Panel

Here you can tweak the app to your liking:
* you can change the LookAndFeel (style)
* you can change the language

![picture](images/settings.png)

Currently available languages:
* serbian latin
* english


Currently available styles:
* nimbus
* metal
* mcwin
* system default - it will look different on different platforms


Language changes are real time and are saved for the next startup.

### Installers

If you do not wish to download the entire repository, installers are available
*  <a id="raw-url" href="https://raw.githubusercontent.com/dimitrijekaranfilovic/downloadable-content/master/personal-accounting-installers/PersonalAccounting-1.0.exe">Windows</a>

*  <a id="raw-url" href="https://raw.githubusercontent.com/dimitrijekaranfilovic/downloadable-content/master/personal-accounting-installers/personalaccounting_amd64.deb">Debian Linux</a>

### Additional information

* App will save data about your last used LookAndFeel, language and last position of the window and 
when run next time it will apply those settings
* App was written using jdk-14.0.1 and jdk-12.0.2 and can be run by them.
* Default LookAndFeel is Nimbus and default language is english.

### Known bugs and issues

* When loading previously saved LookAndFeel, NullException is thrown, so that feature is suspended for now.
* Properties files containing UTF characters cannot be properly loaded when program is executed from a
executable .jar file.

### Future updates

* Enable printing functionality.
* When displaying activities, display difference between incomes and expenses for each currency for specified time period.
* Localisation for date display.


### Credits
* https://iconscout.com/icon/accept-149

* https://iconscout.com/icon/balance-200

* https://iconscout.com/icon/currency-2493209

* https://iconscout.com/icon/help-question-mark-faq-support-ui-interface-6

* https://iconscout.com/icon/play-next-forward-replay-7

* https://iconscout.com/icon/plus-add-inset-append-attach

* https://iconscout.com/icon/printer-print-copy-ui-interface-paper-ink-7

* https://iconscout.com/icon/settings-382

* https://iconscout.com/icon/skip-back-6

* https://iconscout.com/icon/stock-market-analysis-2031586


