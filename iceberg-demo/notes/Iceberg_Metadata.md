# Table

* Table
  * name()
  * refresh() —— 刷新Table Meta
  * 
  * TableScan newScan() —— 创建一个新的 TableScan
  * BatchScan newBatchScan() —— 创建一个 BatchScan
  * IncrementalAppendScan newIncrementalAppendScan() —— 创建一个 IncrementalAppendScan
  * IncrementalChangelogScan newIncrementalChangelogScan() —— 创建一个 IncrementalChangelogScan
  * 
  * Schema schema() —— 返回 Schema
  * Map<Integer, Schema> schemas() —— 返回table的 schema map.  
  * PartitionSpec spec() —— 返回 table 的 PartitionSpec ，即表的分区描述
  * Map<Integer, PartitionSpec> specs() —— 返回 table 的所有分区 map. 
  * 
  * SortOrder sortOrder() —— 返回表的 SortOrder。
  * Map<Integer, SortOrder> sortOrders() —— 返回sortOrder ID和SortOrder的map。 
  * 
  * Map<String, String> properties() —— 返回表属性map 
  * String location() —— 返回表存储路径
  * 
  * Snapshot currentSnapshot() —— 返回表的当前 SnapShot
  * Snapshot snapshot(long snapshotId) —— 返回 snapshotId 对应 Snapshot 引用
  * Iterable<Snapshot> snapshots() —— 获取表 snapshot 迭代器
  * 
  * List<HistoryEntry> history() —— 返回表的 snapshot 历史
  * 
  * UpdateSchema updateSchema() —— 创建一个 UpdateSchema 用来更新 schema 并提交
  * UpdatePartitionSpec updateSpec() —— 创建一个 UpdatePartitionSpec 用来更新分区信息并提交
  * UpdateProperties updateProperties() —— 创建一个 UpdateProperties 用来更新表属性并提交
  * ReplaceSortOrder replaceSortOrder() —— 创建一个 ReplaceSortOrder 用来更新表的 SortOrder 并提交
  * UpdateLocation updateLocation() —— 创建一个 UpdateLocation 用来更新表的location
  * 
  * AppendFiles newAppend() —— 创建一个 AppendFiles API, 用来向表假如文件
  * AppendFiles newFastAppend() —— 
  * RewriteFiles newRewrite() 
  * RewriteManifests rewriteManifests()
  * OverwriteFiles newOverwrite()
  * RowDelta newRowDelta()
  * ReplacePartitions newReplacePartitions()
  * DeleteFiles newDelete()
  * UpdateStatistics updateStatistics()
  * UpdatePartitionStatistics updatePartitionStatistics()
  * ExpireSnapshots expireSnapshots()
  * ManageSnapshots manageSnapshots()
  * 
  * Transaction newTransaction()
  * FileIO io() —— Returns a FileIO to read and write table data and metadata files.
  * EncryptionManager encryption() —— Returns an EncryptionManager to encrypt and decrypt data files.
  * LocationProvider locationProvider() —— Returns a LocationProvider to provide locations for new data files.
  * List<StatisticsFile> statisticsFiles()
  * List<PartitionStatisticsFile> partitionStatisticsFiles()
  * Map<String, SnapshotRef> refs()
  * UUID uuid()
  * Snapshot snapshot(String name)


# Scan

  * TableScan —— 对一次 Table scan API 的配置 
  * BatchScan
  * IncrementalAppendScan
  * IncrementalChangelogScan




# 1. TableMetadata

用来表示 Table Metadata的class。通常一份 Table Metadata 包含如下内容：

1. Schema List ：表示 Table 的Schema描述详情和变更版本详情
2. PartitionSpec List ： 表示 Table 分区的描述详情
3. SortOrder ：Table 中数据和被删除的文件的排序描述
4. Location ： Table的数据和元数据存储路径
5. Properties ： Table 的属性KV对
6. Snapshot List : Table 的所有 snapshot 的描述详情和变更历史

一个 Table Metadata json file示例 (v3.metadata.json)：

