package ru.zhukov.repository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import ru.zhukov.domain.AccountRecord;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<AccountRecord> createAccountRecord() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource);

        return null;
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
