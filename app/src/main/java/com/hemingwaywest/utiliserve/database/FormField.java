package com.hemingwaywest.utiliserve.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.hemingwaywest.utiliserve.Utilities.Converters;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Project: Utiliserve
 * HemingwayWest, LLC. Copyright (c) 2019  All rights reserved.
 * User: Samitoo
 * Date: 6/14/2019
 * url: www.HemingwayWest.com
 * Notes:
 */
@Entity(indices ={@Index("form_id")} ,tableName = "form_field")
@TypeConverters({Converters.class})
//, foreignKeys = @ForeignKey(entity = Forms.class,
//                                                            parentColumns = "id",
//                                                            childColumns = "form_id",
//                                                            onDelete = CASCADE)
public class FormField {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int form_id;
    private String name;
    private String value;
    private String fieldType;
    private List<String> optionsList;

    @Ignore
    public FormField(int form_id, String name, String value, String fieldType, List<String> optionsList){
        this.form_id = form_id;
        this.name = name;
        this.value = value;
        this.fieldType = fieldType;
        this.optionsList = optionsList;
    }

    public FormField(int id, int form_id, String name, String value, String fieldType, List<String> optionsList){
        this.id = id;
        this.form_id = form_id;
        this.name = name;
        this.value = value;
        this.fieldType = fieldType;
        this.optionsList = optionsList;
        /*if (optionsList!=null){
            this.optionsList = new ArrayList<>();
            this.optionsList.addAll(optionsList);
        }*/

    }

    /*public static FormField[] prepopulateFormFields(){
        return new FormField[];
    }*/


    //Getters
    public int getId(){return id;}
    public int getForm_id(){return form_id;}
    public String getName() {return name;}
    public String getValue() {return value;}
    public String getFieldType() {return fieldType;}
    public List<String> getOptionsList() {return optionsList;}

    //Setters
    public void setId(int id){this.id = id;}
    public void setForm_id(int parentID){this.form_id = parentID;}
    public void setName(String name) {this.name = name;}
    public void setValue(String value) {this.value = value;}
    public void setFieldType(String fieldType) {this.fieldType = fieldType;}
    public void setOptionsList(List<String> optionsList) {this.optionsList = optionsList;}
}
