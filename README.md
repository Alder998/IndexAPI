# Index Build

### 1. How to load the project 
This project is shared on GitHub and can be Downloaded from there.

### 2. Project Structure
The project has been developed on a structure with the following points:
- **DataClass** takes the data from the two In-Memory Storages with all the Indexes created, and the stocks available to build the Indexes 
- **APIController** is an intermediate class that is supposed to intervene if an Error occurs to prevent data to be saved with errors
- **APIRestEndPoint** stores the API endpoints

### 2.1 Data
All the data used for building the indexes (i.e., the StockSample.json file) has been filled (and it is currently been filled) with all the **S&P500 components' data from Yahoo Finance**. The data has been taken from the **Python's yfinance library** with a batch file. This batch can run in every moment from the local PC and update the JSON file. The algorithm has not been included in this repository because it saves the data to a local directory. However, The stocks data on the date 17/07/2024 is available in the project file.

### 3. Project Assumptions
Regarding the Index construction, some very basic Assumptions have been made:
- In every index, every single stock has the same percentage (the indexes have a **uniform composition**). This composition needs the right updates to be maintained.
- The indexes are supposed to be **Total Return Indexes**. Therefore, the dividends are always reinvested in the stock total Value, leading to various Updates.
- The stock quantity in each index can be lower than 1. This is just a matter of simplicity, to be able to manage Indexes with a number of stocks that is always defined.
 
### 4. Possible Improvements
The possible improvements and corrections that could be done within this code base are:
- Deletion of Class APIController, or of APIRestEndPoint, and keep one single EndPoint Service that manages the data from the main DataClass. This would make the project structure faster and more agile and the data flows less difficult to read and understand.
- Improvement of methods to make the calls able to run in a concurrent way. I have no time to understand and study such methods, and I think that having a know-how about how to implement such structures would have improved my developer's abilities
- A better description of Errors and APIs Call. The errors that have been added are now 100% working as they would need. To have clear Error-tracing procedures is one of the most useful practices in Back-end Development.
- Last, but not least, I tried (and failed due to lack of time :)) to implement a Methods that updates the index prices according to the new stock prices, and creates a index Time series (that was supposed to be represented in the file indexSeries.json). This would have linked better the code to a final product devlopment. But maybe I would take this code again, and implement such a functionality.

### 5. Project Feedback
Personally, I really liked this little project. I knew almost nothing about how to create an Index, and this lead me to study and think about possible implementations. Overall, this work widened my horizons in something that I really like to do, and gave me ideas and inspirations for future projects. 
