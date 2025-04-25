package com.example;

import org.apache.spark.sql.SparkSession;

public class IcebergSnapshotExample {
    public static void main(String[] args) {
        // 初始化 SparkSession
        SparkSession spark = SparkSession.builder()
                .appName("IcebergSnapshotExample")
                .master("local[*]")
                .config("spark.sql.catalog.local", "org.apache.iceberg.spark.SparkCatalog")
                .config("spark.sql.catalog.local.type", "hadoop")
                .config("spark.sql.catalog.local.warehouse", "/tmp/iceberg/warehouse")
                .config("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
                .getOrCreate();

        // 步骤 1：创建表并插入数据
        spark.sql("CREATE TABLE IF NOT EXISTS local.db.sales (id INT, amount DOUBLE, sale_date DATE) USING iceberg PARTITIONED BY (sale_date)");
        spark.sql("INSERT INTO local.db.sales VALUES "
                + "(1, 100.0, CAST('2023-01-01' AS DATE)), "
                + "(2, 200.0, CAST('2023-01-01' AS DATE))");

        // 步骤 2：删除数据
        spark.sql("DELETE FROM local.db.sales WHERE id = 1");

        // 步骤 3：重写清单文件
        // spark.sql("CALL local.system.rewrite_manifests('db.sales')");

        // 查看快照历史
        spark.sql("SELECT snapshot_id, operation, summary FROM local.db.sales.snapshots").show();

        // 关闭 SparkSession
        spark.stop();
    }
}