```json
{
  "format-version" : 2,
  "table-uuid" : "43231447-a29c-47f6-8172-a54f332ecb2e",
  "location" : "/tmp/iceberg/warehouse/db/sales",
  "last-sequence-number" : 2,
  "last-updated-ms" : 1745552903559,
  "last-column-id" : 3,
  "current-schema-id" : 0,
  "schemas" : [ {
    "type" : "struct",
    "schema-id" : 0,
    "fields" : [ {
      "id" : 1,
      "name" : "id",
      "required" : false,
      "type" : "int"
    }, {
      "id" : 2,
      "name" : "amount",
      "required" : false,
      "type" : "double"
    }, {
      "id" : 3,
      "name" : "sale_date",
      "required" : false,
      "type" : "date"
    } ]
  } ],
  "default-spec-id" : 0,
  "partition-specs" : [ {
    "spec-id" : 0,
    "fields" : [ {
      "name" : "sale_date",
      "transform" : "identity",
      "source-id" : 3,
      "field-id" : 1000
    } ]
  } ],
  "last-partition-id" : 1000,
  "default-sort-order-id" : 0,
  "sort-orders" : [ {
    "order-id" : 0,
    "fields" : [ ]
  } ],
  "properties" : {
    "owner" : "li.luo",
    "write.parquet.compression-codec" : "zstd"
  },
  "current-snapshot-id" : 6206490217468364957,
  "refs" : {
    "main" : {
      "snapshot-id" : 6206490217468364957,
      "type" : "branch"
    }
  },
  "snapshots" : [ {
    "sequence-number" : 1,
    "snapshot-id" : 5007280460602055120,
    "timestamp-ms" : 1745552899694,
    "summary" : {
      "operation" : "append",
      "spark.app.id" : "local-1745552887467",
      "added-data-files" : "1",
      "added-records" : "2",
      "added-files-size" : "941",
      "changed-partition-count" : "1",
      "total-records" : "2",
      "total-files-size" : "941",
      "total-data-files" : "1",
      "total-delete-files" : "0",
      "total-position-deletes" : "0",
      "total-equality-deletes" : "0"
    },
    "manifest-list" : "/tmp/iceberg/warehouse/db/sales/metadata/snap-5007280460602055120-1-51ae4cf8-9fa3-424a-a699-44056e1d98b4.avro",
    "schema-id" : 0
  }, {
    "sequence-number" : 2,
    "snapshot-id" : 6206490217468364957,
    "parent-snapshot-id" : 5007280460602055120,
    "timestamp-ms" : 1745552903559,
    "summary" : {
      "operation" : "overwrite",
      "spark.app.id" : "local-1745552887467",
      "added-data-files" : "1",
      "deleted-data-files" : "1",
      "added-records" : "1",
      "deleted-records" : "2",
      "added-files-size" : "904",
      "removed-files-size" : "941",
      "changed-partition-count" : "1",
      "total-records" : "1",
      "total-files-size" : "904",
      "total-data-files" : "1",
      "total-delete-files" : "0",
      "total-position-deletes" : "0",
      "total-equality-deletes" : "0"
    },
    "manifest-list" : "/tmp/iceberg/warehouse/db/sales/metadata/snap-6206490217468364957-1-8e9d49ab-d9de-42e8-927b-9603fb4e390a.avro",
    "schema-id" : 0
  } ],
  "statistics" : [ ],
  "snapshot-log" : [ {
    "timestamp-ms" : 1745552899694,
    "snapshot-id" : 5007280460602055120
  }, {
    "timestamp-ms" : 1745552903559,
    "snapshot-id" : 6206490217468364957
  } ],
  "metadata-log" : [ {
    "timestamp-ms" : 1745552892695,
    "metadata-file" : "/tmp/iceberg/warehouse/db/sales/metadata/v1.metadata.json"
  }, {
    "timestamp-ms" : 1745552899694,
    "metadata-file" : "/tmp/iceberg/warehouse/db/sales/metadata/v2.metadata.json"
  } ]
}
```


