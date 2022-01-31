# MoneyTreeApp

This is simple application which shows the list of accounts and transsactions for respective accounts.
This application loads Data from JSON (From assets folder). These Json files has all the required data of accounts and its transactions.


This app loads data from JSON files and Stores in Room database.

For showing the data to UI , all the data fetched from room database using Dao , Repository and ViewModel (LiveData)

All JSON parsing , Database operations like inserting , fetching are done in background using AsyncTask, threads and RXJava.



