package com.shoppurs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.shoppurs.model.Category;
import com.shoppurs.model.SubCategory;

public class SubCatMapper implements RowMapper<SubCategory>{

	@Override
	public SubCategory mapRow(ResultSet rs, int arg1) throws SQLException {
		SubCategory item = new SubCategory();
		item.setSubCatId(rs.getInt("SUB_CATEGORY_ID"));
		item.setCatId(rs.getInt("SUB_CATEGORY_CAT_ID"));
		item.setSubCatName(rs.getString("SUB_CATEGORY_NAME"));
		item.setDelStatus(rs.getString("DEL_STATUS"));
		item.setCreatedBy(rs.getString("CREATED_BY"));
		item.setUpdatedBy(rs.getString("UPDATED_BY"));
		item.setCreatedDate(rs.getString("CREATED_DATE"));
		item.setUpdatedDate(rs.getString("UPDATED_DATE"));
		return item;
	}

}
