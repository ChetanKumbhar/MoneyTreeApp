# MoneyTreeApp

This is simple application which shows the list of accounts and transactions for respective accounts.
This application loads Data from JSON (From assets folder). These Json files has all the required data of accounts and its transactions.


This app loads data from JSON files and Stores in Room database.

For showing the data to UI , all the data fetched from room database using Dao , Repository and ViewModel (LiveData)

All JSON parsing , Database operations like inserting , fetching are done in background using AsyncTask, threads and RXJava.


Below is the flow diagram / Artitecture for this application,
This application uses MVVM desing pattern.

<img width="905" alt="Screenshot 2022-02-01 at 0 38 08" src="https://user-images.githubusercontent.com/55182041/151823889-5024ae49-f020-4bb3-9373-cd4b6bcf0dcf.png">

