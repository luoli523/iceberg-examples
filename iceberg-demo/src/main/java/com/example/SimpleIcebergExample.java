package com.example;

import org.apache.iceberg.Table;
import org.apache.iceberg.catalog.TableIdentifier;
import org.apache.iceberg.spark.SparkCatalog;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SimpleIcebergExample {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("SimpleIceberg")
                .master("local[*]")
                .config("spark.sql.catalog.local", "org.apache.iceberg.spark.SparkCatalog")
                .config("spark.sql.catalog.local.type", "hadoop")
                .config("spark.sql.catalog.local.warehouse", "/tmp/iceberg/warehouse")
                .getOrCreate();

        spark.sql("CREATE TABLE IF NOT EXISTS local.db.test (id INT, data STRING) USING iceberg");
        spark.sql("INSERT INTO local.db.test VALUES (1, 'a'), (2, 'b')");

        SparkCatalog catalog = (SparkCatalog) spark.sessionState()
                .catalogManager()
                .catalog("local");

        TableIdentifier tableId = TableIdentifier.of("db", "test");
        Table icebergTable = catalog.icebergCatalog().loadTable(tableId);

        // 输出表的元数据信息
        System.out.println("Table Location: " + icebergTable.location());
        System.out.println("Schema: " + icebergTable.schema());
        System.out.println("Partition Spec: " + icebergTable.spec());
        System.out.println("Current Snapshot: " + icebergTable.currentSnapshot());
        System.out.println("Snapshot ID: " + icebergTable.currentSnapshot().snapshotId());

        Dataset<Row> result = spark.sql("SELECT * FROM local.db.test");
        result.show();

        spark.stop();
    }
}
