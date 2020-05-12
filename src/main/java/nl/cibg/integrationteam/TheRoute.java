package nl.cibg.integrationteam;

/*
    Route to demonstrate the effect of having two BatchConsumers using the same aggregator route.
    By CIBG Netherlands - DvdBr
 */

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.zipfile.ZipAggregationStrategy;
import org.springframework.stereotype.Component;

@Component
public class TheRoute extends RouteBuilder {
    private static final String DRCT_ZIPPER = "direct:theroute.zip.aggregator";
    private static final String MYCORID = "MyCorrelationId";

    @Override
    public void configure() throws Exception {
        from("file:./input/dir1")
                .setHeader(MYCORID, constant("COR1"))
                //.process(new DebugHookProcessor())
                .log("processing file dir1 -- File: ${header.CamelFileNameConsumed} Size: ${exchangeProperty.CamelBatchSize} Index: ${exchangeProperty.CamelBatchIndex} Complete?: ${exchangeProperty.CamelBatchComplete}")
                .to(DRCT_ZIPPER)
        ;

        from("file:./input/dir2")
                .setHeader(MYCORID, constant("COR2"))
                //.process(new DebugHookProcessor())
                .log("processing file dir2 -- File: ${header.CamelFileNameConsumed} Size: ${exchangeProperty.CamelBatchSize} Index: ${exchangeProperty.CamelBatchIndex} Complete?: ${exchangeProperty.CamelBatchComplete}")
                .to(DRCT_ZIPPER)
        ;

        from(DRCT_ZIPPER)
                .aggregate(header(MYCORID), new ZipAggregationStrategy())
                    .completionFromBatchConsumer()
                    .eagerCheckCompletion()
                //.process(new DebugHookProcessor())
                .log("AGGREGATED -- Correlation Id Header: ${header.MyCorrelationId} CamelAggregatedCorrelationKey:${exchangeProperty.CamelAggregatedCorrelationKey} File: ${header.CamelFileNameConsumed} Size: ${exchangeProperty.CamelBatchSize} Index: ${exchangeProperty.CamelBatchIndex} Complete?: ${exchangeProperty.CamelBatchComplete}")
                .to("file:./output")
        ;

    }
}
