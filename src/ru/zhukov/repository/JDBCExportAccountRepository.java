package ru.zhukov.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.zhukov.domain.AccountRecordExport;
import ru.zhukov.dto.ExportJournal;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gukov on 11.04.2016.
 */
public class JDBCExportAccountRepository {

    private DataSource dataSource;
    private SimpleJdbcInsert simpleJdbcInsert;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public JDBCExportAccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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

}
