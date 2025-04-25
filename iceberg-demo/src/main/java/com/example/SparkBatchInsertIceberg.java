package com.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SparkBatchInsertIceberg {
    public static void main(String[] args) throws InterruptedException {
        // 初始化 SparkSession
        SparkSession spark = SparkSession.builder()
                .appName("BatchIceberg")
                .master("local[*]")
                .config("spark.sql.catalog.local", "org.apache.iceberg.spark.SparkCatalog")
                .config("spark.sql.catalog.local.type", "hadoop")
                .config("spark.sql.catalog.local.warehouse", "/tmp/iceberg/warehouse")
                .config("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
                .getOrCreate();

        // 创建表
        spark.sql("CREATE TABLE IF NOT EXISTS local.db.sales2 (id INT, amount DOUBLE, sale_date DATE) USING iceberg PARTITIONED BY (sale_date)");

        // 模拟一分钟内的多次插入
        List<Dataset<Row>> batchData = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) { // 模拟 10 次小批量插入
            String sql = String.format("SELECT %d AS id, %f AS amount, CAST('2023-01-01' AS DATE) AS sale_date",
                    i, random.nextDouble() * 1000);
            batchData.add(spark.sql(sql));
            Thread.sleep(5000); // 模拟 5 秒一次插入
        }

        // 合并数据并写入
        Dataset<Row> combinedData = batchData.get(0);
        for (int i = 1; i < batchData.size(); i++) {
            combinedData = combinedData.union(batchData.get(i));
        }
        combinedData.write().mode("append").saveAsTable("local.db.sales2");

        // 验证快照
        spark.sql("SELECT * FROM local.db.sales2.snapshots").show();

        spark.sql("SELECT * FROM local.db.sales2").show();
        /*
          DESCRIBE TABLE local.db.sales2.snapshots
          +-------------+------------------+-------+
          |     col_name|         data_type|comment|
          +-------------+------------------+-------+
          | committed_at|         timestamp|   null|
          |  snapshot_id|            bigint|   null|
          |    parent_id|            bigint|   null|
          |    operation|            string|   null|
          |manifest_list|            string|   null|
          |      summary|map<string,string>|   null|
          +-------------+------------------+-------+
         */

        // 关闭 SparkSession
        spark.stop();
    }
}