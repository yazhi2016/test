package com.example.util;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@ModelContainer
@Table(database = AppDatabase.class)
public class MySearch extends BaseModel {
	@PrimaryKey(autoincrement = true)
	public Long id;
	@Column
	public String keywords = "";

}
