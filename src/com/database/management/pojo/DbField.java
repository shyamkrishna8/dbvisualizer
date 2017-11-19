package com.database.management.pojo;

public class DbField {

    private String fieldName;
    private FieldType fieldType = FieldType.LARGE_STRING;
    private boolean nullAllowed;
    private String defaultValue;

    public DbField() {
        super();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isNullAllowed() {
        return nullAllowed;
    }

    public void setNullAllowed(boolean nullAllowed) {
        this.nullAllowed = nullAllowed;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String constructFieldDeclaration() {
        String returnVal = this.fieldName + " " + this.fieldType.getDbFieldDeclaration();
        if (!this.nullAllowed) {
            returnVal += " not null";
        }
        if (this.defaultValue != null && !this.defaultValue.isEmpty()) {
            returnVal += " default ";
            switch (this.fieldType) {
                case SMALL_STRING:
                    returnVal += "'" + this.getDefaultValue() + "'";
                    break;
                case LARGE_STRING:
                    returnVal += "'" + this.getDefaultValue() + "'";
                    break;
                default:
                    returnVal += this.defaultValue;
            }
        }
        return returnVal;
    }

    @Override
    public String toString() {
        return "DbField [fieldName=" + fieldName + ", fieldType=" + fieldType + ", nullAllowed=" + nullAllowed
                + ", defaultValue=" + defaultValue + ", toString()=" + super.toString() + "]";
    }
}