一个Iceberg `非分区表` 表目录结构示例：
```
li.luo@luolishopeenewmac:/tmp/iceberg/warehouse/db/test$ tree .
.
├── data
│   ├── 00000-0-0ed4e36e-ee39-4888-8643-8f90680b21cd-00001.parquet
│   ├── 00000-0-6357f26e-b3c7-4036-b115-2d5934b1700e-00001.parquet
│   ├── 00000-0-9b697d94-adfe-4196-b7fc-c75fc16311f7-00001.parquet
│   ├── 00000-0-e0ffdded-9395-4af2-ab1f-6154d6e81d4d-00001.parquet
│   ├── 00000-0-e29ed0b5-ea09-4ad1-9348-66f48bb63ab5-00001.parquet
│   ├── 00000-0-e2ad9d6e-ee9c-4560-b9af-c822a11efe08-00001.parquet
│   ├── 00000-0-f57af1f4-010a-4c3a-b489-58f9728151c4-00001.parquet
│   ├── 00001-1-0ed4e36e-ee39-4888-8643-8f90680b21cd-00001.parquet
│   ├── 00001-1-6357f26e-b3c7-4036-b115-2d5934b1700e-00001.parquet
│   ├── 00001-1-9b697d94-adfe-4196-b7fc-c75fc16311f7-00001.parquet
│   ├── 00001-1-e0ffdded-9395-4af2-ab1f-6154d6e81d4d-00001.parquet
│   ├── 00001-1-e29ed0b5-ea09-4ad1-9348-66f48bb63ab5-00001.parquet
│   ├── 00001-1-e2ad9d6e-ee9c-4560-b9af-c822a11efe08-00001.parquet
│   └── 00001-1-f57af1f4-010a-4c3a-b489-58f9728151c4-00001.parquet             ← 数据文件
└── metadata
    ├── 00b96a70-a73e-452b-a3d8-a427c8f18227-m0.avro
    ├── 12d25526-b4da-4d71-9e37-f350288db001-m0.avro
    ├── 2ac48652-90e9-4219-9234-27cf38cae740-m0.avro
    ├── 552eb68c-5ac5-4f38-859e-c9c3dfb080db-m0.avro
    ├── 8f699dbf-1d9f-4686-b9fc-7ff1514e9dc6-m0.avro
    ├── a526a897-f0a2-4e59-81c5-1408287e57a6-m0.avro
    ├── e1957d8e-d901-4f3c-8fde-3c67e8794957-m0.avro                           ← ManifestFile 类
    ├── snap-1513587217398638675-1-12d25526-b4da-4d71-9e37-f350288db001.avro
    ├── snap-5412422061727073641-1-552eb68c-5ac5-4f38-859e-c9c3dfb080db.avro
    ├── snap-5780211464833406240-1-e1957d8e-d901-4f3c-8fde-3c67e8794957.avro
    ├── snap-6705611632697455614-1-8f699dbf-1d9f-4686-b9fc-7ff1514e9dc6.avro
    ├── snap-7393123273733423764-1-00b96a70-a73e-452b-a3d8-a427c8f18227.avro
    ├── snap-7992999869146304788-1-a526a897-f0a2-4e59-81c5-1408287e57a6.avro
    ├── snap-973334179108984755-1-2ac48652-90e9-4219-9234-27cf38cae740.avro    ← Snapshot 类
    ├── v1.metadata.json
    ├── v2.metadata.json
    ├── v3.metadata.json
    ├── v4.metadata.json
    ├── v5.metadata.json
    ├── v6.metadata.json
    ├── v7.metadata.json
    ├── v8.metadata.json
    └── version-hint.text
```

一个 Iceberg 分区表目录机构示例：

