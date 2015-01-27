package com.JazzDevStudio.LacunaExpress.MISCClasses;

/**
 * Created by Alma on 1/26/2015.
 */
public class TextFilters {
    public static String FilterIllegalCharacters(String inputToFilter){
       inputToFilter = inputToFilter.replace("[","\\[");
       inputToFilter = inputToFilter.replace("]","\\]");
       inputToFilter = inputToFilter.replace("{","\\{");
       inputToFilter = inputToFilter.replace("}","\\}");
       inputToFilter = inputToFilter.replace("\"","\\\"");
       //inputToFilter = inputToFilter.replace("\'","\\\'");
       //inputToFilter = inputToFilter.replaceAll("([\"'\\[\\]\\(\\)\\{\\}])/\\\\$1/g");
       //inputToFilter.replaceAll

       inputToFilter = inputToFilter.replace("<","");
       inputToFilter = inputToFilter.replace(">","");
       return inputToFilter;
    }
}
