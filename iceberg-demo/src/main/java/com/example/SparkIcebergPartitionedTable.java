package com.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import java.util.Random;
import java.util.UUID;

public class SparkIcebergPartitionedTable {
    public static void main(String[] args) {
        // 初始化 SparkSession
        SparkSession spark = SparkSession.builder()
                .appName("IcebergPartitionedTable")
                .master("local[*]")
                .config("spark.sql.catalog.local", "org.apache.iceberg.spark.SparkCatalog")
                .config("spark.sql.catalog.local.type", "hadoop")
                .config("spark.sql.catalog.local.warehouse", "/tmp/iceberg/warehouse")
                .config("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
                .getOrCreate();

        // 创建带两级分区的 Iceberg 表
        String createTableSQL = "CREATE TABLE IF NOT EXISTS local.db.orders ("
                + "order_id STRING, "
                + "order_name STRING, "
                + "price DOUBLE, "
                + "grass_date DATE, "
                + "country STRING) "
                + "USING iceberg "
                + "PARTITIONED BY (country, grass_date)";
        spark.sql(createTableSQL);

        // 随机生成 100 条数据
        Random random = new Random();
        String[] countries = {"USA", "China", "Germany", "Brazil"};
        String[] dates = {"2023-01-01", "2023-01-02", "2023-01-03"};
        StringBuilder insertSQL = new StringBuilder("INSERT INTO local.db.orders VALUES ");
        for (int i = 0; i < 100; i++) {
            String orderId = UUID.randomUUID().toString();
            String orderName = "Order_" + random.nextInt(1000);
            double price = 10 + random.nextDouble() * 990; // 10 到 1000 之间
            String grassDate = dates[random.nextInt(dates.length)];
            String country = countries[random.nextInt(countries.length)];
            insertSQL.append(String.format("('%s', '%s', %f, CAST('%s' AS DATE), '%s')",
                    orderId, orderName, price, grassDate, country));
            if (i < 99) {
                insertSQL.append(", ");
            }
        }
        spark.sql(insertSQL.toString());

        // 验证数据
        Dataset<Row> result = spark.sql("SELECT * FROM local.db.orders LIMIT 10");
        result.show();

        // 关闭 SparkSession
        spark.stop();
    }
}