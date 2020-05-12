Multiple Routes Sharing Aggregator Poc
======================================
Camel Router Spring Project
Created 11 May 2020 By CIBG Netherlands - DvdBr

Project to demonstrate the behavior of two batchconsumer routes using one aggregation route.
If both consume files at the same time, even with the correlation header, the CamelBatchComplete closes all exchanges.
This will give unexpected behavior. (at least for me)

I would expect the correlation header to separate the two flows including the closing of the batch.

===========================
Start the routes. Notice the logs and created zips. Place an extra file in dir1 to close the open batches.

To build this project use

    mvn install

To run this project with Maven use

    mvn camel:run

For more help see the Apache Camel documentation

    http://camel.apache.org/

