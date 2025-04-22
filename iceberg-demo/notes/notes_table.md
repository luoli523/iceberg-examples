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


# Schema
* Schema Map
* UpdateSchema


# PartitionSpec

# SnapShot
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

# SortOrder
