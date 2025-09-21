
// /**
// * @파일명 : DataPurgeServiceImpl.java
// * @설명 : 데이터 백업 및 삭제 서비스 구현체
// * @작성자 : 김승연
// * @작성일 : 2025.09.05
// * @변경이력 :
// * 2025.09.05 김승연 최초 생성
// */
// @Service
// @RequiredArgsConstructor
// @Slf4j
// public class DataPurgeServiceImpl {

// private final DSLContext dsl;

// @Transactional
// public void purgeWithBackup(PurgeConfig config) {
// String backupDb = "CBSK_BACK";
// String backupTableName = config.getTableName() + "_BACK";

// // 1. 백업 테이블 스키마 동기화
// syncBackupTableSchema(backupDb, backupTableName, config.getTableName());

// // 2. 삭제 대상 조건 구성 (STS)
// Condition condition =
// DSL.field("DEL_DT").lt(DSL.currentTimestamp().minus(config.getDays(),
// DatePart.DAY));
// if (!"CD".equals(config.getDeleteSts())) {
// condition =
// condition.and(DSL.field("STS").in(config.getDeleteSts().split(""))); // "C"
// or "D"
// }

// // 3. 백업 데이터 INSERT
// int inserted = dsl.insertInto(
// DSL.table(DSL.name(backupDb, backupTableName)))
// .columns(getAllColumns(config.getTableName()))
// .select(dsl.select(getAllColumns(config.getTableName()))
// .from(DSL.table(DSL.name(config.getTableName())))
// .where(condition)
// .limit(config.getBatchSize()))
// .execute();

// // 4. 원본 데이터 삭제
// int deleted = dsl.deleteFrom(DSL.table(DSL.name(config.getTableName())))
// .where(condition)
// .limit(config.getBatchSize())
// .execute();

// // 5. 로그 출력
// log.info("Purge completed for table: {}, backup: {}.{} | Inserted: {},
// Deleted: {}",
// config.getTableName(), backupDb, backupTableName, inserted, deleted);
// }

// /**
// * 백업 테이블 스키마를 운영 테이블과 맞추는 메서드
// */
// private void syncBackupTableSchema(String backupDb, String backupTable,
// String originalTable) {
// // 백업 테이블 존재 여부 확인
// boolean exists = dsl.fetchExists(
// DSL.selectOne()
// .from("information_schema.tables")
// .where(DSL.field("table_schema").eq(backupDb))
// .and(DSL.field("table_name").eq(backupTable)));

// if (!exists) {
// // 없으면 생성
// dsl.query("""
// CREATE TABLE {0}.{1} LIKE {2}
// """,
// DSL.name(backupDb),
// DSL.name(backupTable),
// DSL.name(originalTable)).execute();
// log.info("Backup table created: {}.{}", backupDb, backupTable);
// return;
// }

// // 이미 있다면 컬럼 비교 후 누락된 컬럼 추가
// List<String> originalColumns = getColumnNames(originalTable);
// List<String> backupColumns = getColumnNames(backupDb + "." + backupTable);

// for (String col : originalColumns) {
// if (!backupColumns.contains(col)) {
// String colDef = getColumnDefinition(originalTable, col);
// dsl.query("ALTER TABLE {0}.{1} ADD COLUMN " + colDef,
// DSL.name(backupDb),
// DSL.name(backupTable))
// .execute();
// log.info("Added column '{}' to backup table {}.{}", col, backupDb,
// backupTable);
// }
// }
// }

// /**
// * 운영 테이블의 컬럼명 리스트 가져오기
// */
// private List<String> getColumnNames(String tableName) {
// return dsl.select(DSL.field("COLUMN_NAME", String.class))
// .from("information_schema.columns")
// .where(DSL.field("table_schema").eq(dsl.select(DSL.dbName())))
// .and(DSL.field("table_name").eq(tableName))
// .fetch(DSL.field("COLUMN_NAME", String.class));
// }

// /**
// * 특정 컬럼의 DDL 정의 가져오기
// */
// private String getColumnDefinition(String tableName, String columnName) {
// Record record = dsl.fetchOne("""
// SELECT COLUMN_TYPE, IS_NULLABLE, COLUMN_DEFAULT, EXTRA
// FROM information_schema.columns
// WHERE table_schema = DATABASE()
// AND table_name = {0}
// AND column_name = {1}
// """, DSL.val(tableName), DSL.val(columnName));

// StringBuilder def = new StringBuilder(columnName)
// .append(" ")
// .append(record.get("COLUMN_TYPE", String.class));

// if ("NO".equals(record.get("IS_NULLABLE"))) {
// def.append(" NOT NULL");
// }
// if (record.get("COLUMN_DEFAULT") != null) {
// def.append(" DEFAULT '").append(record.get("COLUMN_DEFAULT")).append("'");
// }
// if (record.get("EXTRA") != null && !record.get("EXTRA").isBlank()) {
// def.append(" ").append(record.get("EXTRA"));
// }
// return def.toString();
// }

// /**
// * 테이블 모든 컬럼을 DSLField[] 형태로 반환
// */
// private Field<?>[] getAllColumns(String tableName) {
// return getColumnNames(tableName).stream()
// .map(DSL::field)
// .toArray(Field[]::new);
// }
// }