```
li.luo@luolishopeenewmac:/tmp/iceberg/warehouse/db/orders$ tree .
.
├── data
│   ├── country=Brazil
│   │   ├── grass_date=2023-01-01
│   │   │   └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00001.parquet
│   │   ├── grass_date=2023-01-02
│   │   │   └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00002.parquet
│   │   └── grass_date=2023-01-03
│   │       └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00003.parquet
│   ├── country=China
│   │   ├── grass_date=2023-01-01
│   │   │   └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00004.parquet
│   │   ├── grass_date=2023-01-02
│   │   │   └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00005.parquet
│   │   └── grass_date=2023-01-03
│   │       └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00006.parquet
│   ├── country=Germany
│   │   ├── grass_date=2023-01-01
│   │   │   └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00007.parquet
│   │   ├── grass_date=2023-01-02
│   │   │   └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00008.parquet
│   │   └── grass_date=2023-01-03
│   │       └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00009.parquet
│   └── country=USA
│       ├── grass_date=2023-01-01
│       │   └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00010.parquet
│       ├── grass_date=2023-01-02
│       │   └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00011.parquet
│       └── grass_date=2023-01-03
│           └── 00000-10-2fbe786d-97f9-4626-be1a-0f048c3dced9-00012.parquet
└── metadata
    ├── 25c1a7e6-e83e-4337-8f95-515b6761dd43-m0.avro
    ├── snap-9042763890032070143-1-25c1a7e6-e83e-4337-8f95-515b6761dd43.avro
    ├── v1.metadata.json
    ├── v2.metadata.json
    └── version-hint.text
```

```
元数据文件 (Metadata File, v*.metadata.json)
     └── 快照 (Snapshot)
          └── 清单列表 (Manifest List, snap-*.avro)
                └── 清单文件 (Manifest File, *.avro)  ← ManifestFile 类
                     └── 数据文件 (Data File, *.parquet/orc/avro)
```


## 1.1 Schema

Iceberg的metadata中， 有一段是用来描述表的schema信息，如下示例：

```json
"schemas" : [ {
  "type" : "struct",
  "schema-id" : 0,
  "fields" : [ {
    "id" : 1,
    "name" : "id",
    "required" : false,
    "type" : "int"
  }, {
    "id" : 2,
    "name" : "data",
    "required" : false,
    "type" : "string"
  } ]
} ]
```

Schema 类就是表schema信息的java描述。其中描述了schema中有多少`column`，`column id`，`column name`，`column type`等信息。

* Schema Map
* UpdateSchema


## 1.2 PartitionSpec

Iceberg中用来描述表的 partition 定义的类。用来表示如何 produce 一个表的分区数据。
通常包含表分区的 `spec-id`， `partitionField`列表（即哪写 column 为一个分区列)等信息。
如下示例表示这个表是一个`非分区表`。

```json
"default-spec-id" : 0,
"partition-specs" : [ {
  "spec-id" : 0,
  "fields" : [ ]
} ],
"last-partition-id" : 999
```

以下是一个分区表示例：

```json
"default-spec-id" : 0,
  "partition-specs" : [ {
    "spec-id" : 0,
    "fields" : [ {
      "name" : "country",
      "transform" : "identity",
      "source-id" : 5,
      "field-id" : 1000
    }, {
      "name" : "grass_date",
      "transform" : "identity",
      "source-id" : 4,
      "field-id" : 1001
    } ]
  } ],
  "last-partition-id" : 1001,
```

## 1.3 SortOrder

SortOrder 是用来描述 Table 中的数据和被删除的文件该如何排序的类。
它包含用来做排序的 field 列表。

```json
"default-sort-order-id" : 0,
"sort-orders" : [ {
  "order-id" : 0,
  "fields" : [ ]
} ],
```

## 1.4 Properties

表的属性，通常是一批KV对。在Table Metadata 中用 `Map<String, String>` 来表示.

```json
"properties" : {
  "owner" : "li.luo",
  "write.parquet.compression-codec" : "zstd"
},
```

## 1.5 Snapshot

Snapshot 表示 Table 在某个时间点的数据快照。通常一个 snapshot 包含一个或者多个 manifest 文件，Table 在这个 snapshot 中的完整数据是所有这些 manifest file指向的数据文件的集合。
snapshot 由 Table 的一次 `操作（Operation）` 创建，比如 `AppendFiles` 或 `ReWriteFiles`

