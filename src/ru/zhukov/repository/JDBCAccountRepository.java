package ru.zhukov.repository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.zhukov.domain.AccountRecord;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Gukov on 28.03.2016.
 */
@Repository
public class JDBCAccountRepository implements AccountRepository,InitializingBean {
    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public JDBCAccountRepository(DataSource dataSource){
        this.dataSource = dataSource;
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<AccountRecord> listAccountRecordsByMonthAndYear(int month, int year) {

        String queryAccount = "SELECT  (RTrim(x.brief_name)+' '+RTRIM(x.naz_otd)) AS Otdel," +
                              " RTRIM(B.CREDIT) as credit, RTRIM(B.DEBET) as debit, RTRIM(B.TEXT) as text, B.MES, B.ZATR, B.XCHECK, X.MNEMOKOD as cfo, b.code as code, " +
                              " (RTrim(c.name)+' '+Rtrim(c.first_name)+' '+Rtrim(c.sec_name)) AS employee, " +
                              " AP9 AS MESPROVOD, AP10 AS YEARPROVOD, AP1 AS OBJECT, AP7 AS NALOG, B.SUMMA, B.idKey " +
                              " FROM PROVOD_BO B " +
                              " LEFT JOIN CONST C ON B.PERS_ID = C.PERS_ID " +
                              " LEFT JOIN XCHECK X ON B.XCHECK = X.N_OTD " +
                              " WHERE  B.AP9 = :VMES AND  B.AP10 = :VYEAR " +
                              " AND B.SUMMA <> 0";
        Map<String,Object> nameParameters = new HashMap<>();
        nameParameters.put("VMES",month);
        nameParameters.put("VYEAR",year);

         return jdbcTemplate.query(queryAccount,nameParameters, new AccountRecordMapper());

    }

    @Override
    public void createAccountRecord() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource);
        jdbcCall.withProcedureName("sp_create_provodki").execute();
    }

    @Override
    public List<AccountRecord> listAccountRecordForExportByMonthAndYear(int month, int year) {
        String listExport = "SELECT B.CREDIT,B.DEBET,B.TEXT,B.MES,\n" +
                            "B.ZATR as AP2,B.AP1,B.AP7,X.MNEMOKOD,sum(B.SUMMA) AS SUMMA\n" +
                            "FROM PROVOD_BO B\n" +
                            "LEFT JOIN XCHECK X ON B.XCHECK = X.N_OTD\n" +
                            "WHERE SUBSTRING(B.CREDIT,1,2) NOT IN ('50','51','62','71','73.20.00')\n" +
                            "AND   B.SUMMA <> 0  AND\n" +
                            "B.AP9 = :VMES AND  B.AP10 = :VYEAR\n" +
                            "GROUP BY B.CREDIT,B.DEBET,B.TEXT,B.MES,B.ZATR,B.AP1,B.AP7,X.MNEMOKOD\n";
        Map<String,Object> nameParameters = new HashMap<>();
        nameParameters.put("VMES",month);
        nameParameters.put("VYEAR",year);
        return jdbcTemplate.query(listExport,nameParameters, new AccountRecordMapper());
    }


    public void exportAccountRecord() {
        KeyHolder keyHolder = new GeneratedKeyHolder();

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private class AccountRecordMapper implements RowMapper<AccountRecord>{

        @Override
        public AccountRecord mapRow(ResultSet resultSet, int i) throws SQLException {
            AccountRecord accountRecord = new AccountRecord();
            accountRecord.setDepartment(resultSet.getString("Otdel"));
            accountRecord.setEmployee(resultSet.getString("employee"));
            accountRecord.setDebit(resultSet.getString("debit"));
            accountRecord.setCredit(resultSet.getString("credit"));
            accountRecord.setSumma(resultSet.getDouble("summa"));
            accountRecord.setDescription(resultSet.getString("text"));
            accountRecord.setCostItem(resultSet.getString("zatr"));
            accountRecord.setObjectId(resultSet.getString("object"));
            accountRecord.setTaxArticle(resultSet.getString("nalog"));
            accountRecord.setCfo(resultSet.getString("cfo"));
            accountRecord.setCode(resultSet.getString("code"));
            return accountRecord;
        }
    }
}
