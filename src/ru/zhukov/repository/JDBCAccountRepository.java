package ru.zhukov.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ru.zhukov.domain.AccountRecord;
import ru.zhukov.domain.AccountRecordExport;
import ru.zhukov.domain.AccrualEmployee;
import ru.zhukov.dto.ExportJournal;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gukov on 28.03.2016.
 */

public class JDBCAccountRepository implements AccountRepository{
    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;
    private DataSource exportDataSource;



    public JDBCAccountRepository(DataSource dataSource,DataSource exportDataSource){
        this.dataSource = dataSource;
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.exportDataSource = exportDataSource;
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
    public List<AccountRecordExport> listAccountRecordForExportByMonthAndYear(int month, int year) {
        String listExport = "SELECT B.CREDIT,B.DEBET as debit,B.TEXT,B.MES,\n" +
                            "B.ZATR as AP2,B.AP1 as ap4,B.AP7 as ap8,X.MNEMOKOD as ap7," +
                            "sum(B.SUMMA) AS SUMMA\n" +
                            "FROM PROVOD_BO B\n" +
                            "LEFT JOIN XCHECK X ON B.XCHECK = X.N_OTD\n" +
                            "WHERE SUBSTRING(B.CREDIT,1,2) NOT IN ('50','51','62','71','73.20.00')\n" +
                            "AND   B.SUMMA <> 0  AND\n" +
                            "B.AP9 = :VMES AND  B.AP10 = :VYEAR\n" +
                            "GROUP BY B.CREDIT,B.DEBET,B.TEXT,B.MES,B.ZATR,B.AP1,B.AP7,X.MNEMOKOD\n";
        Map<String,Object> nameParameters = new HashMap<>();
        nameParameters.put("VMES",month);
        nameParameters.put("VYEAR",year);

        return jdbcTemplate.query(listExport,nameParameters,new AccountRecordExportMapper());
    }

    @Override
    public void exportAccountRecord(int month, int year, ExportJournal exportJournal) {
         JDBCExportAccountRepository exportAccountRepository = new JDBCExportAccountRepository(exportDataSource);
         Number parent = exportAccountRepository.addRecordInLedgerJournalTable(exportJournal);
         exportAccountRepository.batchInsert(this.listAccountRecordForExportByMonthAndYear(month,year),exportJournal.getCode(),exportJournal.getDimensionDB());


    }


    public  List<AccrualEmployee> accrualEmployeeList(int month, int year){
        String query = "SELECT DISTINCT c.pers_id as persId, (x.brief_Name + ' '+ X.naz_otd + ' ') as NameOtdel ,\n" +
                       "C.tabel_n,(RTrim(c.name)+' '+Rtrim(c.first_name)+' '+Rtrim(c.sec_name))AS NameEmployee,\n" +
                       "(case when isnull(c.sprm4,'')<> '' then c.sprm4 else case\n" +
                         "when isnull(c.sprm5,'')<> '' then c.sprm5 else c.sprm6 end end) as schet,\n" +
                         "R.CODE,R.SUMMA,  (RTrim(c.name)+' '+Rtrim(c.first_name)+' '+Rtrim(c.sec_name)) AS poluch,\n" +
                         "p.number_schet as schet_sb,\n" +
                         " RTrim(c.name)  as name,\n" +
                         " Rtrim(c.first_name)  as first_name,\n" +
                         " Rtrim(c.sec_name)  as sec_name,\n" +
                         "V.CODENAME FROM CONST C\n" +
                       "JOIN XCHECK X ON C.N_OTD = X.N_OTD\n" +
                       "/*AND  X.N_OTD IN (SELECT N_OTD FROM PERMISSIONS P\n" +
                       "                  WHERE P.USER_ID = (SELECT ID FROM USERS U\n" +
                       "                                     WHERE U.USER_NM = :USER_NM\n" +
                       "                  ))*/\n" +
                       "LEFT JOIN ARHIV_RAS R ON R.PERS_ID = C.PERS_ID\n" +
                       "AND R.YY = :VYEAR and R.MM = :VMES\n" +
                       "LEFT JOIN SPRAV V ON V.CODE = R.CODE\n" +
                       "left join perech p on r.pers_id = p.pers_id\n" +
                       "where  r.code ='701'\n" +
                       "and isnull(p.bank,'') <> ''\n" +
                       "ORDER BY (RTrim(c.name)+' '+Rtrim(c.first_name)+' '+Rtrim(c.sec_name))\n";


        Map<String,Object> nameParameters = new HashMap<>();
        nameParameters.put("VMES",month);
        nameParameters.put("VYEAR",year);

        return jdbcTemplate.query(query,nameParameters,new AccrualEmployeeMapper());

    }




    private class AccountRecordMapper implements RowMapper<AccountRecord>{

        @Override
        public AccountRecord mapRow(ResultSet resultSet, int i) throws SQLException {
            AccountRecord accountRecord = new AccountRecord();
            accountRecord.setId(resultSet.getInt("idKey"));
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

    private class AccountRecordExportMapper implements  RowMapper<AccountRecordExport> {
        @Override
        public AccountRecordExport mapRow(ResultSet resultSet, int i) throws SQLException {
            AccountRecordExport recordExport = new AccountRecordExport();
            recordExport.setDebit(resultSet.getString("debit"));
            recordExport.setCredit(resultSet.getString("credit"));
            recordExport.setSumma(resultSet.getDouble("summa"));
            recordExport.setText(resultSet.getString("text"));
            recordExport.setMonth(resultSet.getInt("mes"));
            //TODO передача ввиде параметра
            //recordExport.setTransDimension(resultSet.getString(""));
            recordExport.setTransDimension2(resultSet.getString("ap2"));
            recordExport.setTransDimension4(resultSet.getString("ap4"));
            recordExport.setTransDimension7(resultSet.getString("ap7"));
            recordExport.setTransDimension8(resultSet.getString("ap8"));



            return  recordExport;

        }
    }

    private class AccrualEmployeeMapper implements RowMapper<AccrualEmployee> {
        @Override
        public AccrualEmployee mapRow(ResultSet resultSet, int i) throws SQLException {
            AccrualEmployee accrualEmployee = new AccrualEmployee();
            accrualEmployee.setPersId(resultSet.getString("persId"));
            accrualEmployee.setTabel(resultSet.getString("tabel_n"));
            accrualEmployee.setNameEmployee(resultSet.getString("NameEmployee"));
            accrualEmployee.setDepartment(resultSet.getString("NameOtdel"));
            accrualEmployee.setAccountNumber(resultSet.getString("schet"));
            accrualEmployee.setReceiverName(resultSet.getString("name"));
            accrualEmployee.setReceiverFirstName(resultSet.getString("first_name"));
            accrualEmployee.setReceiverSecondtName(resultSet.getString("sec_name"));
            accrualEmployee.setAccountByBank(resultSet.getString("schet_sb"));
            accrualEmployee.setCode(resultSet.getString("code"));
            accrualEmployee.setCodeName(resultSet.getString("codename"));
            accrualEmployee.setSumma(resultSet.getDouble("summa"));

            return accrualEmployee;
        }
    }
}
