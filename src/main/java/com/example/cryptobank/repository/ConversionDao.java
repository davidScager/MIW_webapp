package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConversionDao implements Dao<Conversion> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ConversionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    RowMapper<Conversion> conversionRowMapper = (resultSet, rownumber) -> {
        Conversion conversion = new Conversion();
        conversion.setRate(resultSet.getString("rate"));
        /*conversion.setAsset1(assetDao.getByName(resultSet.getString("asset1")));
        conversion.setAsset2(assetDao.getByName(resultSet.getString("asset2")));*/
        conversion.setConversionValue(resultSet.getDouble("conversionValue"));
        return conversion;
    };


    @Override
    public List list() {
        return jdbcTemplate.query("SELECT * FROM Conversion", conversionRowMapper);
    }

    @Override
    public void create(Conversion conversion) {
        String sql = "INSERT INTO Conversion(rate, asset1, asset2, conversionValue) values(?,?,?)";
        jdbcTemplate.update(sql, conversion.getRate(), conversion.getAsset1(), conversion.getAsset2(), conversion.getConversionValue());
    }

    @Override
    public Optional<Conversion> get(int id) {
        //er is geen id in conversion dus komt er een 2e get met een string als waarde
        return Optional.empty();
    }

    public Optional<Conversion> get(String rate){
        List<Conversion> conversionList = jdbcTemplate.query("Select * from Conversion where rate = ?", conversionRowMapper, rate);
        if (conversionList.size() != 1){
            return Optional.empty();
        } else {
            return Optional.of(conversionList.get(0));
        }
    }

    @Override
    public void update(Conversion conversion, int id) {
    // ook hier een andere methode omdat er geen id is
    }

    public void update(Conversion conversion, String rate){
        String sql ="UPDATE Conversion set rate =?, asset1 = ?, asset2 = ?, conversionValue = ? where rate = ?";
        jdbcTemplate.update(sql, rate, conversion.getAsset1(), conversion.getAsset1(), conversion.getConversionValue(), rate);
    }

    @Override
    public void delete(int id) {
        //hier ook
    }

    public void delete(String rate){
        jdbcTemplate.update("DELETE FROM Conversion WHERE rate = ?", rate);
    }


}
