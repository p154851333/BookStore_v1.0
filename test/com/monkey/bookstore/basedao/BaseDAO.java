package com.monkey.bookstore.basedao;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;

public class BaseDAO {
	private QueryRunner qr = new TxQueryRunner();
	
	public void add(){
		
	}
}