一个 Snapshot 包含如下fields：
* `sequenceNumber` : 每个snapshot都会由一个 sequenceNumber，表示这是 Table 的第几个快照。
* `snapshotId` ：每个snapshot都有一个唯一的ID
* `parentId` : 当前快照的 parent 快照ID
* `timestampMillis` : 当前快照的创建时间
* `manifestListLocation` ：当前快照的 manifest list文件路径。注意，这里是manifest list文件的路径，不是 manifest 文件（通常由一个或多个）本身路径
* `operation` ：创建当前snapshot 的 DataOperation 操作。 
  * `append` : 向表中插入新的数据，没有数据删除。由 `AppendFiles` 类实现
  * `replace` ：表中文件被删除并被替换成其他文件，没有对表中数据进行修改。由 `ReWriteFiles` 类实现
  * `overwrite` ： 表中文件的数据被重写并被替换。由 `OverWriteFiels` 和 `ReplacePartition` 类实现
  * `delete` : 数据被从表中删除， 没有数据新增。 由 `DeleteFiles` 类实现
* `schemaId` : 当前快照所使用的schema的id
* `firstRowId` ：当前快照中第一条被写入的数据的 row id。每一条被写入当前快照的数据都会被赋予一条大于改 firstRowId 的ID，所有小于该 firstRowId 的数据都是在当前快照之前的快照中。
* `addedRows` ： 当前快照中写入了多少条新记录。
* `allManifests` : `List<ManifestFile>`，当前快照的所有 manifest files
* `dataManifests` : `List<ManifestFile>`，当前快照的所有 data的 manifest files（parquet file）
* `deleteManifests` : `List<ManifestFile>`，当前快照的所有 数据删除的manifest files
* `addedDataFiles` : `List<ManifestFile>`，当前快照的所有 新写入数据的 manifest files
* `removedDataFiles` : `List<ManifestFile>`，当前快照的所有 删除数据文件的 manifest files
* `addedDeleteFiles` : `List<ManifestFile>`，当前快照的新增删除数据的manifest files
* `removedDeleteFiles` : `List<ManifestFile>`，当前快照的所有删除 数据清理的 manifest files

snapshot在metadata文件中的数据示例如下：

```json
"current-snapshot-id" : 5412422061727073641,
  "refs" : {
    "main" : {
      "snapshot-id" : 5412422061727073641,
      "type" : "branch"
    }
  },
  "snapshots" : [ {
    "sequence-number" : 1,
    "snapshot-id" : 973334179108984755,
    "timestamp-ms" : 1745306934471,
    "summary" : {
      "operation" : "append",
      "spark.app.id" : "local-1745306923882",
      "added-data-files" : "2",
      "added-records" : "2",
      "added-files-size" : "1194",
      "changed-partition-count" : "1",
      "total-records" : "2",
      "total-files-size" : "1194",
      "total-data-files" : "2",
      "total-delete-files" : "0",
      "total-position-deletes" : "0",
      "total-equality-deletes" : "0"
    },
    "manifest-list" : "/tmp/iceberg/warehouse/db/test/metadata/snap-973334179108984755-1-2ac48652-90e9-4219-9234-27cf38cae740.avro",
    "schema-id" : 0
  }, {
    "sequence-number" : 2,
    "snapshot-id" : 6705611632697455614,
    "parent-snapshot-id" : 973334179108984755,
    "timestamp-ms" : 1745307602890,
    "summary" : {
      "operation" : "append",
      "spark.app.id" : "local-1745307592604",
      "added-data-files" : "2",
      "added-records" : "2",
      "added-files-size" : "1194",
      "changed-partition-count" : "1",
      "total-records" : "4",
      "total-files-size" : "2388",
      "total-data-files" : "4",
      "total-delete-files" : "0",
      "total-position-deletes" : "0",
      "total-equality-deletes" : "0"
    },
    "manifest-list" : "/tmp/iceberg/warehouse/db/test/metadata/snap-6705611632697455614-1-8f699dbf-1d9f-4686-b9fc-7ff1514e9dc6.avro",
    "schema-id" : 0
  } ],
```

### 1.5.1 snapshot avro文件 （Manifest List文件）示例

示例内容 （snap-*.avro）：

