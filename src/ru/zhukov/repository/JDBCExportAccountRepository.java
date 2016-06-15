package ru.zhukov.repository;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.zhukov.domain.AccountRecordExport;
import ru.zhukov.domain.JournalExportDetail;
import ru.zhukov.domain.JournalExportHeader;
import ru.zhukov.dto.ExportJournal;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gukov on 11.04.2016.
 */
public class JDBCExportAccountRepository implements  JournalExportRepository {

    private DataSource dataSource;
    private SimpleJdbcInsert simpleJdbcInsert;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public JDBCExportAccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<JournalExportHeader> loadAllJournal(String dimension){
       String loadJournal = "SELECT RecNo" +
                            ",State " +
                            ",IMPORTBATCH" +
                            ",SystemId" +
                            ",ParentRecId" +
                            ",Name" +
                            ",Dimension" +
                            " FROM EXP_LEDGERJOURNALTABLE where dimension = :dimension";

        Map<String,Object> nameParameters = new HashMap<>();
        nameParameters.put("dimension",dimension);

        return jdbcTemplate.query(loadJournal,nameParameters, new JournalExportHeaderRecordMapper());



    }


    public Number addRecordInLedgerJournalTable(ExportJournal journal) {

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("EXP_LEDGERJOURNALTABLE")
                .usingGeneratedKeyColumns("RecNo");


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("State",0);
        parameters.put("IMPORTBATCH",journal.getName());
        parameters.put("SystemId","АИТ");
        parameters.put("Name",journal.getDescription());
        parameters.put("ParentRecId",journal.getCode());

        parameters.put("Dimension",journal.getDimensionDB());

        return  simpleJdbcInsert.executeAndReturnKey(parameters);



    }


    public int[] batchInsert(final List<AccountRecordExport> recordExportList, String parent,String dimensionDB){

        String insertExport = " insert into EXP_LEDGERJOURNALTRANS( ParentRecId, TransDate,AccountNum, " +
                " OffsetAccount,CurrencyCode, AmountCurDebit, Txt,Dimension,Dimension2_," +
                " Dimension4_,Dimension7_,Dimension8_) " +
                " VALUES( '"+parent+"' ,GETDATE(),:debit,:credit,'RUR',:summa,:text,'"+ dimensionDB+"'," +
                " :transDimension2,:transDimension4,:transDimension7,:transDimension8)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(recordExportList.toArray());
        int[] insertRecord = jdbcTemplate.batchUpdate(insertExport,batch);
        return  insertRecord;

    }

    @Override
    public List<JournalExportHeader> loadJournal(String dimension) {
       return  loadAllJournal(dimension);
    }

    @Override
    public void delete(String parentRecId,String dimension) {

        String deleteTrans = "delete from EXP_LEDGERJOURNALTRANS where ParentRecId in (select parentRecId from  EXP_LEDGERJOURNALTABLE where ParentRecId = :parentRecId and Dimension = :dimension) " +
                         " and  dimension = :dimension";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("parentRecId",parentRecId);
        parameters.put("dimension",dimension);
        this.jdbcTemplate.update(deleteTrans, parameters);

        String deleteTable = "delete from EXP_LEDGERJOURNALTABLE where ParentRecId = :parentRecId and Dimension = :dimension";
        parameters.put("parentRecId",parentRecId);
        parameters.put("dimension",dimension);
        this.jdbcTemplate.update(deleteTable, parameters);



    }

    @Override
    public List<JournalExportDetail> loadDetailJournal(String parentJournal) {
        String loadDetailJournal = "SELECT RecNo" +
                ",State " +
                ",ParentRecId" +
                ",TableRecNo" +
                ",TransDate" +
                ",AccountNum as Debit " +
                ",OffsetAccount as Credit " +
                ",CurrencyCode, AmountCurDebit as summa " +
                ",Txt as text" +
                ",Dimension as dimension" +
                ",Dimension2_ as dimension2" +
                ",Dimension4_ as dimension4" +
                ",Dimension7_ as dimension7" +
                ",Dimension8_ as dimension8" +
                " FROM EXP_LEDGERJOURNALTRANS where ParentRecId = :ParentRecId";

        Map<String,Object> nameParameters = new HashMap<>();
        nameParameters.put("ParentRecId",parentJournal);

        return jdbcTemplate.query(loadDetailJournal,nameParameters, new JournalExportDetailRecordMapper());
    }

    private class JournalExportHeaderRecordMapper implements RowMapper<JournalExportHeader> {

        @Override
        public JournalExportHeader mapRow(ResultSet resultSet, int i) throws SQLException {
            JournalExportHeader journalExportHeaderRecord = new JournalExportHeader();
            journalExportHeaderRecord.setRecNo(resultSet.getLong("RecNo"));
            journalExportHeaderRecord.setState(resultSet.getInt("State"));
            journalExportHeaderRecord.setImportBatch(resultSet.getString("IMPORTBATCH"));
            journalExportHeaderRecord.setSystemId(resultSet.getString("SystemId"));
            journalExportHeaderRecord.setParentRecId(resultSet.getString("ParentRecId"));
            journalExportHeaderRecord.setName(resultSet.getString("Name"));
            journalExportHeaderRecord.setDimension(resultSet.getString("Dimension"));

            return journalExportHeaderRecord;
        }
    }

    private class JournalExportDetailRecordMapper implements RowMapper<JournalExportDetail> {
        @Override
        public JournalExportDetail mapRow(ResultSet resultSet, int i) throws SQLException {
            JournalExportDetail journalExportDetailRecord = new JournalExportDetail();
            journalExportDetailRecord.setRecNo(resultSet.getLong("RecNo"));
            journalExportDetailRecord.setState(resultSet.getInt("State"));
            journalExportDetailRecord.setTransDate(resultSet.getDate("TransDate"));
            journalExportDetailRecord.setTableRecNo(resultSet.getLong("TableRecNo"));
            journalExportDetailRecord.setParentRecId(resultSet.getString("ParentRecId"));
            journalExportDetailRecord.setDebit(resultSet.getString("debit"));
            journalExportDetailRecord.setCredit(resultSet.getString("credit"));
            journalExportDetailRecord.setSumma(resultSet.getDouble("summa"));
            journalExportDetailRecord.setText(resultSet.getString("text"));
            journalExportDetailRecord.setTransDimension(resultSet.getString("Dimension"));
            journalExportDetailRecord.setTransDimension2(resultSet.getString("Dimension2"));
            journalExportDetailRecord.setTransDimension4(resultSet.getString("Dimension4"));
            journalExportDetailRecord.setTransDimension7(resultSet.getString("Dimension7"));
            journalExportDetailRecord.setTransDimension8(resultSet.getString("Dimension8"));


            return  journalExportDetailRecord;
        }
    }
}


