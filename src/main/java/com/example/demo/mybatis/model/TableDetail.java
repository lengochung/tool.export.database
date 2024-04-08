package com.example.demo.mybatis.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TableDetail {
    private String TABLE_CATALOG;
    private String TABLE_SCHEMA;
    private String TABLE_NAME;
    private String COLUMN_NAME;
    private String ORDINAL_POSITION;
    private String COLUMN_DEFAULT;
    private String IS_NULLABLE;
    private String DATA_TYPE;
    private String CHARACTER_MAXIMUM_LENGTH;
    private String CHARACTER_OCTET_LENGTH;
    private String NUMERIC_PRECISION;
    private String NUMERIC_SCALE;
    private String DATETIME_PRECISION;
    private String CHARACTER_SET_NAME;
    private String COLLATION_NAME;
    private String COLUMN_TYPE;
    private String COLUMN_KEY;
    private String EXTRA;
    private String PRIVILEGES;
    private String COLUMN_COMMENT;
    private String GENERATION_EXPRESSION;
    private String SRS_ID;
    private List<Integer> SEQ_IN_INDEX = new ArrayList<Integer>();
    public List<Integer> getSEQ_IN_INDEX() {
        return SEQ_IN_INDEX;
    }
    public void setSEQ_IN_INDEX(List<Integer> sEQ_IN_INDEX) {
        SEQ_IN_INDEX = sEQ_IN_INDEX;
    }
    private String SEQ_IN_INDEX_JSON;
    public String getSEQ_IN_INDEX_JSON() {
        return SEQ_IN_INDEX_JSON;
    }
    @SuppressWarnings("unchecked")
    public void setSEQ_IN_INDEX_JSON(String sEQ_IN_INDEX_JSON) {
        try {
            List<Integer> list = new ArrayList<>();
            list = new Gson().fromJson(sEQ_IN_INDEX_JSON, new TypeToken<List<Integer>>() {}.getType());
            this.SEQ_IN_INDEX = list;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public String getTABLE_CATALOG() {
        return TABLE_CATALOG;
    }
    public void setTABLE_CATALOG(String tABLE_CATALOG) {
        TABLE_CATALOG = tABLE_CATALOG;
    }
    public String getTABLE_SCHEMA() {
        return TABLE_SCHEMA;
    }
    public void setTABLE_SCHEMA(String tABLE_SCHEMA) {
        TABLE_SCHEMA = tABLE_SCHEMA;
    }
    public String getTABLE_NAME() {
        return TABLE_NAME;
    }
    public void setTABLE_NAME(String tABLE_NAME) {
        TABLE_NAME = tABLE_NAME;
    }
    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }
    public void setCOLUMN_NAME(String cOLUMN_NAME) {
        COLUMN_NAME = cOLUMN_NAME;
    }
    public String getORDINAL_POSITION() {
        return ORDINAL_POSITION;
    }
    public void setORDINAL_POSITION(String oRDINAL_POSITION) {
        ORDINAL_POSITION = oRDINAL_POSITION;
    }
    public String getCOLUMN_DEFAULT() {
        return COLUMN_DEFAULT;
    }
    public void setCOLUMN_DEFAULT(String cOLUMN_DEFAULT) {
        COLUMN_DEFAULT = cOLUMN_DEFAULT;
    }
    public String getIS_NULLABLE() {
        return IS_NULLABLE;
    }
    public void setIS_NULLABLE(String iS_NULLABLE) {
        IS_NULLABLE = iS_NULLABLE;
    }
    public String getDATA_TYPE() {
        return DATA_TYPE;
    }
    public void setDATA_TYPE(String dATA_TYPE) {
        DATA_TYPE = dATA_TYPE;
    }
    public String getCHARACTER_MAXIMUM_LENGTH() {
        return CHARACTER_MAXIMUM_LENGTH;
    }
    public void setCHARACTER_MAXIMUM_LENGTH(String cHARACTER_MAXIMUM_LENGTH) {
        CHARACTER_MAXIMUM_LENGTH = cHARACTER_MAXIMUM_LENGTH;
    }
    public String getCHARACTER_OCTET_LENGTH() {
        return CHARACTER_OCTET_LENGTH;
    }
    public void setCHARACTER_OCTET_LENGTH(String cHARACTER_OCTET_LENGTH) {
        CHARACTER_OCTET_LENGTH = cHARACTER_OCTET_LENGTH;
    }
    public String getNUMERIC_PRECISION() {
        return NUMERIC_PRECISION;
    }
    public void setNUMERIC_PRECISION(String nUMERIC_PRECISION) {
        NUMERIC_PRECISION = nUMERIC_PRECISION;
    }
    public String getNUMERIC_SCALE() {
        return NUMERIC_SCALE;
    }
    public void setNUMERIC_SCALE(String nUMERIC_SCALE) {
        NUMERIC_SCALE = nUMERIC_SCALE;
    }
    public String getDATETIME_PRECISION() {
        return DATETIME_PRECISION;
    }
    public void setDATETIME_PRECISION(String dATETIME_PRECISION) {
        DATETIME_PRECISION = dATETIME_PRECISION;
    }
    public String getCHARACTER_SET_NAME() {
        return CHARACTER_SET_NAME;
    }
    public void setCHARACTER_SET_NAME(String cHARACTER_SET_NAME) {
        CHARACTER_SET_NAME = cHARACTER_SET_NAME;
    }
    public String getCOLLATION_NAME() {
        return COLLATION_NAME;
    }
    public void setCOLLATION_NAME(String cOLLATION_NAME) {
        COLLATION_NAME = cOLLATION_NAME;
    }
    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }
    public void setCOLUMN_TYPE(String cOLUMN_TYPE) {
        COLUMN_TYPE = cOLUMN_TYPE;
    }
    public String getCOLUMN_KEY() {
        return COLUMN_KEY;
    }
    public void setCOLUMN_KEY(String cOLUMN_KEY) {
        COLUMN_KEY = cOLUMN_KEY;
    }
    public String getEXTRA() {
        return EXTRA;
    }
    public void setEXTRA(String eXTRA) {
        EXTRA = eXTRA;
    }
    public String getPRIVILEGES() {
        return PRIVILEGES;
    }
    public void setPRIVILEGES(String pRIVILEGES) {
        PRIVILEGES = pRIVILEGES;
    }
    public String getCOLUMN_COMMENT() {
        return COLUMN_COMMENT;
    }
    public void setCOLUMN_COMMENT(String cOLUMN_COMMENT) {
        COLUMN_COMMENT = cOLUMN_COMMENT;
    }
    public String getGENERATION_EXPRESSION() {
        return GENERATION_EXPRESSION;
    }
    public void setGENERATION_EXPRESSION(String gENERATION_EXPRESSION) {
        GENERATION_EXPRESSION = gENERATION_EXPRESSION;
    }
    public String getSRS_ID() {
        return SRS_ID;
    }
    public void setSRS_ID(String sRS_ID) {
        SRS_ID = sRS_ID;
    }
}