```json
{
   "manifest_path":"/tmp/iceberg/warehouse/db/orders/metadata/25c1a7e6-e83e-4337-8f95-515b6761dd43-m0.avro",
   "manifest_length":8456,
   "partition_spec_id":0,
   "content":0,  // 0=数据文件, 1=删除文件 对应 ManifestContent 类
   "sequence_number":1,
   "min_sequence_number":1,
   "added_snapshot_id":9042763890032070143,
   "added_data_files_count":12,
   "existing_data_files_count":0,
   "deleted_data_files_count":0,
   "added_rows_count":100,
   "existing_rows_count":0,
   "deleted_rows_count":0,
   "partitions":[
      {
         "contains_null":false,
         "contains_nan":false,
         "lower_bound":"Brazil",
         "upper_bound":"USA"
      },
      {
         "contains_null":false,
         "contains_nan":false,
         "lower_bound":"\u009EK\u0000\u0000",
         "upper_bound":"K\u0000\u0000"
      }
   ]
}
```

### 1.5.2 ManifestFile

ManifestFile 表示一个 manifest 清单文件，清单文件记录一组 **数据文件（Data Files）** 的元信息，数据文件是实际存储表数据的文件（如 Parquet 文件）。

清单文件以 Avro 格式存储，包含多个条目（entry），每个条目描述一个数据文件的元信息。以下是一个清单文件的伪 JSON 表示（实际为 Avro）：

```json
[
  {
    "status": 1,  // 1=新增, 2=现有, 0=删除
    "snapshot_id": 1234567890,
    "data_file": {
      "content": 0,  // 0=数据文件, 1=删除文件 对应 ManifestContent 类
      "file_path": "/tmp/iceberg/warehouse/db/table/data/sale_date=2023-01-01/00000-0-abc123.parquet",
      "file_format": "PARQUET",
      "partition": {"sale_date": "2023-01-01"},
      "record_count": 100,
      "file_size_in_bytes": 1024000,
      "column_sizes": {"1": 4000, "2": 8000},
      "value_counts": {"1": 100, "2": 100},
      "null_value_counts": {"1": 0, "2": 0},
      "lower_bounds": {"1": 1, "2": 10.0},
      "upper_bounds": {"1": 100, "2": 1000.0}
    }
  },
  {
    "status": 1,
    "snapshot_id": 1234567890,
    "data_file": {
      "file_path": "/tmp/iceberg/warehouse/db/table/data/sale_date=2023-01-01/00000-1-def456.parquet",
      "partition": {"sale_date": "2023-01-01"},
      "record_count": 50,
      ...
    }
  }
]
```

* status：数据文件的状态：
  * 1：新增（added）。
  * 2：现有（existing）。
  * 0：删除（deleted）。
* snapshot_id：数据文件所属的快照 ID。
* data_file：数据文件的详细元信息，包括路径、格式、分区值、记录数和列统计信息（如 `lower_bounds` 和 `upper_bounds`）。


## 1.6 MetadataUpdate

表示对 Table或 View 的 Metadata (如v1.metadata.json)的一次变更。 不同的变更类型包括：

## 1.7 PendingUpdate

表示对 Table metadata的一次操作API 描述。其中的调用接口：

* commit() —— 一旦调用，就表示这次对 metadata 的修改正式commit了。一旦commit成功，则表示table已经被更新。

PendingUpdata有很多的子类，分别表示对 Table metadata 的各种不同操作。

* HistoryEntry
  * An entry contains a change to the table state. At the given timestamp, the current snapshot was set to the given snapshot ID.
* UpdateLocation
  * API for setting a table's or view's base location.
* ReplaceSortOrder
  * API for replacing table sort order with a newly created order.
    The table sort order is used to sort incoming records in engines that can request an ordering.
    Apply returns the new sort order for validation.
    When committing, these changes will be applied to the current table metadata. Commit conflicts will be resolved by applying the pending changes to the new table metadata.
* UpdateProperties
  * API for updating table properties.
    Apply returns the updated table properties as a map for validation.
    When committing, these changes will be applied to the current table metadata. Commit conflicts will be resolved by applying the pending changes to the new table metadata.
* UpdatePartitionSpec
  * API for partition spec evolution.
* UpdateSchema
  * API for schema evolution.
