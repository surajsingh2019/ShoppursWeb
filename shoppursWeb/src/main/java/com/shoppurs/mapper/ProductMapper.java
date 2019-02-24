package com.shoppurs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shoppurs.model.MyProduct;

public class ProductMapper implements RowMapper<MyProduct>{

	@Override
	public MyProduct mapRow(ResultSet rs, int arg1) throws SQLException {
		MyProduct item = new MyProduct();
		
		item.setProdId(rs.getInt("SUB_CATEGORY_ID"));
		item.setProdCatId(rs.getInt("SUB_CATEGORY_CAT_ID"));
		item.setProdName(rs.getString("SUB_CATEGORY_NAME"));
		item.setProdBarCode(rs.getString("DEL_STATUS"));
		item.setProdDesc(rs.getString("DEL_STATUS"));
		item.setProdReorderLevel(rs.getString("DEL_STATUS"));
		item.setProdQoh(rs.getString("DEL_STATUS"));
		item.setProdHsnCode(rs.getString("DEL_STATUS"));
		item.setProdCgst(rs.getString("DEL_STATUS"));
		item.setProdIgst(rs.getString("DEL_STATUS"));
		item.setProdSgst(rs.getString("DEL_STATUS"));
		item.setProdWarranty(rs.getString("DEL_STATUS"));
		item.setProdMfgDate(rs.getString("DEL_STATUS"));
		item.setProdExpiryDate(rs.getString("DEL_STATUS"));
		item.setProdMfgBy(rs.getString("DEL_STATUS"));
		item.setProdImage1(rs.getString("DEL_STATUS"));
		item.setProdImage2(rs.getString("DEL_STATUS"));
		item.setProdImage3(rs.getString("DEL_STATUS"));
		item.setProdMrp(rs.getFloat(""));
		item.setProdSp(rs.getFloat(""));
		item.setCreatedBy(rs.getString("CREATED_BY"));
		item.setUpdatedBy(rs.getString("UPDATED_BY"));
		item.setCreatedDate(rs.getString("CREATED_DATE"));
		item.setUpdatedDate(rs.getString("UPDATED_DATE"));
		return item;
	}

}