* AppendFiles
  * API for appending new files in a table.
    When committing, these changes will be applied to the latest table snapshot. Commit conflicts will be resolved by applying the changes to the new latest snapshot and reattempting the commit.
* RewriteFiles
  * API for replacing files in a table.
    When committing, these changes will be applied to the latest table snapshot. Commit conflicts will be resolved by applying the changes to the new latest snapshot and reattempting the commit. If any of the deleted files are no longer in the latest snapshot when reattempting, the commit will throw a ValidationException.
    Note that the new state of the table after each rewrite must be logically equivalent to the original table state.
* RewriteManifests
  * API for rewriting manifests for a table.
    This API accumulates manifest files, produces a new Snapshot of the table described only by the manifest files that were added, and commits that snapshot as the current.
    This API can be used to rewrite matching manifests according to a clustering function as well as to replace specific manifests. Manifests that are deleted or added directly are ignored during the rewrite process. The set of active files in replaced manifests must be the same as in new manifests.
    When committing, these changes will be applied to the latest table snapshot. Commit conflicts will be resolved by applying the changes to the new latest snapshot and reattempting the commit.
* OverwriteFiles
  * API for overwriting files in a table.
* RowDelta
  * API for encoding row-level changes to a table.
* ReplacePartitions 
  * API for overwriting files in a table by partition.
    This is provided to implement SQL compatible with Hive table operations but is not recommended. Instead, use the overwrite API to explicitly overwrite data.
    The default validation mode is idempotent, meaning the overwrite is correct and should be committed out regardless of other concurrent changes to the table. Alternatively, this API can be configured to validate that no new data or deletes have been applied since a snapshot ID associated when this operation began. This can be done by calling validateNoConflictingDeletes(), validateNoConflictingData(), to ensure that no conflicting delete files or data files respectively have been written since the snapshot passed to validateFromSnapshot(long).
    This API accumulates file additions and produces a new Snapshot of the table by replacing all files in partitions with new data with the new additions. This operation is used to implement dynamic partition replacement.
    When committing, these changes will be applied to the latest table snapshot. Commit conflicts will be resolved by applying the changes to the new latest snapshot and reattempting the commit.
* DeleteFiles
  * API for deleting files from a table. 
* UpdateStatistics
  * API for updating statistics files in a table.
* UpdatePartitionStatistics 
  * API for updating partition statistics files in a table.
* ExpireSnapshots 
  * API for removing old snapshots from a table.
    This API accumulates snapshot deletions and commits the new list to the table. This API does not allow deleting the current snapshot.
    Manifest files that are no longer used by valid snapshots will be deleted. Data files that were deleted by snapshots that are expired will be deleted. deleteWith(Consumer) can be used to pass an alternative deletion method.
    apply() returns a list of the snapshots that will be removed
* ManageSnapshots
  * API for managing snapshots. Allows rolling table data back to a stated at an older table snapshot. Rollback:
    This API does not allow conflicting calls to setCurrentSnapshot(long) and rollbackToTime(long).
    When committing, these changes will be applied to the current table metadata. Commit conflicts will not be resolved and will result in a CommitFailedException. Cherrypick:
    In an audit workflow, new data is written to an orphan snapshot that is not committed as the table's current state until it is audited. After auditing a change, it may need to be applied or cherry-picked on top of the latest snapshot instead of the one that was current when the audited changes were created. This class adds support for cherry-picking the changes from an orphan snapshot by applying them to the current snapshot. The output of the operation is a new snapshot with the changes from cherry-picked snapshot.
* Transaction
  * A transaction for performing multiple updates to a table.
* FileIO 
  * Pluggable module for reading, writing, and deleting files.
    Both table metadata files and data files can be written and read by this module. Implementations must be serializable because various clients of Spark tables may initialize this once and pass it off to a separate module that would then interact with the streams.
* EncryptionManager
  * Module for encrypting and decrypting table data files.
    This must be serializable because an instance may be instantiated in one place and sent across the wire in some Iceberg integrations, notably Spark.
* LocationProvider
  * Interface for providing data file locations to write tasks.
    Implementations must be Serializable because instances will be serialized to tasks.

